package com.commerce.qa.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static org.testng.Assert.assertEquals;

import org.testng.Reporter;

import com.commerce.qa.base.AmazonAppService;
import com.commerce.qa.pages.CartPage;
import com.commerce.qa.pages.SearchPage;

public class CartPageTest extends AmazonAppService {
	
	SearchPage searchObj;
	CartPage cartObj;

	public CartPageTest() {
		callerClassName = this.getClass().getName();
	}

	@BeforeClass
	public void setUp() throws InterruptedException

	{
		intializeAmazonApp();
		searchObj=new SearchPage(appdriver);
		searchObj.fetchProductDetails(globalConfigObj.getConfigValue("ProductToSearch"));
		Reporter.log("Search product\n");
		cartObj = new CartPage(appdriver);
		Reporter.log("Navigate to Cart Page\n");
		cartObj.navigateToCartView();
	}
	
	@Test(priority=1)
	public void verifyProductName() {	
		assertEquals(searchObj.getselectedProductName(), cartObj.getProductName(),
				"Product Name is not matching in cart and product details page");
		Reporter.log("Product name is being verified");	
	}
	
	@Test(priority=2)
	public void verifyProductPrice() {		
		assertEquals(searchObj.getselectedProductPrice(), cartObj.getProductPrice(),
				"Product price is not matching in cart and product details page");
		Reporter.log("Product price is verified");

	}


	@AfterClass
	public void teardown() throws InterruptedException {
		Reporter.log("User logging out");
		cartObj.logout();
		appdriver.quit();
		Reporter.log("LogOut successful");

	}

}
