package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.ProductRuleGroupSelectionPopUp;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.PageUtil;

/**
 * Class that represents rules add/edit configuration screen. Used in 
 * Selenium2 tests.
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductAddEditRulePage extends AbstractWebPage {

	public static final int CONDITIONS_HIDDEN   = 1;
	public static final int CONDITIONS_DISABLED = 2;
	public static final int CONDITIONS_REQUIRED = 4;
	
	public static final int OVERRIDES_DEFAULT   = 1;
	public static final int OVERRIDES_WARNING   = 2;
	
	private static final String PRODUCT_UPDATE_FORM = "productUpdateForm:%s";
	
	public enum RuleFields {
		DescriptionText, ConditionExpressionText, RuleAssertionExpressionText, DefExpressionText, ErrorMsgText
	}
	
// --------
	@FindBy(id="productUpdateForm:RuleNameImp")
	private WebElement txtFieldRuleName;
	
	@FindBy(id="productUpdateForm:RuleDisabledInd:0")
	private WebElement radioRuleDisabledYes;

	@FindBy(id="productUpdateForm:RuleDisabledInd:1")
	private WebElement radioRuleDisabledNo;
	
	@FindBy(id="productUpdateForm:ruleOverrideChBox")
	private WebElement checkBoxOverride;
	
	@FindBy(id="productUpdateForm:underwriting_authority_level")
	private WebElement dropDownOverrideAuthorityLevel;

	@FindBy(id="productUpdateForm:enableGlobal")
	private WebElement buttonEnableGlobal;	
	
	@FindBy(id="confirmEnableGlobalRuleDialog_form:buttonYes")
	private WebElement buttonEnableGlobalYes;	
	
	@FindBy(id="confirmEnableGlobalRuleDialog_form:buttonNo")
	private WebElement buttonEnableGlobalNo;

	@FindBy(id="productUpdateForm:disableGlobal")
	private WebElement buttonDisableGlobal;
	
	@FindBy(id="confirmDisableGlobalRuleDialog_form:buttonYes")
	private WebElement buttonDisableGlobalYes;	
	
	@FindBy(id="confirmDisableGlobalRuleDialog_form:buttonNo")
	private WebElement buttonDisableGlobalNo;
	
	@FindBy(id="productUpdateForm:RuleGlobalInd")
	private WebElement indicatorGlobalRule;

	@FindBy(id="productUpdateForm:globalEffectiveDate")
	private WebElement txtFieldGlobalEffectiveDate;
	
	@FindBy(id="productUpdateForm:showGroupDialogForAdd")
	private WebElement buttonCreateExtensions;	
	
	@FindBy(id="productUpdateForm:showGroupDialog")
	private WebElement buttonAddGroupToRule;	
	
	@FindBy(id="productUpdateForm:existingRuleExtensionsMenuItem_header")
	private WebElement panelExistingRuleExtensions;	
	
	@FindBy(id="productUpdateForm:relatedRuleGroups")
	private WebElement extensionsTable;
	
	@FindBy(id="productUpdateForm:navigateToBaseRuleLink")
	private WebElement linkNavigateToBaseRule;		
	
	@FindBy(id="productUpdateForm")
	private WebElement formProductUpdate;		
	
	@FindBy(id="productUpdateForm:body_actionEventTable:tb")
	private WebElement actionsTable;	
	
	@FindBy(id="prettyMuchUselessForm_DoNotRemove:OKbtn")
	private WebElement buttonRemoveExtensionYes;
	
	@FindBy(id="prettyMuchUselessForm_DoNotRemove:CancelBtn")
	private WebElement buttonRemoveExtensionNo;		
	
// --------
	@FindBy(id="productUpdateForm:DescriptionText")
	private WebElement txtFieldDescription;

	@FindBy(id="productUpdateForm:ConditionExpressionText")
	private WebElement txtFieldConditionExpression;

	@FindBy(id="productUpdateForm:RuleAssertionExpressionText")
	private WebElement txtFieldAssertionExpression;

	@FindBy(id="productUpdateForm:DefExpressionText")
	private WebElement txtFieldDefaulValueExpression;

	@FindBy(id="productUpdateForm:ErrorMsgText")
	private WebElement txtFieldError;	

	@FindBy(id="productUpdateForm:HiddenInd")
	private WebElement checkHidden;

	@FindBy(id="productUpdateForm:DisabledInd")
	private WebElement checkDisabled;

	@FindBy(id="productUpdateForm:Required")
	private WebElement checkRequired;

	@FindBy(id="productUpdateForm:OverrideInd")
	private WebElement checkDefault;
	
	@FindBy(id="productUpdateForm:WarningInd")
	private WebElement checkWarning;
	
// -------- 
	@FindBy(id="productUpdateForm:Next")
	private WebElement buttonNext;
	
	@FindBy(id="topCancelLink")
	private WebElement buttonCancel;	
		
	public ProductAddEditRulePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	
	
	public ProductAddEditRulePage setRuleName(String name) {
		PageUtil.waitTillElementLoaded(getDriver(), getWait(), By.id("productUpdateForm:RuleNameImp"));
		txtFieldRuleName.clear();
		txtFieldRuleName.sendKeys(name);
		
		return this;
	}
	
	public ProductAddEditRulePage setDisabled(boolean st) {
		if(st)
			ElementUtils.setSelected(radioRuleDisabledYes, true);
		else
			ElementUtils.setSelected(radioRuleDisabledNo, true);
		
		return this;
	}
	
	public ProductAddEditRulePage setOverride(boolean st) {
		ElementUtils.setSelected(checkBoxOverride, st);
		return this;
	}
	
	public boolean getOverride() {
		return checkBoxOverride.isSelected();
	}
	
	public ProductAddEditRulePage setAuthorityLevel(int level) {
		dropDownOverrideAuthorityLevel.findElement(By.cssSelector("option[value='"+ level +"']")).click();
		
		return this;
	}	
	
	public ProductAddEditRulePage setGlobalEnable(boolean YesNo) {
		buttonEnableGlobal.click();
		
		if(YesNo)
			buttonEnableGlobalYes.click();
		else
			buttonEnableGlobalNo.click();
		
		return this;
	}
	
	public ProductAddEditRulePage setGlobalDisable(boolean YesNo) {
		buttonDisableGlobal.click();
		
		if(YesNo)
			buttonDisableGlobalYes.click();
		else
			buttonDisableGlobalNo.click();
		
		return this;
	}
	
	public ProductAddEditRulePage setGlobalEffDate(String date) {
		ElementUtils.setInputValue(txtFieldGlobalEffectiveDate, date);
		return this;
	}
	
	public ProductAddEditRulePage setFieldValue(RuleFields fieldName, String text) {
		String fieldId = String.format(PRODUCT_UPDATE_FORM, fieldName);
		WebElement field = formProductUpdate.findElement(By.id(fieldId));
		ElementUtils.setInputValue(field, text);
		return this;
	}
	
	public String getAssertionExpression() {
		return txtFieldAssertionExpression.getText();
	}
	
	public boolean isGlobal() {
		if(indicatorGlobalRule.getText().equals("Yes"))
			return true;
		else
			return false;		
	}
	
	public ProductAddEditRulePage setConditions(int conditions) {		
		
		ElementUtils.setSelected(checkHidden, false);
		ElementUtils.setSelected(checkDisabled, false);
		ElementUtils.setSelected(checkRequired, false);
		
		if((conditions & CONDITIONS_HIDDEN) != 0) {
			ElementUtils.setSelected(checkHidden, true);
		} 
		if((conditions & CONDITIONS_DISABLED) != 0) {
			ElementUtils.setSelected(checkDisabled, true);
		} 
		if((conditions & CONDITIONS_REQUIRED) != 0) {
			ElementUtils.setSelected(checkRequired, true);
		}
		
		return this;
	}
	
	public ProductAddEditRulePage setOverrides(int conditions) {		
		
		ElementUtils.setSelected(checkDefault, false);
		ElementUtils.setSelected(checkWarning, false);
		
		if((conditions & OVERRIDES_DEFAULT) != 0) {
			ElementUtils.setSelected(checkDefault, true);
		} 
		if((conditions & OVERRIDES_WARNING) != 0) {
			ElementUtils.setSelected(checkWarning, true);
		}
		
		return this;
	}	
		
	public ProductAddEditRulePage selectAction(String action) {	
		String typeAction = "Action";		
		List<WebElement> tableRows =
				actionsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement eventAction = table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":column_eventAction"));
			if(eventAction.getText().equals(typeAction)) {				
				WebElement td = table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":column_displayName"));			
				if (td.getText().contains(action)) {
					table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":selectedActionEvent")).click();
					return create(ProductAddEditRulePage.class);					
				}				
			}
			i++;
		}
		return this;
	}	
	
	public ProductAddEditRulePage selectEvent(String action) {	
		String typeAction = "Event";		
		List<WebElement> tableRows =
				actionsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement eventAction = table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":column_eventAction"));
			if(eventAction.getText().equals(typeAction)) {				
				WebElement td = table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":column_displayName"));			
				if (td.getText().contains(action)) {
					table.findElement(By.id("productUpdateForm:body_actionEventTable:"+ i +":selectedActionEvent")).click();
					return create(ProductAddEditRulePage.class);					
				}				
			}
			i++;
		}
		return this;
	}	
	
	public ProductRuleGroupSelectionPopUp clickCreateExtensionsForGroup() {		
		buttonCreateExtensions.click();
		return create(ProductRuleGroupSelectionPopUp.class);
	}	
	
	public ProductRuleGroupSelectionPopUp clickAddGroupToRule() {		
		buttonAddGroupToRule.click();
		return create(ProductRuleGroupSelectionPopUp.class);
	}	
		
	public ProductAddEditRulePage createExtensionsForGroup(int row) {		
		clickCreateExtensionsForGroup().selectRuleGroup(row).clickSaveNewGroup();
		return this;
	}
	
	public ProductAddEditRulePage clickNavigateToBaseRule() {		
		linkNavigateToBaseRule.click();
		return this;
	}
	
	public ProductAddEditRulePage expandRuleExtensions() {		
		//if (!extensionsTable.isDisplayed()) {
			panelExistingRuleExtensions.click();
		//}
		return this;
	}
	
	public List<String> getRuleExtensions() {		
		List<WebElement> extensionsList = extensionsTable.findElements(By.cssSelector("tr.rich-table-row"));
		List<String> extensionsElements = new ArrayList<String>();	
		
		int i = 0;
		for (WebElement arr : extensionsList) {
			WebElement rowName = arr.findElement(By.id("productUpdateForm:body_relatedRuleGroups:"+ i +":someId"));
			extensionsElements.add(rowName.getText());
			i++;
		}
		return extensionsElements;
	}
	
	public ProductAddEditRulePage editRuleExtension(String name) {
		List<WebElement> tableRows;
		
		try {
		tableRows = extensionsTable.findElements(By.cssSelector("tr.rich-table-row"));
		}
		catch (NoSuchElementException e) {
			return null;		
		}

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement rowName = table.findElement(By.id("productUpdateForm:body_relatedRuleGroups:"+ i +":someId"));			
			if (rowName.getText().contains(name)) {				
				table.findElement(By.id("productUpdateForm:body_relatedRuleGroups:"+ i +":editRuleLink")).click();
				return this;
			}
			i++;
		}
		return null;
	}
	
	public ProductAddEditRulePage deleteRuleExtension(String name, boolean YesNo) {
		List<WebElement> tableRows = extensionsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("productUpdateForm:body_relatedRuleGroups:"+ i +":someId"));			
			if (td.getText().contains(name)) {				
				td.findElement(By.cssSelector("a")).click();
				if(YesNo)
					buttonRemoveExtensionYes.click();
				else
					buttonRemoveExtensionNo.click();					
				return this;
			}
			i++;
		}
		return null;
	}	
	
	public boolean checkAppliesToGroup(String extensionName) {
		WebElement appliesToGroups = formProductUpdate.findElement(By.cssSelector("span.ruleBlockHeader tr:nth-child(7) td:nth-child(2)"));

		if (appliesToGroups.getText().equals(extensionName)) {
			return true;
		} else {
			return false;
		}
	}	
	
	public ProductRulesConfigurationPage clickCancel() {
		buttonCancel.click();
		return create(ProductRulesConfigurationPage.class);
	}
	
	public ProductRulesConfigurationPage clickNext() {
		buttonNext.click();
		return create(ProductRulesConfigurationPage.class);
	}
}
