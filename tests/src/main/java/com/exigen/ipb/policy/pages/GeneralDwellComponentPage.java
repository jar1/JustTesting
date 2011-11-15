
package com.exigen.ipb.policy.pages;

import java.util.List;

import com.exigen.ipb.components.domain.OptionalQuestionAnswer;
import com.exigen.ipb.product.pages.RulesOverrideConfirmPage;
import com.exigen.ipb.product.pages.dialogs.ProductMoveToGroupPopUp;
import com.exigen.ipb.selenium.pages.PageConfiguration;

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
public class GeneralDwellComponentPage extends DataGatheringPage {
	
	private static final String CHECKED = "checked";
//	
//	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalIntExistsInd:0")
//	private WebElement additionalInterestYes;
//	
//	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalIntExistsInd:1")
//	private WebElement additionalInterestNo;
//	
	@FindBy(id="policyDataGatherForm:componentContextHolder")
	private WebElement componentsHolder;
	
	@FindBy(id="policyDataGatherForm:formGrid_Dwell")
	private WebElement componentsGrid;	
	
	@FindBy(id="policyDataGatherForm:dataGatherView_ListDwell:tb")
	private WebElement listDwell;
	
	@FindBy(id="policyDataGatherForm:addDwell")
	private WebElement addDwell;
	
	@FindBy(id="policyDataGatherForm:eliminateDwell")
	private WebElement removeDwell;
//	           
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_Dwell:0")
	private WebElement optionalQuestionAnswerYes;
	
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_Dwell:1")
	private WebElement optionalQuestionAnswerNo;
	
	@FindBy(id="confirmOptionalNoSelected_Dwell_Dialog_form:buttonYes")
	private WebElement confirmNoSelection;

	@FindBy(id="confirmEliminateInstance_Dialog_form:buttonYes")
	private WebElement confirmRemoveComponent;	

	@FindBy(id="policyDataGatherForm:save")
	private WebElement buttonSave;
	
	public GeneralDwellComponentPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}
		
	public void addDwell() {
		addDwell.click();
	}
	
	public void removeDwell() {
		removeDwell.click();
	}
	
//	public void setAdditionalInterestExists(boolean exists) {
//	if (exists) {
//		additionalInterestYes.click();
//	} else {
//		additionalInterestNo.click();
//	}
//}

//	
//	public void setTerritoryCd(String name) {
//		territoryCd.clear();
//		territoryCd.sendKeys(name);
//	}
//
//	/**
//	 * @return the itemName
//	 */
//	public WebElement getItemName() {
//		return itemName;
//	}
//	
//	public String getItemNameValue() {
//		return itemName.getAttribute("value");
//	}
	
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
	
	public boolean isOptionalQuestionLabelVisible() {
		try {
			componentsHolder.findElement(By.id("policyDataGatherForm:addOptionalQuestionLabel_Dwell"));			
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public int getDwellListSize() {
		try {			
			List<WebElement> tableRows = listDwell.findElements(By.cssSelector("tr.rich-table-row"));
			return tableRows.size();
		}	catch (NoSuchElementException e) {
			return 0;
		}	
	}
	
	public GeneralDwellComponentPage editDwellList(int row, String action) {
		List<WebElement> tableRows = listDwell.findElements(By.cssSelector("tr.rich-table-row"));		
		--row;		
		tableRows.get(row).findElement(By.id("policyDataGatherForm:dataGatherView_ListDwell:"+ row +":dataGatherView_List_"+action)).click();
		
		if(action.equals(GeneralPolicyComponentPage.LIST_REMOVE)) {
			confirmRemoveComponent.click();
			return this;
		} else if (action.equals(GeneralPolicyComponentPage.LIST_CHANGE)) {
			return this;
		}
		return null;
	}	
	
	public boolean existsField(String fieldName, String AdditionalStandard) {
		try {
			if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_STANDARD))
				componentsHolder.findElement(By.id("policyDataGatherForm:sedit_Dwell_"+fieldName));
			else if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL))
				componentsHolder.findElement(By.id("policyDataGatherForm:sedit_Dwell_additionalFields_"+fieldName));
			else
				return false;
			
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public boolean existsRemoveButton() {
		try {
			return removeDwell.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch(NullPointerException e) {
			return false;
		}
	}
	
	public boolean existsAddButton() {
		try {
			return addDwell.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public void save() {
		buttonSave.click();
	}
}
