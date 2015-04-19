package raknet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import raknet.login.Login02;
import raknet.login.Login05;
import raknet.login.Login07;
import raknet.packet.MessagePacket;
import redstonelamp.Player;
import redstonelamp.RedstoneLamp;
import redstonelamp.Server;
import redstonelamp.utils.Hex;

public class PacketHandler implements Runnable {
	public RedstoneLamp redstone;
	private Server server;
	private DatagramPacket packet;
	private InetAddress clientAddress;
	private int clientPort;
	private Queue<DatagramPacket> splitPackets = new LinkedList();
	private Queue<ByteBuffer> queuePackets = new LinkedList<ByteBuffer>();
	private Queue<ByteBuffer> queuePacketsToAll = new LinkedList<ByteBuffer>();
	private int queueDataSize;
	private int queueDataSizeToAll;
	
	public PacketHandler(RedstoneLamp redstone, Server server, DatagramPacket packet) {
		this.redstone = redstone;
		this.server = server;
		this.packet = packet;
		this.clientAddress = packet.getAddress();
		this.clientPort = packet.getPort();
	}
	
	public void run() {
		if(packet != null) {
			int packetType = (packet.getData()[0] & 0xFF);
			int packetSize = packet.getData().length;
			this.server.getLogger().debug("Packet from: " + clientAddress + ":" + clientPort + " with type: " + Integer.toHexString(packetType));
			switch(packetType) {
				case 0x02: //ID_UNCONNECTED_PING_OPEN_CONNECTIONS_1
				case 0x05: //ID_OPEN_CONNECTION_REQUEST_1
				case 0x07: //ID_OPEN_CONNECTION_REQUEST_2
					loginPacketHandler();
				break;
				
				case 0x1A:
					this.server.getLogger().info(clientAddress + ":" + clientPort + " tried to connect to the server with incorrect protocol version!");
				break;
				
				case 0xA0: //NACK
					this.server.getLogger().debug("NACK from: " + clientAddress + ":" + clientPort + " with type: " + Integer.toHexString(packetType) + " and with size: " + packetSize);
				break;
				
				case 0xC0: //ACK
					this.server.getLogger().debug("ACK from: " + clientAddress + ":" + clientPort + " with type: " + Integer.toHexString(packetType) + " and with size: " + packetSize);
				break;
				
				case 0x84: //CustomPacket_5
				case 0x85: //CustomPacket_6
				case 0x86: //CustomPacket_7
				case 0x87: //CustomPacket_8
				case 0x88: //CustomPacket_9
				case 0x89: //CustomPacket_10
				case 0x8A: //CustomPacket_11
				case 0x8B: //CustomPacket_12
				case 0x8C: //CustomPacket_13
				case 0x8D: //CustomPacket_14
				case 0x8E: //CustomPacket_15
				case 0x8F: //CustomPacket_16
					this.server.getLogger().debug("DataPacket from: " + clientAddress + ":" + clientPort + " with type: " + Integer.toHexString(packetType) + " and with size: " + packetSize);
					dataPacketHandler();
				break;
				
				default: //UnknownPacket
					this.server.getLogger().warn("An unknown packet was sent from " + clientAddress + ":" + clientPort + " with type: " + Integer.toHexString(packetType) + " and with size: " + packetSize);
				break;
			}
		}
	}
	
	private void loginPacketHandler() {
		int packetType = (packet.getData()[0] & 0xFF);
		Packet pkt = null;
		switch(packetType) {
		case 0x02:
			pkt = new Login02(packet, server.serverID);
			break;
			
		case 0x05:
			pkt = new Login05(packet, server.serverID);
			break;
			
		case 0x07:
			pkt = new Login07(packet, server.serverID, redstone);
			break;
			
		default:
			this.server.getLogger().error("Unable to handle login packet!");
			break;
		}

		if (pkt != null) {
		pkt.process(this);
		}
	}
	
	private void dataPacketHandler() {
		
	}
	
	private Player currentPlayer() {
		for (Player p : server.players) {
			if (p.clientAddress.equals(clientAddress) && p.clientPort == clientPort) {
				return p;
			}
		}
		return null;
	}

	private void splitDataPacket() {
		ByteBuffer b = ByteBuffer.wrap(packet.getData());
		b.get();
		byte[] count = new byte[3];
		b.get(count); //TODO: Send ack 
		sendACK(count);

		int len = packet.getLength() - 4;
		byte[] buffer = new byte[len];
		b.get(buffer);

		ByteBuffer data = ByteBuffer.wrap(buffer);
		int i = 0;
		int length = 0;
		while (i < buffer.length) {
			if (buffer[i] == 0x00) {
				length = (data.getShort(i + 1) / 8);// + 3;
				i += 3;
			} else if (buffer[i] == 0x40) {
				length = (data.getShort(i + 1) / 8);// + 6;
				i += 6;
			} else if (buffer[i] == 0x60) {
				length = (data.getShort(i + 1) / 8);// + 10;
				i += 10;
			}
			data.position(i);
			byte[] c = new byte[length];
			data.get(c);
			this.server.getLogger().debug((new StringBuilder()).append("split: ").append(Hex.getHexString(buffer[i])).append(" -> ").append(Hex.getHexString(c, true)).append(" Size: ").append(length).toString());
			DatagramPacket split = new DatagramPacket(c, c.length);
			splitPackets.add(split);
			i += length;
		}
	}

	public void sendPacket(ByteBuffer d) {
		this.server.getLogger().debug("Send: " + Hex.getHexString(d.array(), true));
		DatagramPacket p = new DatagramPacket(d.array(), d.capacity());
		p.setAddress(clientAddress);
		p.setPort(clientPort);
		try {
			server.socket.send(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendPacketToAll(Queue<ByteBuffer> q, Player player) {
		ByteBuffer b1 = ByteBuffer.allocate(queueDataSizeToAll + 4);
		b1.put((byte) 0x84);
		b1.put(Hex.intToBytes(player.getPacketCount(), 3), 0, 3);
		java.util.Iterator<ByteBuffer> it = q.iterator();
		while (it.hasNext()) {
		b1.put(it.next().array());
		}

		this.server.getLogger().debug("PacketToAll: " + Hex.getHexString(b1.array(), true));

		DatagramPacket p = new DatagramPacket(b1.array(), b1.capacity());
		p.setAddress(player.clientAddress);
		p.setPort(player.clientPort);
		try {
			server.socket.send(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendACK(byte[] count) {
		ByteBuffer r = ByteBuffer.allocate(7);
		r.put((byte) 0xc0);
		r.putShort((short) 1);
		r.put((byte) 0x01);
		r.put(count);

		DatagramPacket p = new DatagramPacket(r.array(), r.capacity());
		p.setAddress(clientAddress);
		p.setPort(clientPort);
		try {
			server.socket.send(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addToQueue(ByteBuffer b) {
		queuePackets.add(b);
		queueDataSize += b.capacity();
	}

	public void addToQueueForAll(ByteBuffer b) {
		queuePacketsToAll.add(b);
		queueDataSizeToAll += b.capacity();
	}

	private void ChatHandle() {
		new MessagePacket(packet).processAll(this);
	}
}
