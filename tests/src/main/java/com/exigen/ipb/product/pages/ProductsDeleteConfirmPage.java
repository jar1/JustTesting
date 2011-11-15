package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class ProductsDeleteConfirmPage extends AbstractWebPage {
	
	@FindBy(id="informDeletedProductsForm:informDeletedProductsTable")
	private WebElement tableProducstDeleted;
	
	@FindBy(id="informDeletedProductsForm:backBtn")
	private WebElement buttonBack;
	
	public ProductsDeleteConfirmPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	/**
	 * Does not seem to work
	 * 
	 * @param productCode
	 * @return 
	 */
	public boolean isProductDeleted(String productCode) {
		return tableProducstDeleted.getText().contains(productCode);
	}
	
	public ProductSearchPage clickBack() {
		buttonBack.click();
		
		return create(ProductSearchPage.class);
	}

}
