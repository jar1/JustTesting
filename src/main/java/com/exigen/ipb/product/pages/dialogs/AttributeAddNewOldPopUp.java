package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class AttributeAddNewOldPopUp extends AbstractWebPage {
	
	@FindBy(id="addNewAttributeForm:addNewAttributeEdit_attribute_dataType")
	private WebElement dataTypeOption;

	@FindBy(id="addNewAttributeForm:addNewAttributeEdit_attribute_name")
	private WebElement nameInput;

	@FindBy(id="addNewAttributeForm:addNewAttributeEdit_attribute_label")
	private WebElement labelInput;
	
	@FindBy(id="addNewAttributeForm:addNewAttributeOk")
	private WebElement addButton;
	
	@FindBy(id="addNewAttributeForm:addNewAttributeCancel")
	private WebElement cancelButton;	
	
	
	public AttributeAddNewOldPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public AttributeAddNewOldPopUp setAttributeName(String name) {
		nameInput.clear();
		nameInput.sendKeys(name);
		return this;
	}
	
	public AttributeAddNewOldPopUp setAttributeLabel(String label) {
		labelInput.clear();
		labelInput.sendKeys(label);
		return this;
	}
	
	public AttributeAddNewOldPopUp setDataType(String dataType) {		
		dataTypeOption.findElement(By.cssSelector("option[value='"+ dataType +"']")).click();

		return this;
	}
	
	public ProductComponentsConfigurationPage add() {
		addButton.click();

		return create(ProductComponentsConfigurationPage.class);
	}	
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
}
