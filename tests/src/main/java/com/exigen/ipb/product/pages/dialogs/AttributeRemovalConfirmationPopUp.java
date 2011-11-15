package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class AttributeRemovalConfirmationPopUp extends AbstractWebPage {
	
	@FindBy(id="attributeRemovalForm:affectedRulesDataList")
	private WebElement listDependRules;
	
	@FindBy(id="attributeRemovalForm:attributeRemovalDialogRemove")
	private WebElement yesButton;
	
	@FindBy(id="attributeRemovalForm:attributeRemovalDialogCancel")
	private WebElement cancelButton;
	
	public AttributeRemovalConfirmationPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public List<String> getDependRules() {
		List<WebElement> tableRows = listDependRules.findElements(By.cssSelector("li.rich-list-item"));
		List<String> dependRulesList = new ArrayList<String>();	

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("attributeRemovalForm:affectedRulesDataList:"+ i +":businessRule"));			
			dependRulesList.add(td.getText());			
			i++;
		}
		return dependRulesList;
	}	
	
	public AlternativeProductDataPage remove() {
		yesButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public ProductComponentsConfigurationPage clickYes() {
		yesButton.click();		
		return create(ProductComponentsConfigurationPage.class);
	}
	
	public ProductComponentsConfigurationPage clickNo() {
		cancelButton.click();
		return create(ProductComponentsConfigurationPage.class);
	}
	
}
