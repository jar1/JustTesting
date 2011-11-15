package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class ProductsComponentReplaceMergePage extends AbstractWebPage {
	
	@FindBy(id="diffElementsForm:merge")
	private WebElement buttonMerge;
	
	@FindBy(id="topCancelLink")
	private WebElement buttonCancel;
	
	
	public ProductsComponentReplaceMergePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public ProductDataPage merge() {
		buttonMerge.click();
		return create(ProductDataPage.class);
	}	

	public ProductDataPage cancel() {
		buttonCancel.click();		
		return create(ProductDataPage.class);
	}

}
