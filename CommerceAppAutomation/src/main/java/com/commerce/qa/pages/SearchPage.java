
package com.commerce.qa.pages;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class SearchPage {
	
	WebElement desiredProduct;
	String productName;
	String productPrice;
	
	
	private AndroidDriver<AndroidElement> appdriver;

	@AndroidFindBy(xpath ="//android.widget.Button[@text='Add to Cart']")
	public MobileElement addToCart;

	@AndroidFindBy(xpath = "//android.widget.Button[@text='Cart']")
	public MobileElement cart;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_search_src_text")
	private MobileElement search,searchBox;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/rs_results_count_text")
	private MobileElement productCount;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Enter a pincode']")
	private MobileElement pinCode;
	
	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/loc_ux_pin_code_text_pt1")
	private MobileElement enterPinCodeValue;
	
	@AndroidFindBy(xpath = "//android.widget.Button[@text='Apply']")
	private MobileElement applyPinCode;

	@AndroidFindBy(xpath = "//*[@text='DONE']")
	private MobileElement clickDone;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/list_product_linear_layout")
	private List<AndroidElement> productListInPage;

	@AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/item_title")
	private MobileElement selectedProduct;

	public SearchPage(AndroidDriver<AndroidElement> driver) {
		this.appdriver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(this.appdriver), this);
	}

	public String searchProduct(String productToSearch) throws InterruptedException {

		search.click();
		Thread.sleep(60000);
		searchBox.sendKeys(productToSearch);
		Thread.sleep(10000);
		this.appdriver.pressKey(new KeyEvent(AndroidKey.ENTER));
		System.out.println("Search Text [" + searchBox.getText() + "] After Click ");
		String SearchProdName=searchBox.getText();
		Thread.sleep(60000);		
		return SearchProdName;
	}
	
	public String getSelectedProductDetail ()  throws InterruptedException {
		
		try {			
			if (pinCode != null) {
				pinCode.click();				
				appdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				enterPinCodeValue.sendKeys("624001");
				applyPinCode.click();
			}
		} catch (NoSuchElementException nsee) {
			
			System.out.println("Pincode of is already available.Continuing with product search" + nsee.getAdditionalInformation());
		}

		try {
			int tmpCount = 0;
			
			if (productListInPage!=null && productListInPage.size()>1) {
				tmpCount=productListInPage.size()-3;
			}
			AndroidElement productLayout =  productListInPage.get(tmpCount);	
			this.productName=productLayout.findElement(By.id("com.amazon.mShop.android.shopping:id/item_title")).getText();			
			System.out.println("Selected Product name "+ this.productName);

			List<MobileElement> insideLayout=productLayout.findElementsByXPath("//android.widget.TextView");
			String[] parts = insideLayout.get(2).getText().split(Pattern.quote("."));
			this.productPrice=parts[0].replaceAll("[^0-9]", "");
			System.out.println("Selected Prod price "+  this.productPrice);
			
			productLayout.click();
			Thread.sleep(50000);
			
		} catch (NoSuchElementException nsee) {
			System.out.println("[ERROR] -- ProductLayout Element Not Found" + nsee.getStackTrace());
		}
		return productName;
	}
	
	public void fetchProductDetails(String ProductSearch) throws InterruptedException {
		
		searchProduct(ProductSearch);
		getSelectedProductDetail();
		
		appdriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Add to Cart\"));");
		Thread.sleep(10000);
		addToCart.click();
		Thread.sleep(20000);
		WebDriverWait wait = new WebDriverWait(appdriver, 90);
		wait.until(ExpectedConditions.visibilityOf(clickDone));
		clickDone.click();		
		Thread.sleep(50000);
	}
	
	public String getselectedProductName() {
		return this.productName;
	}
	
	public String getselectedProductPrice() {
		return this.productPrice;
	}
	
	public void searchAllProduct(String ProductToSearch) throws InterruptedException {
		search.click();
		Thread.sleep(60000);
		searchBox.sendKeys(ProductToSearch);
		Thread.sleep(10000);
		this.appdriver.pressKey(new KeyEvent(AndroidKey.ENTER));		
		Thread.sleep(60000);
		this.appdriver.findElements(
				MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).setAsVerticalList().flingToEnd(2)"));
		
	}

}
