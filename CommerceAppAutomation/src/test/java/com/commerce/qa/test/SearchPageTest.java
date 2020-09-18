

package com.commerce.qa.test;
import com.commerce.qa.pages.LoginPage;
import com.commerce.qa.pages.SearchPage;
import com.commerce.qa.util.DataFile;

import static org.testng.Assert.assertTrue;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.commerce.qa.base.AmazonAppService;

public class SearchPageTest extends AmazonAppService{
	LoginPage loginObj;
	SearchPage searchObj;
	String sheetName="SearchProduct";
	
	public SearchPageTest() {
		callerClassName = this.getClass().getName();
	}

    
	@BeforeClass
	public void SetUp() throws InterruptedException	{
		
		intializeAmazonApp();
		searchObj=new SearchPage(appdriver);
		Reporter.log("Search product\n");
	}
	
	
	@DataProvider
	public Object[][] getDataValue() {
		Object data[][]=DataFile.getData(sheetName);
			return data;
	}
	
	@Test(priority=1,dataProvider="getDataValue")
	public void getProductDetails(String ListToSearch) throws InterruptedException
	{
		searchObj.searchAllProduct(ListToSearch);
		Reporter.log("Product search for the list of data in data provider excel sheet");	
	}
	
	@Test(priority=2)
	public void a_searchProduct() throws InterruptedException 
	{
		String searchProd=searchObj.searchProduct(globalConfigObj.getConfigValue("ProductToSearch"));
		Reporter.log("\\nSearching for "+searchProd+"\n");
		assertTrue(searchProd!=null && searchProd.equals(globalConfigObj.getConfigValue("ProductToSearch")));
	}
	
	@Test(priority=3)
	public void b_selectProductDetail() throws InterruptedException 
	{
		String productSelected=searchObj.getSelectedProductDetail();
		Reporter.log("\nSelected Product.\n"+productSelected+"\n");
		assertTrue(productSelected!=null && productSelected.length()>1);
	}
	
	@AfterClass
	public void teardown() {
		appdriver.quit();
	}


}
