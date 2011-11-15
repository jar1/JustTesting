package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Page Object that represents product deletion page.
 * 
 * @author ?
 * @since 3.9
 */
public class ProductsDeletePage extends AbstractWebPage {
	
	@FindBy(id="deleteSelectedProductsForm:deleteSelectedProductsTable")
	private WebElement tableProductsToDelete;
	
	@FindBy(id="deleteSelectedProductsForm:deleteProductsBtn")
	private WebElement buttonDelete;
	
	@FindBy(id="confirmRemoveProductsDialog_form:buttonYes")
	private WebElement buttonConfirmDelete;
	
	@FindBy(id="deleteSelectedProductsForm:deleteSelectedProductsTable:0:candidatesSize")
	private WebElement objectCount;
	
	public ProductsDeletePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	
	
	/**
	 * Determines whether product is available for deletion.
	 * 
	 * @param productCode  The code of product.
	 * @return
	 */
	public boolean isProductAvailableForDelete(String productCode) {
		return tableProductsToDelete.getText().contains(productCode);
	}
	
	/**
	 * Confirms product deletion and proceeds deleting it.
	 * @return
	 */
	public ProductsDeleteConfirmPage clickDeleteAndConfirm() {
		buttonDelete.click();
		buttonConfirmDelete.click();
		return create(ProductsDeleteConfirmPage.class);
	}
	
	public int getObjectCount() {
		return Integer.parseInt(objectCount.getText());
	}
}
