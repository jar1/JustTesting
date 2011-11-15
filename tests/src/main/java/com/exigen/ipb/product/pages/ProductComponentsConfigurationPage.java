package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.AppliedRuleDialog;
import com.exigen.ipb.product.pages.dialogs.AttributeAddNewOldPopUp;
import com.exigen.ipb.product.pages.dialogs.AttributeRemovalConfirmationPopUp;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Class that represents component's configuration in old view. Used in 
 * Selenium2 tests.
 * 
 * @author mulevicius
 * @author jdasekvicius
 * 
 * @since 3.9
 */
public class ProductComponentsConfigurationPage extends AbstractWebPage {

	@FindBy(id="iconcomponentConfigurationEditForm:configureAppRuleMenuGroup")
	private WebElement componentProperties;
	
	@FindBy(id="componentConfigurationEditForm:hiddenAddButtonInd")
	private WebElement addButtonVisibleCheckbox;
	
	@FindBy(id="componentConfigurationEditForm:hiddenRemoveButtonInd")
	private WebElement removeButtonVisibleCheckbox;
	
	@FindBy(id="componentConfigurationEditForm:addNewAttributeBtn")
	private WebElement buttonAddNewAttribute;	
	
	@FindBy(id="componentConfigurationEditForm:removeAttributeBtn")
	private WebElement buttonRemoveAttribute;
	
	@FindBy(id="componentConfigurationEditForm:body_selectedAttributesTable:tb")
	private WebElement attributesTable;
	
	@FindBy(id="componentConfigurationEditForm:configureAppRule")
	private WebElement linkConditionalComponentRendering;
	
	@FindBy(id="componentConfigurationEditForm:componentLabelRenderedInConsolidatedView")
	private WebElement checkBoxShowBlockLabelInConsolidatedView;	
	
	@FindBy(id="componentConfigurationEditForm:displayedInConcolidatedViewComponentFld")
	private WebElement checkBoxShowInConsolidatedView;
	
	@FindBy(id="topSaveLink")
	private WebElement saveButton;
	
	@FindBy(id="topCancelLink")
	private WebElement cancelButton;
	
	public ProductComponentsConfigurationPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public boolean getAddButtonVisible() {
		return addButtonVisibleCheckbox.isSelected();
	}
	
	public boolean getRemoveButtonVisible() {
		return removeButtonVisibleCheckbox.isSelected();
	}
	
	public ProductComponentsConfigurationPage setAddButtonIsVisibleTo(boolean set) {
		ElementUtils.setSelected(addButtonVisibleCheckbox, set);
		return this;
	}
	
	public ProductComponentsConfigurationPage setRemoveButtonIsVisibleTo(boolean set) {
		ElementUtils.setSelected(removeButtonVisibleCheckbox, set);
		return this;
	}
	
	public ProductComponentsConfigurationPage setShowBlockLabelInConsolidatedView(boolean set) {
		ElementUtils.setSelected(checkBoxShowBlockLabelInConsolidatedView, set);
		return this;
	}
	
	public ProductComponentsConfigurationPage setShowInConsolidatedView(boolean set) {
		ElementUtils.setSelected(checkBoxShowInConsolidatedView, set);
		return this;
	}	
	
	public boolean isShowBlockLabelInConsolidatedViewSelected() {
		return checkBoxShowBlockLabelInConsolidatedView.isSelected();
	}
	
	public boolean isShowInConsolidatedViewSelected() {
		return checkBoxShowInConsolidatedView.isSelected();
	}	
	
	public ProductComponentsConfigurationPage expand() {
		if(!addButtonVisibleCheckbox.isDisplayed()) {
			componentProperties.click();
		}
		return this;
	}
	
	public AttributeAddNewOldPopUp clickAddNewAttribute() {
		buttonAddNewAttribute.click();
		return create(AttributeAddNewOldPopUp.class);
	}
	
	public ProductComponentsConfigurationPage selectDisplayble(String attName) {		
		List<WebElement> tableRows = attributesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows)	{
			WebElement td = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":column_code"));			
			if (td.getText().equals(attName))	{				
				WebElement check = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":selectedAttrFullQuoteInd"));
				ElementUtils.setSelected(check, true);
				return this;
			}
			i++;
		}		
		return null;
	}
	
	public ProductComponentsConfigurationPage selectDisplaybleAll() {		
		List<WebElement> tableRows = attributesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows)	{			
			WebElement check = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":selectedAttrFullQuoteInd"));
			ElementUtils.setSelected(check, true);
			i++;
		}		
		return this;
	}
	
	public ProductComponentsConfigurationPage selectAttribute(String attName) {		
		List<WebElement> tableRows = attributesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows)	{
			WebElement td = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":column_code"));			
			if (td.getText().equals(attName))	{				
				WebElement check = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":selectedAttrIsSelected"));
				ElementUtils.setSelected(check, true);
				return this;
			}
			i++;
		}		
		return null;
	}
	
	public List<String> getAttributesList() {		
		List<WebElement> tableRows = attributesTable.findElements(By.cssSelector("tr.rich-table-row"));
		List<String> tableElementNames = new ArrayList<String>();

		int i = 0;
		for (WebElement arr : tableRows) {
			WebElement td = arr.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":column_code"));		
			tableElementNames.add(td.getText());
			i++;
		}			
		return tableElementNames;
	}
	
	public Set<String> getAttributesSet() {
		return new HashSet<String>(getAttributesList());
	}
	
	public ProductComponentsConfigurationPage selectAttributesAll() {		
		List<WebElement> tableRows = attributesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows)	{
			WebElement check = table.findElement(By.id("componentConfigurationEditForm:body_selectedAttributesTable:"+ i +":selectedAttrIsSelected"));
			ElementUtils.setSelected(check, true);
			i++;
		}		
		return this;
	}	
	
	public AppliedRuleDialog clickConditionalComponentRendering() {
		linkConditionalComponentRendering.click();
		return create(AppliedRuleDialog.class);
	}
		
	public AttributeRemovalConfirmationPopUp clickRemove() {
		buttonRemoveAttribute.click();
		return create(AttributeRemovalConfirmationPopUp.class);
	}
	
	public void remove(String attName) {
		selectAttribute(attName);
		buttonRemoveAttribute.click();
	}
	
	public ProductDataPage clickSave() {
		saveButton.click();
		return create(ProductDataPage.class);
	}
	
	public ProductDataPage clickCancel() {
		cancelButton.click();
		return create(ProductDataPage.class);
	}
}
