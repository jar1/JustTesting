package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**

 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */
public class ProductNewRootAddPopUp extends AbstractWebPage {
	
	@FindBy(id="entityConfigurationCreationForm:entityConfigurationDtoData_entityConfigName")
	private WebElement inputFieldName;	
	
	@FindBy(id="entityConfigurationCreationForm:entityConfigurationDtoData_entryPointInt:0")
	private WebElement checkBoxEtryPointYes;
	
	@FindBy(id="entityConfigurationCreationForm:entityConfigurationDtoData_entryPointInt:1")
	private WebElement checkBoxEtryPointNo;
	
	@FindBy(id="entityConfigurationCreationForm:entityConfigurationDtoData_rootEntityType")
	private WebElement dropDownType;
	
	@FindBy(id="entityConfigurationCreationForm:submitEntityConfigurationCreation")
	private WebElement buttonSave;
	
	@FindBy(id="entityConfigurationCreationForm:cancelEntityConfigurationCreation")
	private WebElement buttonCancel;		
		
	public ProductNewRootAddPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public ProductNewRootAddPopUp setName(String name) {
		inputFieldName.clear();
		inputFieldName.sendKeys(name);
		return this;
	}	
	
	public ProductNewRootAddPopUp setEntryPoint(boolean YesNo) {
		if(YesNo)
			ElementUtils.setSelected(checkBoxEtryPointYes, true);
		else
			ElementUtils.setSelected(checkBoxEtryPointNo, true);
		return this;
	}	
	
	public ProductNewRootAddPopUp setType(String type) {
		dropDownType.findElement(By.cssSelector("option[value='"+ type +"']")).click();
		return this;
	}	
	
	public ProductConsolidatedViewPage save() {
		buttonSave.click();
		return create(ProductConsolidatedViewPage.class);
	}	

	public ProductConsolidatedViewPage cancel() {
		buttonCancel.click();
		return create(ProductConsolidatedViewPage.class);
	}	
}