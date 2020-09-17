package com.commerce.qa.test;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.commerce.qa.base.AmazonAppService;
import com.commerce.qa.pages.LoginPage;


public class LoginPageTest extends AmazonAppService {
	
	LoginPage loginObj;
	boolean loginflag = true;

	public LoginPageTest() {
		callerClassName = this.getClass().getName();
	}
	
	/*
    @BeforeTest
    public void startUpSuite() throws IOException, InterruptedException {
    	startEmulator();
    	startServer();
    }
    */
	
	@BeforeClass
	public void setup() throws IOException, InterruptedException {			
		intializeAmazonApp();
		this.loginObj = new LoginPage(appdriver);
		appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Reporter.log("Launching the application.");
	}

	@Test
	public void verifyUserLogin() throws InterruptedException {
		String loginTeststr= loginObj.signInToApp(amazonConfigObj.getConfigValue("UId"), amazonConfigObj.getConfigValue("Password"));
		Reporter.log("\nLogin Verification Status["+loginTeststr+"]");		
		assertFalse(loginTeststr.equals("Hello. Sign In"));
	}

	@AfterClass
	public void teardown() {
		appdriver.quit();
	}
	

}
