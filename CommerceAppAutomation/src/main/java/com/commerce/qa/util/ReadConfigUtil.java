package com.commerce.qa.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfigUtil {
	private String fileDirectory = "\\src\\main\\java\\com\\commerce\\qa\\config\\";
	private String configfileName;
	
	public ReadConfigUtil(String fileName) {
		configfileName=fileName;
	}

	public String getConfigValue(String propertyName) {
		Properties propDetails = new Properties();
		String propValue;
		FileInputStream configFileObj;
		try {
			
			configFileObj = new FileInputStream(
					System.getProperty("user.dir") + fileDirectory+ configfileName);
			propDetails.load(configFileObj);
			propValue=propDetails.getProperty(propertyName);
		} catch (FileNotFoundException fe) {
			propValue= null;
			fe.printStackTrace();
		} catch (IOException ioe) {
			propValue= null;
			ioe.printStackTrace();
		} catch (Exception exp) {
			propValue= null;
			exp.printStackTrace();
		}
		return propValue;
	}

}
