package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.StringUtils;

import com.exigen.ipb.components.domain.WordingCase;
import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object that represents Component Attribute Constraints editing pop-up
 * in alternate component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AttributeConstraintsDialog extends AbstractParentedWebPage<AlternativeProductDataPage> {

	@FindBy(id="selectedAttributeConstraintsForm")
	private WebElement form;
	
	@FindBy(id="selectedAttributeConstraintsForm:mandatoryInd")
	private WebElement mandatoryInd;
	
	@FindBy(id="selectedAttributeConstraintsForm:disabledInd")
	private WebElement disabledInd;
	
	@FindBy(id="selectedAttributeConstraintsForm:defaultValue")
	private WebElement defaultValue;
	
	@FindBy(id="selectedAttributeConstraintsForm:regExp")
	private WebElement regExp;
	
	@FindBy(id="selectedAttributeConstraintsForm:wordingCase")
	private WebElement wordCasing;
	
	@FindBy(id="selectedAttributeConstraintsForm:valuesRangeFrom")
	private WebElement valuesRangeFrom;
	
	@FindBy(id="selectedAttributeConstraintsForm:valuesRangeTo")
	private WebElement valuesRangeTo;
	
	@FindBy(id="selectedAttributeConstraintsForm:minLength")
	private WebElement minLength;
	
	@FindBy(id="selectedAttributeConstraintsForm:maxLength")
	private WebElement maxLength;
	
	@FindBy(id="selectedAttributeConstraintsForm:saveButton")
	private WebElement saveButton;
	
	@FindBy(id="selectedAttributeConstraintsForm:cancelButton")
	private WebElement cancelButton;
	
	public AttributeConstraintsDialog(WebDriver driver,
			PageConfiguration conf, AlternativeProductDataPage parent) {
		
		super(driver, conf, parent);
	}
	
	public AlternativeProductDataPage save() {
		saveButton.click();
		return create(AlternativeProductDataPage.class);
	}

	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public boolean isDialogDisplayed() {
		return form.isDisplayed();
	}
	
	public boolean isMandatory() {
		return mandatoryInd.isSelected();
	}
	
	public void setMandatory(boolean value) {
		ElementUtils.setSelected(mandatoryInd, value);
	}
	
	public boolean isDisabled() {
		return disabledInd.isSelected();
	}
	
	public void setDisabled(boolean value) {
		ElementUtils.setSelected(disabledInd, value);
	}
	
	public String getDefaultValue() {
		return ElementUtils.getInputValue(defaultValue);
	}
	
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue.sendKeys(defaultValue.toString());
	}

	public String getRegExp() {
		return ElementUtils.getInputValue(regExp);
	}
	
	public void setRegExp(String regExp) {
		this.regExp.sendKeys(regExp);
	}
	
	public WordingCase getWordingCase() {
		Select s = new Select(wordCasing);
		
		String selectedText = s.getFirstSelectedOption().getText();
		
		return StringUtils.hasText(selectedText)
				? WordingCase.valueOf(selectedText)
				: null;
	}
	
	public void setWordingCase(WordingCase wordingCase) {
		this.wordCasing.findElement(By.cssSelector("option[value='"+wordingCase+"']")).click();
	}
	
	public String getValuesRangeFrom() {
		return ElementUtils.getInputValue(valuesRangeFrom);
	}
	
	public void setValuesRangeFrom(Object from) {
		valuesRangeFrom.sendKeys(from.toString());
	}

	public String getValuesRangeTo() {
		return ElementUtils.getInputValue(valuesRangeTo);
	}

	public void setValuesRangeTo(Object to) {
		valuesRangeTo.sendKeys(to.toString());
	}

	public String getMinLength() {
		return ElementUtils.getInputValue(minLength);
	}

	public void setMinLength(Object min) {
		minLength.sendKeys(min.toString());
	}

	public String getMaxLength() {
		return ElementUtils.getInputValue(maxLength);
	}

	public void setMaxLength(Object max) {
		maxLength.sendKeys(max.toString());
	}

}
