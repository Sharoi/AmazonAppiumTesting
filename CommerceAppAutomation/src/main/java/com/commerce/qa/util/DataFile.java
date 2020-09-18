package com.commerce.qa.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.JavascriptExecutor;

public class DataFile {
	public static long PAGE_LOAD_TIMEOUT = 10;
	public static long IMPLICIT_WAIT = 15;

	public static String TESTDATA_SHEET_PATH = "./src/main/java/com/commerce/qa/testData/DataSheet.xlsx";

	static Workbook book;

	static JavascriptExecutor js;

	// method to get data from dataProvider
	public static Object[][] getData(String sheetName) {
		Sheet excelSheet;
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		excelSheet = book.getSheet(sheetName);
		Object[][] data = new Object[excelSheet.getLastRowNum()][excelSheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < excelSheet.getLastRowNum(); i++) {
			
			if (excelSheet.getRow(0).getLastCellNum() >= 2) {
				data[i][0] = excelSheet.getRow(i + 1).getCell(0).toString();
				data[i][1] = excelSheet.getRow(i + 1).getCell(1).toString();
				
			}else  {
				data[i][0] = excelSheet.getRow(i + 1).getCell(0).toString();
			}
		}
		return data;
	}

}
