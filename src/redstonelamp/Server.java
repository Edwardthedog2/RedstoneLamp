package redstonelamp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Random;

import raknet.PacketHandler;
import redstonelamp.logger.Logger;
import redstonelamp.utils.StringCast;

public class Server extends Thread {
	private String address, name, motd, generator_settings, level_name, seed, level_type, rcon_pass;
	private int port, spawn_protection, max_players, gamemode, difficulty;
	private boolean whitelist, announce_player_achievements, allow_cheats, spawn_animals, spawn_mobs, force_gamemode, hardcore, pvp, query, rcon, auto_save;
	
	private boolean isListening;
	private RedstoneLamp redstone = new RedstoneLamp();
	public DatagramSocket socket;
	private DatagramPacket packet;
	public long serverID;
	private Random rnd = new Random();
	public long start;
	public Player[] players;
	
	public Server(String name, String motd, String port, String whitelist, String announce_player_achievements, String spawn_protection, String max_players, String allow_cheats, String spawn_animals, String spawn_mobs, String gamemode, String force_gamemode, String hardcore, String pvp, String difficulty, String generator_settings, String level_name, String seed, String level_type, String query, String rcon, String rcon_pass, String auto_save) throws SocketException {
		isListening = false;
		Thread.currentThread().setName("RedstoneLamp");
		this.name = name;
		this.motd = motd;
		this.port = StringCast.toInt(port);
		this.whitelist = StringCast.toBoolean(whitelist);
		this.announce_player_achievements = StringCast.toBoolean(announce_player_achievements);
		this.spawn_protection = StringCast.toInt(spawn_protection);
		this.max_players = StringCast.toInt(max_players);
		this.allow_cheats = StringCast.toBoolean(allow_cheats);
		this.spawn_animals = StringCast.toBoolean(spawn_animals);
		this.spawn_mobs = StringCast.toBoolean(spawn_mobs);
		this.gamemode = StringCast.toInt(gamemode);
		this.force_gamemode = StringCast.toBoolean(force_gamemode);
		this.hardcore = StringCast.toBoolean(hardcore);
		this.pvp = StringCast.toBoolean(pvp);
		this.difficulty = StringCast.toInt(difficulty);
		this.generator_settings	= generator_settings;
		this.level_name	= level_name;
		this.seed = seed;
		this.level_type	= level_type;
		this.query = StringCast.toBoolean(query);
		this.rcon = StringCast.toBoolean(rcon);
		this.rcon_pass = rcon_pass;
		this.auto_save = StringCast.toBoolean(auto_save);
		try {
			InetAddress ip = InetAddress.getLocalHost();
			this.address = ip.getHostAddress();
		} catch (UnknownHostException e) {
			this.getLogger().fatal("Unable to determine system IP!");
		}
		this.getLogger().info("Starting Minecraft: PE server on " + this.getAddress() + ":" + this.getPort());
		socket = new DatagramSocket(StringCast.toInt(port));
		socket.getBroadcast();
		serverID = rnd.nextLong();
		isListening = true;
	}
	
	public void run() {
		start = System.currentTimeMillis();
		while(isListening) {
			byte[] buffer = new byte[1536];
			packet = new DatagramPacket(buffer, 1536);
			int packetSize = 0;
			try {
				socket.setSoTimeout(5000);
				socket.receive(packet);
				socket.setSoTimeout(0);
			} catch(Exception e) {
				this.getLogger().debug("No packets recieved in the last 5 seconds");
			}
			
			if(packetSize > 0) {
				ByteBuffer b = ByteBuffer.wrap(packet.getData());
				byte[] data = new byte[packet.getLength()];
				b.get(data);
				
				DatagramPacket pkt = new DatagramPacket(data, packetSize);
				pkt.setAddress(packet.getAddress());
				pkt.setPort(packet.getPort());
				new Thread(new PacketHandler(redstone, this, pkt)).start();
			}
		}
	}
	
	public void sendPacket(ByteBuffer response, Player player) {
		DatagramPacket responsePacket = null;
		if (response != null) {
		    try {
				responsePacket = new DatagramPacket(response.array(), response.capacity());
				responsePacket.setAddress(player.clientAddress);
				responsePacket.setPort(player.clientPort);
				socket.send(responsePacket);
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		}
	}
	
	/*
	 * @return String ServerIP
	 */
	public String getAddress() {
		return address;
	}
	
	/*
	 * @return String ServerPort
	 */
	public int getPort() {
		return port;
	}
	
	
	/*
	 * @return String MOTD
	 */
	public String getMOTD() {
		return motd;
	}
	
	/*
	 * @return boolean Whitelisted
	 */
	public boolean isWhitelisted() {
		return whitelist;
	}
	
	/*
	 * @return int MaxPlayers
	 */
	public int getMaxPlayers() {
		return max_players;
	}
	
	/*
	 * @return boolean Cheats
	 */
	public boolean cheatsEnabled() {
		return allow_cheats;
	}
	
	/*
	 * @return boolean Animals
	 */
	public boolean spawnAnimals() {
		return spawn_animals;
	}
	
	/*
	 * @return boolean Mobs
	 */
	public boolean spawnMobs() {
		return spawn_mobs;
	}
	
	/*
	 * @return int Gamemode
	 */
	public int getGamemode() {
		return gamemode;
	}
	
	/*
	 * @return boolean Hardcore
	 */
	public boolean isHardcore() {
		return hardcore;
	}
	
	/*
	 * @return boolean PvP
	 */
	public boolean isPvPEnabled() {
		return pvp;
	}
	
	/*
	 * @return int Difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}
	
	/*
	 * @return String LevelName
	 */
	public String getLevelName() {
		return level_name;
	}
	
	/*
	 * @return String seed
	 */
	public String getSeed() {
		return seed;
	}
	
	/*
	 * @return boolean AutoSave
	 */
	public boolean isAutoSaveEnabled() {
		return auto_save;
	}
	
	/*
	 * @return long ID
	 */
	public long getServerID() {
		return serverID;
	}
	
	/*
	 * @return Logger
	 */
	public Logger getLogger() {
		return RedstoneLamp.logger;
	}
}
