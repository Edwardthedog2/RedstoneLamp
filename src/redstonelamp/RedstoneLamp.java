package redstonelamp;

import java.io.IOException;

import redstonelamp.logger.Logger;
import redstonelamp.utils.RedstoneLampProperties;
import redstonelamp.utils.StringCast;

public class RedstoneLamp {
	public static String SOFTWARE = "RedstoneLamp";
	public static String VERSION = "1.1.0";
	public static String CODENAME = "Pumpkin Seeds";
	public static String STAGE = "DEVELOPMENT";
	public static int API_VERSION = 2;
	
	private static RedstoneLampProperties rlp = new RedstoneLampProperties();
	public static Logger logger = new Logger();
	public static Server server;
	
	public static boolean DEBUG;
	
	public static void main(String[] args) {
		try {
			rlp.load();
			DEBUG = StringCast.toBoolean(rlp.get("DEBUG_MODE"));
		} catch (IOException e) {
			if(DEBUG)
				e.printStackTrace();
			logger.fatal("Unable to load Server Properties file!");
		}
		server = new Server(rlp.get("name"), rlp.get("motd"), rlp.get("server-port"), rlp.get("whitelist"), rlp.get("announce-player-achievements"), rlp.get("spawn-protection"), rlp.get("max-players"), rlp.get("allow-cheats"), rlp.get("spawn-animals"), rlp.get("spawn-mobs"), rlp.get("gamemode"), rlp.get("force-gamemode"), rlp.get("hardcore"), rlp.get("pvp"), rlp.get("difficulty"), rlp.get("generator-settings"), rlp.get("level-name"), rlp.get("level-seed"), rlp.get("levet-type"), rlp.get("enable-query"), rlp.get("enable-rcon"), rlp.get("rcon.password"), rlp.get("auto-save"));
	}
}
