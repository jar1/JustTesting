package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object that represents product expiration dialog in product search page.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ProductExpirationDialog extends AbstractParentedWebPage<ProductSearchPage> {

	@FindBy(id="editProductExpDateForm:effective")
	private WebElement effectiveDate;
	
	@FindBy(id="editProductExpDateForm:newProductExpDate_expiration")
	private WebElement expirationDate;
	
	@FindBy(id="editProductExpDateForm:productExpDateEditButton")
	private WebElement okButton;
	
	@FindBy(id="editProductExpDateForm:cancelBtn")
	private WebElement cancelButton;
	
	public ProductExpirationDialog(WebDriver driver, PageConfiguration conf, ProductSearchPage parent) {
		super(driver, conf, parent);
	}
	
	public String getEffectiveValue() {
		return effectiveDate.getText();
	}
	
	public String getExpirationValue() {
		return ElementUtils.getInputValue(expirationDate);
	}
	
	public void setExpirationValue(String value) {
		ElementUtils.setInputValue(expirationDate, value);
	}
	
	public ProductSearchPage clickOk() {
		okButton.click();
		return parent();
	}
	
	public ProductSearchPage clickCancel() {
		cancelButton.clear();
		return parent();
	}
}
