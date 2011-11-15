
package com.exigen.ipb.policy.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.exigen.ipb.selenium.pages.PageConfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 *

 * @author jdaskevicius
 * @since 3.9
 */
public class GeneralStateSpecificRiskItemComponentPage extends DataGatheringPage {
	
	private static final String CHECKED = "checked";
	
	@FindBy(id="policyDataGatherForm:formGrid_RiskItem")
	private WebElement formRiskItem;
	
	@FindBy(id="policyDataGatherForm:componentContextHolder")
	private WebElement componentHolder;	
	
	@FindBy(id="policyDataGatherForm:dataGatherView_ListRiskItem:tb")
	private WebElement listRiskItem;	
	
	@FindBy(id="policyDataGatherForm:sedit_StateRiskItem_itemName")
	private WebElement itemName;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_territoryCd")
	private WebElement territoryCd;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalFields_Test1_stringValue")
	private WebElement fieldTest1;
	
	@FindBy(id="policyDataGatherForm:sedit_RiskItem_additionalFields_Test2_stringValue")
	private WebElement fieldTest2;			
	
	@FindBy(id="policyDataGatherForm:sedit_StateSpecificRiskItem_additionalIntExistsInd:0")
	private WebElement additionalInterestYes;
	
	@FindBy(id="policyDataGatherForm:sedit_StateSpecificRiskItem_additionalIntExistsInd:1")
	private WebElement additionalInterestNo;

	@FindBy(id="policyDataGatherForm:save")
	private WebElement buttonSave;
	
	@FindBy(id="topSaveAndExitLink")
	private WebElement buttonSaveAndExit;	
	
	@FindBy(id="policyDataGatherForm:next")
	private WebElement buttonNext;
	
	
	public GeneralStateSpecificRiskItemComponentPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}
	
	public GeneralStateSpecificRiskItemComponentPage setAdditionalInterestExists(boolean exists) {
		if (exists) {
			additionalInterestYes.click();
		} else {
			additionalInterestNo.click();
		}
		
		return create(GeneralStateSpecificRiskItemComponentPage.class);	
	}
	
	public GeneralStateSpecificRiskItemComponentPage setField(String fieldName, String fieldValue) {
		WebElement field = componentHolder.findElement(By.id("policyDataGatherForm:sedit_StateSpecificRiskItem_"+fieldName));
				
		field.clear();
		field.sendKeys(fieldValue);			
			
		return create(GeneralStateSpecificRiskItemComponentPage.class);	
	}
	
	public GeneralStateSpecificRiskItemComponentPage clearField(String fieldName) {
		WebElement field = componentHolder.findElement(By.id("policyDataGatherForm:sedit_StateSpecificRiskItem_"+fieldName));
				
		field.clear();
			
		return create(GeneralStateSpecificRiskItemComponentPage.class);	
	}
	
	public GeneralStateSpecificRiskItemComponentPage selectInField(String fieldName, String fieldValue) {
		WebElement field = componentHolder.findElement(By.id("policyDataGatherForm:sedit_StateSpecificRiskItem_"+fieldName));
				
		new Select(field).selectByValue(fieldValue);
			
		return create(GeneralStateSpecificRiskItemComponentPage.class);	
	}
	
	public List<String> getErrorList() {
		List<String> errorList = new ArrayList<String>();	
		try {
			List<WebElement> errorRows = componentHolder.findElements(By.cssSelector("ul li"));		
			
			for (WebElement table : errorRows)
				errorList.add(table.getText());
			
			return errorList;
		}
		catch (NoSuchElementException e) {
			errorList.clear();
			return errorList;
		}
	}
	
	public HashSet<String> getErrorSet() {
		return new HashSet<String>(getErrorList());
	}
	
//	public List<String> getSearchResultCodes() {
//		List<String> foundList = new ArrayList<String>();
//				
//		try {
//			productFoundTable.findElements(By.cssSelector("tr.rich-table-row"));
//		}
//		catch (NoSuchElementException e){
//			foundList.clear();
//			return foundList;
//		}
//		
//		List<WebElement> tableRows = productFoundTable.findElements(By.cssSelector("tr.rich-table-row"));
//		int i = 0;
//		for (WebElement table : tableRows) {
//			foundList.add(table.findElement(By.id("searchForm:body_foundProducts:"+ i +":column_productCd")).getText());
//			i++;
//		}
//		return foundList;
//	}
//	
	
	public void saveAndExit() {
		buttonSaveAndExit.click();
	}
	
	public void next() {
		buttonNext.click();
	}
}
