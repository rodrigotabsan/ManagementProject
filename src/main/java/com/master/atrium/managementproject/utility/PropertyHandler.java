package com.master.atrium.managementproject.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHandler {

	private static PropertyHandler instance = null;
	private Properties props = new Properties();	
	private InputStream stream = UtilEMail.class.getClassLoader().getResourceAsStream("application.properties");
	
	private PropertyHandler(){
         // Here you could read the file into props object
		try {
			props.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized PropertyHandler getInstance() {
		if (instance == null) {
			instance = new PropertyHandler();
		}
		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}
}