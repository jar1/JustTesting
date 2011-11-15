package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductMergePage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Class that represents initial pop up for component replace (merge) confirmation. Used in Selenium2 tests.
 * 
 * mulevicius: technically this "page" (popup) should be included in AlternativeProductDataPage, 
 * but due to expected bloatness of AlternativeProductDataPage it will have its own class.
 * 
 * @author mulevicius
 * @since 3.9
 */
public class ProductConfirmMergePopUp extends AbstractWebPage {
	
	@FindBy(id="replaceConfigForm:diffBtn")
	private WebElement replaceButton;
	
	@FindBy(id="replaceConfigForm:cancelBtn")
	private WebElement cancelButton;
	
	@FindBy(id="replaceConfigForm:selectNewCmp")
	private WebElement diffChooseBox;
	
	public ProductConfirmMergePopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public boolean setComponentNameInOptionBox(String componentName) {
		try {
			WebElement diffComponent = diffChooseBox.findElement(By.cssSelector("option[value='"+ componentName +"']"));
			if(diffComponent != null) {			
				diffComponent.click();
				return true;
			}
			return false;
		} catch(NoSuchElementException ex) {
			return false;
		}
	}
	
	public ProductMergePage replace() {
		replaceButton.click();
		return create(ProductMergePage.class);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
}
