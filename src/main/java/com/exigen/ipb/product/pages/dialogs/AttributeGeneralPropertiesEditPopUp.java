package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

public class AttributeGeneralPropertiesEditPopUp extends AbstractWebPage {
	
	@FindBy(id="selectedAttributeGeneralInfoForm")
	private WebElement popUp;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:attributeLabel")
	private WebElement labelInput;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:attributeSerializationXPath")
	private WebElement xpathInput;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:attributeSerializable")
	private WebElement serializableCheckbox;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:attributeDisplayable")
	private WebElement displayableCheckbox;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:displayedInConsolidatedView")
	private WebElement displayInConsolidatedCheckbox;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:comparable")
	private WebElement comparableCheckbox;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:helpText")
	private WebElement helpTextArea;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:saveButton")
	private WebElement saveButton;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:cancelButton")
	private WebElement cancelButton;
	
	@FindBy(id="selectedAttributeGeneralInfoForm:ratingFactorInd")
	private WebElement ratingFactorCheckbox;
	
	
	public AttributeGeneralPropertiesEditPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public String getAttributeName() {
		return popUp.findElement(By.className("section_header")).getText();
	}
	
	public String getHelpText() {
		return helpTextArea.getText();
	}
	
	public void setHelpText(String helpText) {
		helpTextArea.clear();
		helpTextArea.sendKeys(helpText);
	}
	
	public String getLabelText() {
		return ElementUtils.getInputValue(labelInput);
	}
	
	public void setLabel(String labelText) {
		labelInput.clear();
		labelInput.sendKeys(labelText);
	}
	
	public String getXPathText() {
		return ElementUtils.getInputValue(xpathInput);
	}
	
	public void setXPath(String xpathText) {
		xpathInput.clear();
		xpathInput.sendKeys(xpathText);
	}
	
	public boolean getIsDisplayable() {
		return displayableCheckbox.isSelected();
	}
	
	public boolean getIsComparable() {
		return comparableCheckbox.isSelected();
	}
	
	public boolean getIsRating() {
		return ratingFactorCheckbox.isSelected();
	}
	
	public boolean getIsSerializable() {
		return serializableCheckbox.isSelected();
	}
	
	public boolean getIsDisplayInConsolidated() {
		return displayInConsolidatedCheckbox.isSelected();
	}
	
	public void setDisplayableTo(boolean set) {
		setCheckboxTo(displayableCheckbox, set);
	}
	
	public void setRatingTo(boolean set) {
		setCheckboxTo(ratingFactorCheckbox, set);
	}
	
	public void setDisplayInConsolidatedTo(boolean set) {
		setCheckboxTo(displayInConsolidatedCheckbox, set);
	}
	
	public void setComparableTo(boolean set) {
		setCheckboxTo(comparableCheckbox, set);
	}
	
	public void setSerializableTo(boolean set) {
		setCheckboxTo(serializableCheckbox, set);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage save() {
		saveButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	private void setCheckboxTo(WebElement checkbox, boolean set) {
		ElementUtils.setSelected(checkbox, set);
	}
}
