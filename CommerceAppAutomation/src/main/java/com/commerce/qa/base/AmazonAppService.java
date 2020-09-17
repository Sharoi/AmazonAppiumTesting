package com.commerce.qa.base;

import java.io.IOException;

import com.commerce.qa.util.*;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class AmazonAppService {

	public static AndroidDriver<AndroidElement> appdriver;
	public static AppiumDriverLocalService service;
	public ReadConfigUtil globalConfigObj;
	public ReadConfigUtil amazonConfigObj;
	public static CommonUtil emulatorObject;
	public String callerClassName = this.getClass().getName();

	public AmazonAppService() {

		emulatorObject = new CommonUtil();
		globalConfigObj = new ReadConfigUtil("global.properties");
		amazonConfigObj = new ReadConfigUtil("AmazonDetails.properties");
		System.out.println(
				"Launching " + this.callerClassName + " for User [" + amazonConfigObj.getConfigValue("UId") + "]");
	}

	public void intializeAmazonApp() {
		try {
			appdriver = emulatorObject.intializeAppDriver(amazonConfigObj, callerClassName);
		} catch (IOException | InterruptedException e) {
			appdriver = null;
			e.printStackTrace();
		} catch (Exception exp) {
			appdriver = null;
			exp.printStackTrace();
		}
	}

	public static void getScreenshot(String fileName) throws IOException {
		emulatorObject.getScreenshot(appdriver, fileName);
	}

	public AppiumDriverLocalService startServer() {
		String appiumFlag= globalConfigObj.getConfigValue("START_APPIUM");
		if (appiumFlag.equals("true")) {
			boolean flag = emulatorObject.checkIfServerIsRunnning(4723);
			if (!flag) {
				service = AppiumDriverLocalService.buildDefaultService();
				service.start();
			}
		}
		return service;
	}

	
	public void startEmulator() throws IOException, InterruptedException {
		String emulatorFlag= globalConfigObj.getConfigValue("START_EMULATOR");
		String emulatorFilePath = globalConfigObj.getConfigValue("EMULATOR_FILELOACTION");		
		if (emulatorFlag!=null && emulatorFlag.equals("true")
				&& emulatorFilePath!=null) {
			emulatorObject.startEmulator(emulatorFilePath);
		}
	}

}
