package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class AttributeAddNewPopUp extends AbstractWebPage {
	
	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeEdit_attribute_dataType")
	private WebElement dataTypeOption;

	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeEdit_attribute_name")
	private WebElement nameInput;
	
	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeEdit_attribute_name_error")
	private WebElement nameErrorMsg;
	
	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeEdit_attribute_label")
	private WebElement labelInput;
	
	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeOk")
	private WebElement addButton;
	
	@FindBy(id="additionalAttributeConfigurationForm:addNewAttributeCancel")
	private WebElement cancelButton;
	
	public AttributeAddNewPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public AttributeAddNewPopUp setAttributeName(String name) {
		nameInput.clear();
		nameInput.sendKeys(name);
		return this;
	}
	
	public AttributeAddNewPopUp setAttributeLabel(String label) {
		labelInput.clear();
		labelInput.sendKeys(label);
		return this;
	}
	
	public AttributeAddNewPopUp setDataType(String dataType) {
		WebElement element = dataTypeOption.findElement(By.xpath("//option[@value='" + dataType + "']"));
		element.click();
		return this;
	}
	
	public boolean clickAdd() {
		addButton.click();
		if(nameErrorMsg.isDisplayed()) {
			return false;
		}
		return true;
	}	
	
	public String getNameErrorMsg() {
		if(nameErrorMsg.isDisplayed()) {
			return nameErrorMsg.getText();
		}
		return "";
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
}
