package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Class that represents component merge page (replace). Used in Selenium2 tests.
 * 
 * @author mulevicius
 * @since 3.9
 */
public class ProductMergePage extends AbstractWebPage {

	@FindBy(id="diffElementsForm:merge")
	private WebElement mergeButton;
	
	@FindBy(id="topCancelLink")
	private WebElement cancelButton;
	
	@FindBy(id="expand-all")
	private WebElement expandAllButton;
	
	@FindBy(id="collapse-all")
	private WebElement collapseAllButton;
	
	@FindBy(id="check-all")
	private WebElement checkAllButton;
	
	@FindBy(id="uncheck-all")
	private WebElement uncheckAllButton;
	
	public ProductMergePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public AlternativeProductDataPage merge() {
		mergeButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public void expandAll() {
		expandAllButton.click();
	}
	
	public void collapseAll() {
		collapseAllButton.click();
	}
	
	public void checkAll() {
		checkAllButton.click();
	}
	
	public void uncheckAll() {
		uncheckAllButton.click();
	}
	
	
}
