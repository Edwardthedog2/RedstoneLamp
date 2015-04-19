package redstonelamp.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redstonelamp.RedstoneLamp;

public class RedstoneLampProperties {
	private static File file = new File("./server.properties");
	private static Properties properties;
	
	public String get(String property) {
		return properties.getProperty(property);
	}
	
	public void load() throws IOException {
		properties = new Properties();
//		if(!(file.isFile())) {
//			RedstoneLamp.logger.info("No server.properties file found! Creating one...");
//			InputStream is = (getClass().getResourceAsStream("server.properties"));
//			Files.copy(is, file.getAbsoluteFile().toPath());
//			this.load();
//		}
		RedstoneLamp.logger.info("Loading server properties...");
		InputStream is = RedstoneLamp.class.getClassLoader().getResourceAsStream("server.properties");
		properties.load(is);
	}
}
