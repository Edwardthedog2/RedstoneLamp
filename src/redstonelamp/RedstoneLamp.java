package redstonelamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import redstonelamp.logger.Logger;
import redstonelamp.utils.RedstoneLampProperties;
import redstonelamp.utils.StringCast;

public class RedstoneLamp implements Runnable {
	public static String SOFTWARE = "RedstoneLamp";
	public static String VERSION = "1.1.0";
	public static String CODENAME = "Pumpkin Seeds";
	public static String STAGE = "DEVELOPMENT";
	public static int API_VERSION = 2;
	
	private static RedstoneLampProperties rlp = new RedstoneLampProperties();
	public static Logger logger = new Logger();
	public static Server server;
	private boolean serverRunning;
	public boolean serverStopped;
	public ArrayList<Player> players;
	public HashMap<Integer, Long> entityIDList = new HashMap<Integer, Long>();
	private int connectedPlayers;
	
	public static boolean DEBUG;
	
	public RedstoneLamp() {
		serverRunning = true;
		serverStopped = false;
		connectedPlayers = 0;
		players = new ArrayList<Player>();
	}
	
	public static void main(String[] args) {
		try {
			rlp.load();
			DEBUG = StringCast.toBoolean(rlp.get("DEBUG_MODE"));
		} catch (IOException e) {
			if(DEBUG)
				e.printStackTrace();
			logger.fatal("Unable to load Server Properties file!");
		}
		RedstoneLamp redstone = new RedstoneLamp();
		redstone.run();
	}
	
	public void run() {
		if(startServer()) {
			long ticks = 0;
			while(serverRunning) {
				try {
					Thread.sleep(100);
				} catch(InterruptedException e) {
					//logger.log(Level.SEVERE, null, e);
				}
				ticks++;
				if (ticks % 100 == 0) {
					Runtime.getRuntime().gc();
					//logger.info("Connected players: " + players.size());
				}
			}
		}
	}

	private boolean startServer() {
		try {
			server = new Server(rlp.get("name"), rlp.get("motd"), rlp.get("server-port"), rlp.get("whitelist"), rlp.get("announce-player-achievements"), rlp.get("spawn-protection"), rlp.get("max-players"), rlp.get("allow-cheats"), rlp.get("spawn-animals"), rlp.get("spawn-mobs"), rlp.get("gamemode"), rlp.get("force-gamemode"), rlp.get("hardcore"), rlp.get("pvp"), rlp.get("difficulty"), rlp.get("generator-settings"), rlp.get("level-name"), rlp.get("level-seed"), rlp.get("levet-type"), rlp.get("enable-query"), rlp.get("enable-rcon"), rlp.get("rcon.password"), rlp.get("auto-save"));
		} catch (SocketException e) {
			if(DEBUG)
				e.printStackTrace();
			logger.fatal("***** COULDN'T BIND TO PORT *****");
		}
		return true;
	}

	public void initiateShutdown() {
		serverRunning = false;
		System.exit(0);
	}
	
	public void addPlayer(InetAddress i, int p, Long cid) {
		if (currentPlayer(i, p) == null) {
			boolean b = false;
			int entityID = 1009;
			while (!b) {
				entityID = 1000 + (int) (Math.random() * 1050);
				if (!entityIDList.containsKey(entityID)) {
					entityIDList.put(entityID, cid);
					b = true;
				}
			}
			players.add(new Player(i, p, entityID, cid));
			connectedPlayers++;
			logger.info("Connected players: " + players.size());
		}
	}

	public void removePlayer(InetAddress i, int p) {
		for (int j = 0; j < players.size(); j++) {
			Player player = players.get(j);
			if (player.clientAddress.equals(i) && player.clientPort == p) {
				entityIDList.values().remove(player.clientID);
				players.remove(j);
				connectedPlayers--;
				break;
			}
		}
		System.out.println("Connected players: " + players.size() + " " + entityIDList.size());
	}

	public Player currentPlayer(InetAddress i, int p) {
		for (Player player : players) {
			if (player.clientAddress.equals(i) && player.clientPort == p) {
				return player;
			}
		}
		return null;
	}
	
	public Integer addEntityID(Long cid) {
		boolean b = false;
		int newID = 0;
		while(!b) {
			newID = 1000 + (int) (Math.random() * 1050);
			if(!entityIDList.containsKey(newID)) {
				entityIDList.put(newID, cid);
				b = true;
			}
		}
		return newID;
	}
}
