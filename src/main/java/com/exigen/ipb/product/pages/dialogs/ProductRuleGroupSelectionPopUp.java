package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductAddEditRulePage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductRuleGroupSelectionPopUp extends AbstractWebPage {
	
	@FindBy(id="markerSelectForm:body_markerList:tb")
	private WebElement tableAvailableRuleGroups;
	
	@FindBy(id="markerSelectForm:groupDlgAddGroupsToRule")
	private WebElement buttonAddGroups;
	
	@FindBy(id="markerSelectForm:groupDlgSaveNewExtension")
	private WebElement buttonSaveNewGroup;	
	
	@FindBy(id="markerSelectForm:groupDlgCancel")
	private WebElement buttonCancel;
	
	public ProductRuleGroupSelectionPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public ProductRuleGroupSelectionPopUp selectRuleGroup(int row) {
		tableAvailableRuleGroups.findElement(By.id("markerSelectForm:body_markerList:"+ --row +":groupSelectBox")).click();		
		return this;
	}
	
	public ProductAddEditRulePage clickSaveNewGroup() {
		buttonSaveNewGroup.click();		
		return create(ProductAddEditRulePage.class);
	}
	
	public ProductAddEditRulePage clickAddNewGroups() {
		buttonAddGroups.click();		
		return create(ProductAddEditRulePage.class);
	}
	
	public ProductAddEditRulePage clickCancel() {
		buttonCancel.click();		
		return create(ProductAddEditRulePage.class);
	}
	
	
}