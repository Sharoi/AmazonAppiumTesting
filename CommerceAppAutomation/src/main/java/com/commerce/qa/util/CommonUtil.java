package com.commerce.qa.util;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class CommonUtil {

	public CommonUtil() {
		

	}

	public AndroidDriver<AndroidElement> intializeAppDriver(Object appProperty, String className)
			throws IOException, InterruptedException {

		AndroidDriver<AndroidElement> appdriver;
		ReadConfigUtil propertyDetails = (ReadConfigUtil) appProperty;
		DesiredCapabilities capabilities = new DesiredCapabilities();		
		String appDir = System.getProperty("user.dir") + "\\src\\";
		File app;		
		int tmpwaitTime = 60;		
		
		if (propertyDetails.getConfigValue("NEW_COMMAND_TIMEOUT")!=null )	
			tmpwaitTime= Integer.parseInt(propertyDetails.getConfigValue("NEW_COMMAND_TIMEOUT"));

		System.out.println("Caller name[" + className+"]");
		System.out.println("device name[" + propertyDetails.getConfigValue("DEVICE_NAME")+"]");
		
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, propertyDetails.getConfigValue("DEVICE_NAME"));
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,
				propertyDetails.getConfigValue("AUTOMATION_NAME"));
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, tmpwaitTime);
		
		if (propertyDetails.getConfigValue("launchTimeOut") != null &&
				propertyDetails.getConfigValue("adbTimeOut") != null) {
				capabilities.setCapability("uiautomator2ServerLaunchTimeout", 
						Integer.parseInt(propertyDetails.getConfigValue("launchTimeOut")));
				capabilities.setCapability("adbExecTimeout", 
						Integer.parseInt(propertyDetails.getConfigValue("adbTimeOut")));
		}
		
		// check for APK or APP file if not then set appPackage & appActivity
		if (propertyDetails.getConfigValue("applicationPackageAPK") != null) {
			app = new File(appDir, propertyDetails.getConfigValue("applicationPackageAPK"));
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		} else if ((propertyDetails.getConfigValue("applicationActivity") != null)
				& (propertyDetails.getConfigValue("applicationPackage") != null)) {
			capabilities.setCapability("appPackage", propertyDetails.getConfigValue("applicationPackage"));
			capabilities.setCapability("appActivity", propertyDetails.getConfigValue("applicationActivity"));		    
		} else {
			System.out.println("No Package Detail in config file.");
			capabilities = null;
		}

		if (capabilities != null) {
			capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			System.out.println("beofre driver started:- "+capabilities.toString());
			appdriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			System.out.println("Driver started going to wait[ "+System.currentTimeMillis());
			Thread.sleep(90000);		
			appdriver.manage().timeouts().implicitlyWait(tmpwaitTime, TimeUnit.SECONDS);
			System.out.println("Driver started.["+System.currentTimeMillis()+"]\n***************\n");
		} else {
			appdriver = null;
		}
		

		return appdriver;
	}
	
	public void getScreenshot(AndroidDriver<AndroidElement> screenshoptDriver, String screenshotName)
			throws IOException {
		File scrfile = ((TakesScreenshot) screenshoptDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile, new File(System.getProperty("user.dir") + "\\" + screenshotName + ".png"));

	}
	
	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}


	public void startEmulator(String fileName)throws IOException, InterruptedException  {
		Runtime.getRuntime().exec(
				System.getProperty("user.dir") + fileName);
		Thread.sleep(60000);
	}

}
