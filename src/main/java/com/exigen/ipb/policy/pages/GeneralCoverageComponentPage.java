
package com.exigen.ipb.policy.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.components.domain.OptionalQuestionAnswer;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Coverage component page for selenium test page
 * 
 * @author sstundzia
 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */
public class GeneralCoverageComponentPage extends DataGatheringPage {
	
	private static final String CHECKED = "checked";
	
	@FindBy(id="policyDataGatherForm:componentContextHolder")
	private WebElement componentHolder;	
	
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_Coverage:0")
	private WebElement optionalQuestionAnswerYes;
	
	@FindBy(id="policyDataGatherForm:addOptionalQuestion_Coverage:1")
	private WebElement optionalQuestionAnswerNo;
	
	@FindBy(id="confirmOptionalNoSelected_Coverage_Dialog_form:buttonYes")
	private WebElement confirmNoSelection;
	
	@FindBy(id="policyDataGatherForm:sedit_Coverage_limitAmount")
	private WebElement limitAmountValue;
	
	@FindBy(id="policyDataGatherForm:sedit_Coverage_coverageCd")
	private WebElement mandatoryCoverageCd;
	
	@FindBy(id="policyDataGatherForm:sedit_Coverage_deductibleAmount")
	private WebElement inputFieldDeductibleAmount;
	
	@FindBy(id="policyDataGatherForm:sedit_Coverage_deductibleTypeCd")
	private WebElement inputFieldDeductibleTypeCd;
	
	@FindBy(id="policyDataGatherForm:sedit_Coverage_coverLevelCd")
	private WebElement inputFieldCoverLevelCd;
	
	public GeneralCoverageComponentPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
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
	
	public boolean existsField(String fieldName, String AdditionalStandard) {
		try {
			if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_STANDARD))
				componentHolder.findElement(By.id("policyDataGatherForm:sedit_Coverage_"+fieldName));
			else if(AdditionalStandard.equals(GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL))
				componentHolder.findElement(By.id("policyDataGatherForm:sedit_Coverage_additionalFields_"+fieldName));
			else
				return false;
			
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	
	public boolean isLimitAmountLabelVisible() {
		try {
			getDriver().findElement(By.id("policyDataGatherForm:sedit_Coverage_limitAmount_label"));
			
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public String getLimitAmountValue() {
		return limitAmountValue.getAttribute("value");
	}
	
	public void setLimitAmountValue(String value) {
		limitAmountValue.clear();
		limitAmountValue.sendKeys(value);
	}
	
	public void setDeductibleTypeCd(String coverageCd) {
		inputFieldDeductibleTypeCd.clear();
		inputFieldDeductibleTypeCd.sendKeys(coverageCd);
	}
	
	public void setCoverLevelCd(String coverageCd) {
		inputFieldCoverLevelCd.clear();
		inputFieldCoverLevelCd.sendKeys(coverageCd);
	}
	
	public void setMandatoryCoverageCd(String coverageCd) {
		mandatoryCoverageCd.clear();
		mandatoryCoverageCd.sendKeys(coverageCd);
	}
	
	public GeneralCoverageComponentPage setDeductibleAmount(String amount) {
		inputFieldDeductibleAmount.clear();
		inputFieldDeductibleAmount.sendKeys(amount);
		
		return this;
	}
}
