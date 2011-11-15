package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Page Object that represents component applicability rule dialog in alternate
 * component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AppliedRuleDialog extends AbstractParentedWebPage<AlternativeProductDataPage> {

	@FindBy(id="appliedRuleForm:currComponentName")
	private WebElement currentComponentName;
	
	@FindBy(id="appliedRuleForm:appliedRuleEdit_conditionExpression")
	private WebElement conditionExpression;
	
	@FindBy(id="appliedRuleForm:appliedRuleEdit_description")
	private WebElement description;
	
	@FindBy(id="appliedRuleForm:appliedRuleOkBtn")
	private WebElement saveButton;
	
	@FindBy(id="appliedRuleForm:appliedRuleRemoveBtn")
	private WebElement removeButton;
	
	@FindBy(id="appliedRuleForm:appliedRuleCancelBtn")
	private WebElement cancelButton;
	
	public AppliedRuleDialog(WebDriver driver, PageConfiguration conf,
			AlternativeProductDataPage parent) {
		super(driver, conf, parent);
	}
	
	public boolean isDisplayed() {
		return saveButton.isDisplayed();
	}
	
	public String getCurrentComponentName() {
		return currentComponentName.getText();
	}
	
	public String getConditionExpression() {
		return conditionExpression.getText();
	}
	
	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression.clear();
		this.conditionExpression.click();
		this.conditionExpression.sendKeys(conditionExpression);
	}
	
	public String getDescription() {
		return description.getText();
	}

	public void setDescription(String description) {
		this.description.clear();
		this.description.click();
		this.description.sendKeys(description);
	}
	
	public void clickSave() {
		saveButton.click();
	}
	
	public void remove() {
		removeButton.click();
	}
	
	public void clickCancel() {
		cancelButton.click();
	}
}
