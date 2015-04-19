/*
 * 
 *  _____          _     _                   _                           
 * |  __ \        | |   | |                 | |
 * | |__) |___  __| |___| |_ ___  _ __   ___| |     __ _ _ __ ___  _ __
 * |  _  // _ \/ _` / __| __/ _ \| '_ \ / _ \ |    / _` | '_ ` _ \| '_ \
 * | | \ \  __/ (_| \__ \ || (_) | | | |  __/ |___| (_| | | | | | | |_) |
 * |_|  \_\___|\__,_|___/\__\___/|_| |_|\___|______\__,_|_| |_| |_| .__/
 *                                                                | |
 *                                                                |_|
 * 
 * @author RedstoneLamp Team
 * @link http://RedstoneLamp.net
 * 
 * 
 */

package redstonelamp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import redstonelamp.RedstoneLamp;

public class RedstoneLampProperties {
	private static Properties properties;
	
	public String get(String property) {
		return properties.getProperty(property);
	}
	
	public void load() {
		try {
			properties = new Properties();
			RedstoneLamp.logger.info("Loading server properties...");
			InputStream is = RedstoneLamp.class.getClassLoader().getResourceAsStream("server.properties");
			properties.load(is);
		} catch (IOException e) {
			if(RedstoneLamp.DEBUG)
				e.printStackTrace();
		}
	}
}