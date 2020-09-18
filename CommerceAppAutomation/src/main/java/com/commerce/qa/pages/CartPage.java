package com.commerce.qa.pages;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage {
	
	private String priceInCart;
	private String nameInCart;
	
	private AndroidDriver<AndroidElement> appdriver;

	@AndroidFindBy(className = "android.widget.Image")
	public List<AndroidElement> productListCart;
	

	@AndroidFindBy(id ="sc-active-cart")
	public List<AndroidElement> cartList;

	@AndroidFindBy(className = "android.view.View")
	public List<AndroidElement> productPriceCart;
	
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/chrome_action_bar_cart")
	public MobileElement clickCart;
	

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Cart']")
	public MobileElement cart;

	@AndroidFindBy(xpath = "//android.widget.Button[contains(text(),'Delete']")
	public MobileElement deleteProduct;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/chrome_action_bar_burger_icon")
	public MobileElement openSidePanel;

	@AndroidFindBy(xpath = "//*[@text='Settings']")
	public MobileElement settings;

	@AndroidFindBy(xpath = "//*[@text='Not Sharoi? Sign out']")
	public MobileElement signOut;
	
	@AndroidFindBy(xpath = "//*[@text='SIGN OUT']")
	public MobileElement confirmSignOut;

	public CartPage(AndroidDriver<AndroidElement> driver) {
		this.appdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.appdriver), this);
	}

	public void navigateToCartView() throws InterruptedException {
		try {
			clickCart.click();
			Thread.sleep(60000);
			this.nameInCart=productListCart.get(1).getText();
			String[] splitParts = productPriceCart.get(4).getText().split(Pattern.quote("."));
			this.priceInCart=splitParts[0].replaceAll("[^0-9]", "");	
			System.out.println("Add2Cart:\n"+this.nameInCart+"\n["+this.priceInCart+"]");
			
		}catch (NoSuchElementException nse) {
			System.out.println("[ERROR]:No Such Element Exception["+nse.getMessage()+"]");
		}
		
		
	}

	public String getProductName() {		
		return this.nameInCart;
	}

	public String getProductPrice() {		
		return this.priceInCart;
	}

	public void deleteProductInCart() {
		productListCart.get(2).click();
	
	}

	public void logout() throws InterruptedException {
		openSidePanel.click();
		Thread.sleep(50000);
		appdriver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Settings\"));");
		Thread.sleep(15000);
		settings.click();
		Thread.sleep(10000);
		signOut.click();
		Thread.sleep(40000);
	    confirmSignOut.click();
		Thread.sleep(80000);
		System.out.println("User logged out successfully");
	}
}
