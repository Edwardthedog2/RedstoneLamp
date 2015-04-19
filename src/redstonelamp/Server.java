package redstonelamp;

import redstonelamp.logger.Logger;
import redstonelamp.utils.StringCast;

public class Server extends Thread {
	private String address, name, motd, generator_settings, level_name, seed, level_type, rcon_pass;
	private int port, spawn_protection, max_players, gamemode, difficulty;
	private boolean whitelist, announce_player_achievements, allow_cheats, spawn_animals, spawn_mobs, force_gamemode, hardcore, pvp, query, rcon, auto_save;
	
	public Server(String name, String motd, String port, String whitelist, String announce_player_achievements, String spawn_protection, String max_players, String allow_cheats, String spawn_animals, String spawn_mobs, String gamemode, String force_gamemode, String hardcore, String pvp, String difficulty, String generator_settings, String level_name, String seed, String level_type, String query, String rcon, String rcon_pass, String auto_save) {
		Thread.currentThread().setName("RedstoneLamp");
		this.name							= name;
		this.motd							= motd;
		this.port							= StringCast.toInt(port);
		this.whitelist						= StringCast.toBoolean(whitelist);
		this.announce_player_achievements	= StringCast.toBoolean(announce_player_achievements);
		this.spawn_protection				= StringCast.toInt(spawn_protection);
		this.max_players					= StringCast.toInt(max_players);
		this.allow_cheats					= StringCast.toBoolean(allow_cheats);
		this.spawn_animals					= StringCast.toBoolean(spawn_animals);
		this.spawn_mobs						= StringCast.toBoolean(spawn_mobs);
		this.gamemode						= StringCast.toInt(gamemode);
		this.force_gamemode					= StringCast.toBoolean(force_gamemode);
		this.hardcore						= StringCast.toBoolean(hardcore);
		this.pvp							= StringCast.toBoolean(pvp);
		this.difficulty						= StringCast.toInt(difficulty);
		this.generator_settings				= generator_settings;
		this.level_name						= level_name;
		this.seed							= seed;
		this.level_type						= level_type;
		this.query							= StringCast.toBoolean(query);
		this.rcon							= StringCast.toBoolean(rcon);
		this.rcon_pass						= rcon_pass;
		this.auto_save						= StringCast.toBoolean(auto_save);
	}
	
	public Logger getLogger() {
		return new Logger();
	}
}
