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
public class ProductMoveToGroupPopUp extends AbstractWebPage {
	
	@FindBy(id="moveToGroupSelectForm:body_moveToGroupList:tb")
	private WebElement tableAvailableRuleGroups;
	
	@FindBy(id="moveToGroupSelectForm:moveRuleToGroupConfirm")
	private WebElement buttonSave;
	
	@FindBy(id="moveToGroupSelectForm:moveRuleToGroupCancel")
	private WebElement buttonCancel;
	
	public ProductMoveToGroupPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public ProductMoveToGroupPopUp selectRuleGroup(int row) {
		tableAvailableRuleGroups.findElement(By.id("moveToGroupSelectForm:body_moveToGroupList:"+ --row +":groupSelectBox")).click();		
		return this;
	}
	
	public ProductAddEditRulePage clickSave() {
		buttonSave.click();		
		return create(ProductAddEditRulePage.class);
	}
	
	public ProductAddEditRulePage cancel() {
		buttonCancel.click();		
		return create(ProductAddEditRulePage.class);
	}
	
	
}