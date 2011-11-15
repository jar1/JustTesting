package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object that represents lookup info dialog for selected attribute.
 * 
 * @author gzukas
 * @since 3.9
 */
public class LookupInfoDialog extends AbstractParentedWebPage<AlternativeProductDataPage> {

	@FindBy(id="alternateLookupSearchPopupContentTable")
	private WebElement dialog;
	
	@FindBy(id="lookupSearchForm:lookupTemplate")
	private WebElement lookupTemplateElement;
	
	private Select lookupTemplate = new Select(lookupTemplateElement);
	
	@FindBy(id="lookupSearchForm:lookupName")
	private WebElement lookupName;
	
	@FindBy(id="lookupSearchForm:body_lookupSearchResultTable")
	private WebElement searchResultTable;
	
	@FindBy(id="lookupSearchForm:searchBtn")
	private WebElement searchButton;
	
	@FindBy(id="lookupSearchForm:nextBtn")
	private WebElement nextButton;
	
	@FindBy(id="lookupSearchForm:unassignBtn")
	private WebElement unassignButton;
	
	@FindBy(id="lookupSearchForm:createNewBtn")
	private WebElement newLookupButton;
	
	@FindBy(id="lookupSearchForm:cancelBtn")
	private WebElement cancelButton;
	
	public LookupInfoDialog(WebDriver driver, PageConfiguration conf,
			AlternativeProductDataPage parent) {
		super(driver, conf, parent);
	}
	
	public boolean isDisplayed() {
		return dialog.isDisplayed();
	}
	
	public String getLookupTemplate() {
		return lookupTemplate.getFirstSelectedOption().getText();
	}
	
	public LookupInfoDialog selectLookupTemplate(String value) {
		lookupTemplate.selectByValue(value);
		return this;
	}
	
	public String getLookupName() {
		return ElementUtils.getInputValue(lookupName);
	}
	
	public void setLookupName(String value) {
		lookupName.sendKeys(value);
	}
	
	public LookupInfoDialog search() {
		searchButton.click();
		return this;
	}
	
	public LookupInfoDialog search(String lookupTemplate) {
		selectLookupTemplate(lookupTemplate);
		return search();
	}
	
	public LookupInfoDialog search(String lookupTemplate, String lookupName) {
		selectLookupTemplate(lookupTemplate);
		setLookupName(lookupName);
		return search();
	}

	public LookupEditDialog next() {
		nextButton.click();
		return create(LookupEditDialog.class);
	}
	
	public void unassign() {
		unassignButton.click();
	}
	
	public LookupEditDialog createNewLookup() {
		newLookupButton.click();
		return create(LookupEditDialog.class);
	}
	
	/**
	 * Selects given lookup in search result table.
	 * @param lookup  The lookup to be selected.
	 */
	public void selectLookup(LookupInfoElement lookup) {
		String checkboxId = new StringBuilder("lookupSearchForm:body_lookupSearchResultTable:")
			.append(lookup.getIndex())
			.append(":selectedLookup")
			.toString();
		
		WebElement checkbox = searchResultTable.findElement(By.id(checkboxId));		
		ElementUtils.setSelected(checkbox, true);
//		lookup.setSelected(checkbox.isSelected());
//		lookup.setSelected(isSelected);
	}
	
	/**
	 * @return  Search result items.
	 */
	public List<LookupInfoElement> getSearchResults() {
		List<LookupInfoElement> elements = new ArrayList<LookupInfoElement>();		
		List<WebElement> rows = searchResultTable.findElements(By.className("rich-table-row"));

		String columnFormat = "lookupSearchForm:body_lookupSearchResultTable:%d:%s";
		
		int i = 0;
		for (WebElement row : rows) {
			boolean isSelected = row.findElement(By.id(String.format(columnFormat, i, "selectedLookup"))).isSelected();//findColumn(row, columnFormat, i, "selectedLookup").isSelected();
			String lookupName = row.findElement(By.id(String.format(columnFormat, i, "column_lookupName"))).getText();//findColumn(row, columnFormat, i, "column_lookupName").getText();
			String template = row.findElement(By.id(String.format(columnFormat, i, "column_templateName"))).getText();//findColumn(row, columnFormat, i, "column_templateName").getText();
			String description = row.findElement(By.id(String.format(columnFormat, i, "column_description"))).getText();//findColumn(row, columnFormat, i, "column_description").getText();
			
			LookupInfoElement lookup = new LookupInfoElement(i, lookupName, template, description);
			lookup.setSelected(isSelected);
			elements.add(lookup);
			
			++i;
		}
		
		return elements;
	}
	
	private WebElement findColumn(WebElement parent, String columnFormat, Object... args) {
		return parent.findElement(By.id(String.format(columnFormat, args)));
	}
	

}
