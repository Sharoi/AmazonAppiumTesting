package com.commerce.qa.pages;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage  {
	Logger log= Logger.getLogger(LoginPage.class);
	private String LogoText;
	private AndroidDriver<AndroidElement> appdriver;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Sign in']")
	public MobileElement clickSignIn;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Already a customer? Sign in']")
	public MobileElement signIn;

	@AndroidFindBy(xpath = "(//android.widget.EditText)[1]")
	public MobileElement email;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Continue']")
	public MobileElement continueBtn;

	@AndroidFindBy(id = "ap_password")
	public MobileElement password;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_burger_icon")
	public MobileElement homeActionIcon;
	
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/gno_greeting_text_view")
	public MobileElement homeLogo;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Login']")
	public MobileElement userSignInToAccount;

	public LoginPage(AndroidDriver<AndroidElement> driver) {
		this.appdriver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}


	public String signInToApp(String uid, String pwd) throws  InterruptedException {
		
		String loginName="Hello. Sign In";
		appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(60000);
		homeActionIcon.click();
		
		try {
			if (homeLogo.getText().contains("Sign In") || homeLogo.getText() == null) {
				
				Reporter.log(homeLogo.getText());
				log.info("home logo text" + LogoText);
				homeLogo.click();
				Thread.sleep(90000);
				email.sendKeys(uid);
				appdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
				continueBtn.click();
				WebDriverWait wait = new WebDriverWait(appdriver,90);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
				log.info("Continue Button clicked, wait for 80s");
				Thread.sleep(80000);
				log.info("PASSWORD PAGE");
				password.sendKeys(pwd);
				appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				Thread.sleep(4000);
				userSignInToAccount.click();
				Thread.sleep(80000);
				appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				loginName = "user logged in..successful";
				return loginName;

			}
			else {
				loginName = "User already logged in..Skipping login";
				log.info("Skipping login...."+loginName+"["+homeLogo.getText()+"]");
				Reporter.log("\n"+loginName);
				appdriver.navigate().back();
				Thread.sleep(4000);
				appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				return loginName;
			}
		}catch (NoSuchElementException nse) {
			log.warn("[ERROR]:No Such Element Exception["+nse.getMessage()+"]");
			Reporter.log("\n"+loginName);
			appdriver.navigate().back();
			appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			return loginName;
		}
		 

	}

}
