package com.exigen.ipb.product.pages;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.policy.domain.PolicyChangesAllowedAt;
import com.exigen.ipb.policy.domain.RenewalTypesAllowed;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.BroadLOB;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.PolicyTerm;

/**
 * Product properties page for selenium2 tests
 * 
 * @since 3.9
 * @author mulevicius
 *
 */
public class ProductPropertiesPage extends AbstractWebPage {
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_productCd")
	private WebElement txtFieldProductCode;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_productCd_error")
	private WebElement txtFieldProductCodeError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_productName")
	private WebElement txtFieldProductName;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_productName_error")
	private WebElement txtFieldProductNameError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_usedForPolicyEffectiveDate")
	private WebElement txtDtFieldPolicyEffectiveDate;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_usedForPolicyEffectiveDate_error")
	private WebElement txtDtFieldPolicyEffectiveDateError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_availableForTxDate")
	private WebElement txtDtFieldEndorsementEffectiveDate;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_availableForTxDate_error")
	private WebElement txtDtFieldEndorsementEffectiveDateError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_broadLOBCd")
	private WebElement dropDownBroadLOBCd;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_broadLOBCd_error")
	private WebElement dropDownBroadLOBCdError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_LOBCd")
	private WebElement dropDownLOBCd;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_LOBCd_error")
	private WebElement dropDownLOBCdError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_policyTermType")
	private WebElement dropDownPolicyTermType;	
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_policyChangesAllowedAt")
	private WebElement tablePolicyChangesAllowedAt;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_policyChangesAllowedAt_error")
	private WebElement tablePolicyChangesAllowedAtError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_renewalTypesAllowed")
	private WebElement tableRenewalTypesAllowed;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_renewalTypesAllowed_error")
	private WebElement tableRenewalTypesAllowedError;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_policyChangesAllowedAt:0")
	private WebElement optionPolicyChangesAllowedAtRenewal;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_policyChangesAllowedAt:1")
	private WebElement optionPolicyChangesAllowedAtRenewalMidTerm;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_renewalTypesAllowed:0")
	private WebElement optionRenewalTypesAllowedAuto;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_renewalTypesAllowed:1")
	private WebElement optionRenewalTypesAllowedManual;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_renewalTypesAllowed:2")
	private WebElement optionRenewalTypesAllowedBoth;
	
	@FindBy(id="productUpdateForm:productPropertiesEdit_description")
	private WebElement txtFieldDescription;	
	
	@FindBy(id="productUpdateForm:Next")
	private WebElement buttonNext;
	
	@FindBy(id="productUpdateForm:productStatus")
	private WebElement txtProductStatus;
	
	@FindBy(id="productUpdateForm:productActivationDate")
	private WebElement txtProductActivationDate;
	
	public ProductPropertiesPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public boolean isProductCodeMandatory() {
		return isFieldMandatory(txtFieldProductCode);
	}
	
	public String getProductCodeErrorMsg() {
		return getErrorMsg(txtFieldProductCodeError);
	}
	
	public boolean isProductNameMandatory() {
		return isFieldMandatory(txtFieldProductName);
	}
	
	public String getProductNameErrorMsg() {
		return getErrorMsg(txtFieldProductNameError);
	}
	
	public boolean isEffectiveDateMandatory() {
		return isFieldMandatory(txtDtFieldPolicyEffectiveDate);
	}
	
	public String getEffectiveDateErrorMsg() {
		return getErrorMsg(txtDtFieldPolicyEffectiveDateError);
	}
	
	public boolean isEndorsementEffectiveDateMandatory() {
		return isFieldMandatory(txtDtFieldEndorsementEffectiveDate);
	}
	
	public String getEndorsementEffectiveDateErrorMsg() {
		return getErrorMsg(txtDtFieldEndorsementEffectiveDateError);
	}
	
	public boolean isBroadLOBMandatory() {
		return isFieldMandatory(dropDownBroadLOBCd);
	}
	
	public String getBroadLOBErrorMsg() {
		return getErrorMsg(dropDownBroadLOBCdError);
	}
	
	public boolean isLOBMandatory() {
		return isFieldMandatory(dropDownLOBCd);
	}
	
	public String getLOBErrorMsg() {
		return getErrorMsg(dropDownLOBCdError);
	}
	
	public boolean isPolicyChangesAllowedAtMandatory() {
		return isFieldMandatory(tablePolicyChangesAllowedAt);
	}
	
	public String getPolicyChangesAllowedAtErrorMsg() {
		return getErrorMsg(tablePolicyChangesAllowedAtError);
	}
	
	public boolean isRenewalTypesAllowedMandatory() {
		return isFieldMandatory(tableRenewalTypesAllowed);
	}
	
	public String getRenewalTypesAllowedErrorMsg() {
		return getErrorMsg(tableRenewalTypesAllowedError);
	}
	
	private String getErrorMsg(WebElement element) {
		if(ElementUtils.isDisplayed(element)) {
			return element.getText();
		} else {
			return "";
		}
	}
	
	private boolean isFieldMandatory(WebElement element) {
		return ElementUtils.containsStyleClass(element, "required");
	}
	
	public ProductPropertiesPage enterProductCode(String productCode) {
		ElementUtils.setInputValue(txtFieldProductCode, productCode);
		return this;		
	}
	
	public ProductPropertiesPage enterProductName(String productName) {
		ElementUtils.setInputValue(txtFieldProductName, productName);
		return this;		
	}
	
	public ProductPropertiesPage enterAppliesToPolicyEffectiveDate(String appliesToPolicyEffectiveDate) {
		ElementUtils.setInputValue(txtDtFieldPolicyEffectiveDate, appliesToPolicyEffectiveDate);
		return this;		
	}
	
	public ProductPropertiesPage enterAppliesToEndorsementEffectiveDate(String appliesToEndorsmentEffectiveDate) {
		ElementUtils.setInputValue(txtDtFieldEndorsementEffectiveDate, appliesToEndorsmentEffectiveDate);
		return this;		
	}
	
	public ProductPropertiesPage setBroadLineOfBusiness(BroadLOB broadLOB) {
		new Select(dropDownBroadLOBCd).selectByVisibleText(broadLOB.toString());
		return this;		
	}
	
	public ProductPropertiesPage setLineOfBusiness(LOB lob) {
		new Select(dropDownLOBCd).selectByVisibleText(lob.toString());
		return this;		
	}
	
	public ProductPropertiesPage setPolicyTermType(PolicyTerm policyTerm) {
		new Select(dropDownPolicyTermType).selectByVisibleText(policyTerm.toString());
		return this;		
	}	
	
	public ProductPropertiesPage setPolicyChangesAllowedAt(PolicyChangesAllowedAt policyChangesAllowedAt) {
		switch(policyChangesAllowedAt) {
			case RENEWAL_ONLY :
				optionPolicyChangesAllowedAtRenewal.click();
				break;
			case RENEWAL_AND_MIDTERM :
				optionPolicyChangesAllowedAtRenewalMidTerm.click();
				break;			
		}
		return this;		
	}
	
	public ProductPropertiesPage setRenewalTypesAllowed(RenewalTypesAllowed renewalTypesAllowed) {		
		switch(renewalTypesAllowed) {
			case AUTOMATIC :
				optionRenewalTypesAllowedAuto.click();
				break;
			case MANUAL :
				optionRenewalTypesAllowedManual.click();
				break;		
			case BOTH :
				optionRenewalTypesAllowedBoth.click();
				break;	
		}
		return this;	
	}

	public ProductPropertiesPage enterDescription(String description) {
		ElementUtils.setInputValue(txtFieldDescription, description);
		return this;		
	}
	
	public List<String> getOptionsOfBroadLOBDropDownAsText() {
		return ElementUtils.getOptions(new Select(dropDownBroadLOBCd));
	}
	
	public List<String> getOptionsOfLOBDropDownAsText() {
		return ElementUtils.getOptions(new Select(dropDownLOBCd));
	}
	
	public List<String> getOptionsOfPolicyTermDropDownAsText() {
		return ElementUtils.getOptions(new Select(dropDownPolicyTermType));
	}

	public String getProductStatus() {
		return txtProductStatus.getText();
	}
	
	public String getProductActivationDate() {
		return txtProductActivationDate.getText();
	}
	
	public String getPolicyTermType() {
		Select select = new Select(dropDownPolicyTermType);
		return select.getFirstSelectedOption().getText();
	}
	
	public boolean isOptionPolicyChangesAllowedAtRenewalVisible() {
		return ElementUtils.isDisplayed(optionPolicyChangesAllowedAtRenewal);
	}
	
	public boolean isOptionPolicyChangesAllowedAtRenewalMidTermVisible() {
		return ElementUtils.isDisplayed(optionPolicyChangesAllowedAtRenewalMidTerm);
	}
	
	public boolean isOptionRenewalTypesAllowedAutoVisible() {
		return ElementUtils.isDisplayed(optionRenewalTypesAllowedAuto);
	}
	
	public boolean isOptionRenewalTypesAllowedBothVisible() {
		return ElementUtils.isDisplayed(optionRenewalTypesAllowedBoth);
	}
	
	public boolean isOptionRenewalTypesAllowedManualVisible() {
		return ElementUtils.isDisplayed(optionRenewalTypesAllowedManual);
	}	
	
	public boolean isDisplayed() {
		return ElementUtils.isDisplayed(txtFieldProductCode);
	}
	
	public ProductConsolidatedViewPage clickNext() {
		buttonNext.click();
		return create(ProductConsolidatedViewPage.class);	
	}
	
}
