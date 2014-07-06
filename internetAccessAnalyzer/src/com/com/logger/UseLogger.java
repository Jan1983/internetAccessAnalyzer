package com.logger;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class UseLogger {
	public static void init() {
		try {
			InputStream is = UseLogger.class.getResourceAsStream("logging.properties");
			Properties props = new Properties();
			props.load(is);
			BasicConfigurator.configure(); 
			PropertyConfigurator.configure(props);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
