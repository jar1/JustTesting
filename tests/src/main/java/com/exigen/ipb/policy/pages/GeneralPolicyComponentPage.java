package com.exigen.ipb.policy.pages;

import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * Page representing Policy 2.0 component view in Data Gathering
 * 
 * @author ggrazevicius
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class GeneralPolicyComponentPage extends DataGatheringPage {	
	
	public static final String ATTRIBUTE_STANDARD   = "standard";
	public static final String ATTRIBUTE_ADDITIONAL = "additional";	
	
	public static final String LIST_CHANGE   = "Change";
	public static final String LIST_REMOVE   = "Eliminate";

	@FindBy(id="policyDataGatherForm:sedit_Policy_imported")
	private WebElement source;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_contractTerm_effective")
	private WebElement effectiveDate;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_riskStateCd")
	private WebElement riskState;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_inceptionDate")
	private WebElement originalEffectiveDate;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_countryCd")
	private WebElement country;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_rateEffectiveDate")
	private WebElement rateEffectiveDate;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_transactionEffectiveDate")
	private WebElement transactionEffectiveDate;
	
	@FindBy(id="policyDataGatherForm:sedit_Policy_productCd")
	private WebElement productCdLabel;
	
	@FindBy(id="policyDataGatherForm:next")
	private WebElement buttonNext;	
	
	@FindBy(id="policyDataGatherForm")
	private WebElement dataGatherForm;	
	
	@FindBy(id="policyDataGatherForm:componentViewHolder")
	private WebElement componentTable;
	
	@FindBy(id="policyDataGatherForm:tabList")
	private WebElement tabList;

	public GeneralPolicyComponentPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}
	
	public GeneralPolicyComponentPage(WebDriver driver, PageConfiguration conf, String productCd) {
		super(driver, conf, productCd);
	}

	/**
	 * Fills empty mandatory values with defaults
	 */
	public GeneralPolicyComponentPage fillDefaultMandatoryValuesForPolicy() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormater = new SimpleDateFormat("MM/dd/yyyy");
		String today = simpleDateFormater.format(date);
		new Select(source).selectByValue("NEW");
		new Select(riskState).selectByValue("AZ");
		originalEffectiveDate.sendKeys(today);
		rateEffectiveDate.sendKeys(today);
		country.sendKeys("LT");
		return this;
	}

	public String getSource() {
		return source.getText();
	}

	public String getEffectiveDate() {
		return effectiveDate.getText();
	}

	public String getRiskState() {
		return riskState.getText();
	}

	public String getOriginalEffectiveDate() {
		return originalEffectiveDate.getText();
	}

	public String getCountry() {
		return country.getText();
	}

	public void enterCountry(String countryCd) {
		ElementUtils.setInputValue(country, countryCd);
	}
	
	public String getProductCd() {
		return ElementUtils.getInputValue(productCdLabel);
	}
	
	public String getRateEffectiveDate() {
		return rateEffectiveDate.getText();
	}
	
	public String getTransactionEffectiveDate() {
		return transactionEffectiveDate.getText();
	}
	
	public GeneralPolicyComponentPage enterTransactionEffectiveDate(String newDate) {
		ElementUtils.setInputValue(transactionEffectiveDate, newDate);
		return this;
	}	
}
