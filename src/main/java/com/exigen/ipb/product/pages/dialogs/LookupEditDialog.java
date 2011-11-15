package com.exigen.ipb.product.pages.dialogs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object that represents lookup edit dialog of selected attribute.
 * This dialog is activated from {@link LookupInfoDialog}.
 * 
 * @author gzukas
 * @since 3.9
 */
public class LookupEditDialog extends AbstractParentedWebPage<LookupInfoDialog> {

	@FindBy(id="alternateLookupEditPopupContentTable")
	private WebElement dialog;
	
	@FindBy(id="alternateLookupEditForm:lookupTemplate")
	private WebElement lookupTemplateElement;
	
	private Select lookupTemplate = new Select(lookupTemplateElement);
	
	@FindBy(id="alternateLookupEditForm:lookupName")
	private WebElement lookupName;
	
	@FindBy(id="alternateLookupEditForm:lookupBindingsTable")
	private WebElement lookupBindingTable;
	
	@FindBy(id="alternateLookupEditForm:okBtn")
	private WebElement okButton;
	
	@FindBy(id="alternateLookupEditForm:cancelBtn")
	private WebElement cancelButton;
	
	public LookupEditDialog(WebDriver driver, PageConfiguration conf,
			LookupInfoDialog parent) {
		
		super(driver, conf, parent);
	}
	
	public boolean isDisplayed() {
		return dialog.isDisplayed();
	}
	
	public String getLookupTemplate() {
		return lookupTemplate.getFirstSelectedOption().getText();
	}
	
	public void selectLookupTemplate(String value) {
		lookupTemplate.selectByValue(value);
	}
	
	public String getLookupName() {
		return ElementUtils.getInputValue(lookupName);
	}
	
	public void setLookupName(String value) {
		lookupName.clear();
		lookupName.sendKeys(value);
	}
	
	public boolean isLookupTemplateEnabled() {
		return lookupTemplateElement.isEnabled();
	}
	
	public boolean isLookupNameEnabled() {
		return lookupName.isEnabled();
	}
	
	public LookupEditDialog fillRandomLookupBindings() {
		List<WebElement> inputs = lookupBindingTable.findElements(By.tagName("input"));
		for (WebElement input : inputs) {
			String randomValue = String.valueOf((int)(Math.random() * 100000));
			input.clear();
			input.sendKeys(randomValue);
		}
		return this;
	}
	
	public void clickOk() {
		okButton.click();
	}
	
	public void clickCancel() {
		cancelButton.click();
	}

}
