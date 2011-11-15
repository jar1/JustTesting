
package com.exigen.ipb.policy.pages;

import java.util.List;

import com.exigen.ipb.components.domain.OptionalQuestionAnswer;
import com.exigen.ipb.product.pages.RulesOverrideConfirmPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author ggrazevicius
 * @author jdaskevicius
 * @since 3.9
 */
public class GeneralRiskItemComponentPage extends DataGatheringPage implements LandingPage {
	
	private static final String CHECKED = "checked";
	
	@FindBy(id="policyDataGatherForm:componentContextHolder")
	private WebElement componentHolder;	
	
	@FindBy(id="policyDataGatherForm:dataGatherView_ListRiskItem:tb")
	private WebElement listRiskItem;	
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_itemName")
	private WebElement itemName;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_itemName_label")
	private WebElement itemNameLabel;

	@FindBy(id="policyDataGatherForm:sedit_RiskItem_territoryCd")
	private WebElement territoryCd;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalFields_Test1_stringValue")
	private WebElement fieldTest1;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalFields_Test2_stringValue")
	private WebElement fieldTest2;			
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalIntExistsInd:0")
	private WebElement additionalInterestYes;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalIntExistsInd:1")
	private WebElement additionalInterestNo;
	
	@FindBy(id="policyDataGatherForm:addRiskItem")
	private WebElement addRiskItem;
	
	@FindBy(id="policyDataGatherForm:eliminateRiskItem")
	private WebElement removeRiskItem;
	
	@FindBy(id="confirmEliminateInstance_Dialog_form:buttonYes")
	private WebElement confirmRemoveComponent;	
	
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_RiskItem:0")
	private WebElement optionalQuestionAnswerYes;
	
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_RiskItem:1")
	private WebElement optionalQuestionAnswerNo;
	
	@FindBy(id="confirmOptionalNoSelected_RiskItem_Dialog_form:buttonYes")
	private WebElement confirmNoSelection;
	
	@FindBy(id="policyDataGatherForm:overrideRules")
	private WebElement buttonOverride;
	
	@FindBy(id="policyDataGatherForm:save")
	private WebElement buttonSave;
	
	public GeneralRiskItemComponentPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}
	   
	public GeneralRiskItemComponentPage setAdditionalInterestExists(boolean exists) {
		if (exists) {
			additionalInterestYes.click();
		} else {
			additionalInterestNo.click();
		}
		return this;
	}
	
	public GeneralRiskItemComponentPage addRiskItem() {
		addRiskItem.click();
		return this;
	}
	
	public GeneralRiskItemComponentPage removeRiskItem() {
		removeRiskItem.click();
		return this;
	}
	
	public GeneralRiskItemComponentPage setItemName(String name) {
		ElementUtils.setInputValue(itemName, name);
		return this;
	}
	
	public GeneralRiskItemComponentPage setTest1(String name) {
		ElementUtils.setInputValue(fieldTest1, name);
		return this;
	}	
	
	public GeneralRiskItemComponentPage setTerritoryCd(String name) {
		ElementUtils.setInputValue(territoryCd, name);
		return this;
	}

	/*
	public WebElement getItemName() {
		return itemName;
	}
	
	public WebElement getTest1() {		
		return fieldTest1;
	}
	
	public WebElement getTest2() {
		return fieldTest2;
	}
	*/
	
	public String getItemNameValue() {
		return ElementUtils.getInputValue(itemName);
	}
	
	public String getTest1Value() {
		return ElementUtils.getInputValue(fieldTest1);
	}
	
	public String getTest2Value() {
		return ElementUtils.getInputValue(fieldTest2);
	}
	
	public OptionalQuestionAnswer getOptionalQuestionAnswer() {
		if (Boolean.valueOf(optionalQuestionAnswerYes.getAttribute(CHECKED))) {
			return OptionalQuestionAnswer.Yes;
		} else if (Boolean.valueOf(optionalQuestionAnswerNo.getAttribute(CHECKED))) {
			return OptionalQuestionAnswer.No;
		} else {
			return OptionalQuestionAnswer.None;
		}
	}
	
	public void setOptionalQuestionAnswer(OptionalQuestionAnswer answer) {
		switch (answer) {
		case Yes:
			optionalQuestionAnswerYes.click();
			break;
		
		case No:
			optionalQuestionAnswerNo.click();
			confirmNoSelection.click();
			break;
			
		case None:
			throw new IllegalArgumentException("Can not set to 'None'");

		default:
			break;
		}
	}
	
	public boolean isItemNameLabelVisible() {
		return ElementUtils.isDisplayed(itemNameLabel);
	}
	
	public boolean existsRemoveRiskItemButton() {
		return ElementUtils.isDisplayed(removeRiskItem);
	}
	
	public boolean existsAddRiskItemButton() {
		return ElementUtils.isDisplayed(addRiskItem);
	}
	
	public boolean existsField(String fieldName, String AdditionalStandard) {
		try {
			if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_STANDARD))
				componentHolder.findElement(By.id("policyDataGatherForm:sedit_RiskItem_"+fieldName));
			else if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL))
				componentHolder.findElement(By.id("policyDataGatherForm:sedit_RiskItem_additionalFields_"+fieldName));
			else
				return false;
			
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public GeneralRiskItemComponentPage typeInField(String fieldName, String fieldValue) {
		WebElement field = componentHolder.findElement(By.id("policyDataGatherForm:sedit_RiskItem_"+fieldName));
				
		field.clear();
		field.sendKeys(fieldValue);			
			
		return create(GeneralRiskItemComponentPage.class);
	
	}

	public int getRiskItemListSize() {
		try {			
			List<WebElement> tableRows = listRiskItem.findElements(By.cssSelector("tr.rich-table-row"));
			return tableRows.size();
		}	catch (NoSuchElementException e) {
			return 0;
		}	
	}
	
	public GeneralRiskItemComponentPage editRiskItemList(int row, String action) {
		List<WebElement> tableRows = listRiskItem.findElements(By.cssSelector("tr.rich-table-row"));		
		--row;		
		tableRows.get(row).findElement(By.id("policyDataGatherForm:dataGatherView_ListRiskItem:"+ row +":dataGatherView_List_"+action)).click();
		
		if(action.equals(GeneralPolicyComponentPage.LIST_REMOVE)) {
			confirmRemoveComponent.click();
			return this;
		} else if (action.equals(GeneralPolicyComponentPage.LIST_CHANGE)) {
			return this;
		}
		return null;
	}	
	
	public RulesOverrideConfirmPage clickOverride() {
		buttonOverride.click();
		return create(RulesOverrideConfirmPage.class);
	}
	
	public GeneralRiskItemComponentPage clickSave() {
		buttonSave.click();
		return this;
	}
}
