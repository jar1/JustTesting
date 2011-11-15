package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Class that represents component review page. Used in Selenium2 tests.
 * 
 * @author mulevicius
 * @Since 3.9
 */
public class ProductComponentPreviewPage extends AbstractWebPage {
	
	@FindBy(id="contents")
	private WebElement contents;

	@FindBy(id="topCancelLink")
	private WebElement cancelButton;

	@FindBy(id="componentPreview:noPreviewMessage")
	private WebElement noPreviewMessage;	
	
	@FindBy(id="componentPreview:rulesRegion_1")
	private WebElement previewElement;
	
	private By labelSelector = By.cssSelector("td.pfFormLabel");
	private By dataSelector = By.className("pfFormControl");
	
	public ProductComponentPreviewPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public boolean isComponentPreviewAvailable() {
		return !ElementUtils.isDisplayed(noPreviewMessage);
	}
	
	// selenium hangs if only one isComponentPreviewAvailable() method used
	// it always hangs on this page if you trying to find elements that exists
	// so, checking if some element (which is not supposed to be) is not exists
	public boolean isComponentPreviewUnAvailable() {
		return !ElementUtils.isDisplayed(previewElement);
	}

	/**
	 * Checks if component's preview structure contains data field (table row) with provided label name 
	 * @param label Name of label (eg. First Name)
	 * @return
	 */
	public boolean containsLabel(String label) {
		List<WebElement> labels = contents.findElements(labelSelector);
		
		for (WebElement td : labels) {
			if (td.getText().equals(label)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks if component's preview structure contains data fields (table rows) with provided label names
	 * and ui elements with provided tags and types
	 * @param labels Names of labels (eg. First Name)
	 * @param tags Tags of expected ui elements (eg. input)
	 * @param types Types of expected ui elements (eg. text)
	 * @return True if and only if every expected label is in preview page exactly one time and at least one expected ui element exists nearby
	 */
	public boolean containsStructures(List<String> labels, List<String> tags, List<String> types) {
		
		List<WebElement> rows = fetchAllRows();
		int rowsAsserted = 0;
		
		// now once we have all rows fetched, we can iterate and assert data
		for(int i = 0; i < rows.size(); i++ ) {
			
			// finds a row with label cell that needs to be asserted (is provided in labels)
			WebElement tr = rows.get(i);
			WebElement td = tr.findElement(labelSelector);

			String labelName = td.getText();
			if (labels.contains(labelName)) {
				// once a row is found, we need to locate a data cell in it (next to label cell)
				WebElement dataCell = tr.findElement(dataSelector);
	
				String tag = tags.get(i);
				String type = types.get(i);
				try {
					// once a data cell is found, we need to assert if it contains expected ui element
					WebElement el = dataCell.findElement(By.tagName(tag));
					
					// if ui element is expected to have attribute type and it mismatches with the expected one
					// then return false
					if(type != null && !el.getAttribute("type").equals(type)) {
						return false;
					}
					
					// if we are here, then another row has been successfully asserted
					rowsAsserted++;
					
				} catch (NoSuchElementException ex) {
					// expected data cell cannot be located
					return false;
				}
			}
		}
		
		return rowsAsserted == labels.size();
	}	
	
	/**
	 * Selects all rows in preview page. Every row is expected to contain two columns, 
	 * where first column contains label (eg. License Date) and second column contains 
	 * one or more ui elements, such as input, select, ...
	 * @return List of rows, where each row is WebElement
	 */
	private List<WebElement> fetchAllRows() {
		List<WebElement> rows = new ArrayList<WebElement>();
		rows.addAll(contents.findElements(By.cssSelector("tr.oddRow")));
		rows.addAll(contents.findElements(By.cssSelector("tr.evenRow")));

		return rows;
	}
	
	/**
	 * Exits preview page and navigates back to alternative view page
	 * @return alternative view page
	 */
	public AlternativeProductDataPage clickCancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
}
