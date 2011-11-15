package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductAddEditTabVisibilityRulePage extends AbstractWebPage {
	
// --------
	@FindBy(id="productUpdateForm:tabVisRuleName")
	private WebElement txtFieldRuleName;
	
	@FindBy(id="productUpdateForm:enabled")
	private WebElement checkBoxRuleEnabled;

	@FindBy(id="productUpdateForm:description")
	private WebElement txtFieldDescription;
	
	@FindBy(id="productUpdateForm:expression")
	private WebElement txtFieldExpression;
	
	@FindBy(id="productUpdateForm:deleteInstances")
	private WebElement checkBoxDeleteInstance;

	@FindBy(id="productUpdateForm:Next")
	private WebElement buttonNext;	
	
	@FindBy(id="topCancelLink")
	private WebElement buttonCancelTop;

	public ProductAddEditTabVisibilityRulePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	
	
	public ProductAddEditTabVisibilityRulePage setEnabled(boolean st) {
		ElementUtils.setSelected(checkBoxRuleEnabled, st);
		
		return this;
	}
	
	public ProductAddEditTabVisibilityRulePage setDeleteInstance(boolean st) {
		ElementUtils.setSelected(checkBoxDeleteInstance, st);
		
		return this;
	}
	
	public ProductAddEditTabVisibilityRulePage setName(String ruleName) {
		txtFieldRuleName.clear();
		txtFieldRuleName.sendKeys(ruleName);
		
		return this;
	}
	
	public ProductAddEditTabVisibilityRulePage setDescription(String ruleDescription) {
		txtFieldDescription.clear();
		txtFieldDescription.sendKeys(ruleDescription);
		
		return this;
	}
	
	public ProductAddEditTabVisibilityRulePage setExpression(String ruleExpression) {
		txtFieldExpression.clear();
		txtFieldExpression.sendKeys(ruleExpression);
		
		return this;
	}
	
	public String getName(String ruleExpression) {
		return txtFieldRuleName.getText();
	}	
	
	public ProductRulesConfigurationPage clickNext() {
		buttonNext.click();
		
		return create(ProductRulesConfigurationPage.class);
	}
	
	public ProductRulesConfigurationPage clickButtonCancel() {
		buttonCancelTop.click();
		
		return create(ProductRulesConfigurationPage.class);
	}
}