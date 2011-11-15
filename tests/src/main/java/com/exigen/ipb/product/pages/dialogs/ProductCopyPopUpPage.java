package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductCopyPopUpPage extends AbstractWebPage {		
	
	private static final String COPY_PRODUCT_ELEMENTS = "copyProductForm:copyProductEdit_%s";
	
	public enum CopyProductPopUpFields {
		productName, productCode, usedForTransactionDate, usedForPolicyEffectiveDate,
	}
	
	@FindBy(id="copyProductForm:copyProductEdit_copiedProductDisplayValue")
	private WebElement copiedProductDisplayValue;

	/*
	@FindBy(id="copyProductForm:copyProductEdit_productName")
	private WebElement productName;

	@FindBy(id="copyProductForm:copyProductEdit_productCode")
	private WebElement productCode;

	@FindBy(id="copyProductForm:copyProductEdit_usedForTransactionDate")
	private WebElement usedForTransactionDate;

	@FindBy(id="copyProductForm:copyProductEdit_usedForPolicyEffectiveDate")
	private WebElement usedForPolicyEffectiveDate;
	*/
	
	@FindBy(id="copyProductForm:copyProductOk")
	private WebElement buttonSave;
	
	@FindBy(id="copyProductForm:copyProductCancel")
	private WebElement buttonCancel;
	
	@FindBy(id="copyProductForm")
	private WebElement copyProductForm;
	
		
	public ProductCopyPopUpPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public ProductCopyPopUpPage enterFieldValue(CopyProductPopUpFields fieldName, String fieldValue) {
		String fieldId = String.format(COPY_PRODUCT_ELEMENTS, fieldName);		
		WebElement field = copyProductForm.findElement(By.id(fieldId));
		ElementUtils.setInputValue(field, fieldValue);
		return this;
	}
	
	public boolean isFieldMandatory(CopyProductPopUpFields fieldName) {
		String fieldId = String.format(COPY_PRODUCT_ELEMENTS, fieldName);
		return ElementUtils.containsStyleClass(copyProductForm.findElement(By.id(fieldId)), "required");
	}
	
	public String getCopiedProductValue() {
		return copiedProductDisplayValue.getAttribute("value");
	}
	
	public String getFieldError(CopyProductPopUpFields fieldName) {
		String fieldId = String.format(COPY_PRODUCT_ELEMENTS + "_error", fieldName);
		return copyProductForm.findElement(By.id(fieldId)).getText();
	}
	
	public ProductCopyPopUpPage assignCurrentDateForTransactionAndEffective() {
		return assignDateForTransactionAndEffecive(new Date());
	}
	
	public ProductCopyPopUpPage assignDateForTransactionAndEffecive(Date date) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		String dateString = dateFormat.format(date);
		
		enterFieldValue(CopyProductPopUpFields.usedForTransactionDate, dateString);
		enterFieldValue(CopyProductPopUpFields.usedForPolicyEffectiveDate, dateString);
		
		return this;
	}
	
	public ProductConsolidatedViewPage clickSave() {
		buttonSave.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	public ProductConsolidatedViewPage clickCancel() {
		buttonCancel.click();
		return create(ProductConsolidatedViewPage.class);
	}
}