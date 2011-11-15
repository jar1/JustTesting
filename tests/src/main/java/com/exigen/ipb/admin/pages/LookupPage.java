package com.exigen.ipb.admin.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object class that represents lookup search page.
 * 
 * @author gzukas
 * @since 3.9
 */
public class LookupPage extends AbstractWebPage implements LandingPage {

	public static final String LOOKUPS_MAIN_FLOW = "lookups-main-flow";
	
	@FindBy(id="loofupsListForm:searchCriteria")
	private WebElement searchCriteria;

	@FindBy(id="loofupsListForm:lookupTableId")
	private WebElement lookupTable;
	
	@FindBy(id="loofupsListForm:searchBtn")
	private WebElement searchButton;
	
	public LookupPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public String getSearchCriteria() {
		return ElementUtils.getInputValue(searchCriteria);
	}
	
	public void setSearchCriteria(String criteria) {
		searchCriteria.sendKeys(criteria);
	}
	
	public void clickSearch() {
		searchButton.click();
	}
	
	/**
	 * Searches for lookups that conform to given criteria.
	 * 
	 * @param criteria  The criteria that lookup should conform to.
	 */
	public void search(String criteria) {
		setSearchCriteria(criteria);
		clickSearch();
	}
	
	/**
	 * Basic implementation of search results retrieval.
	 * @return
	 */
	public List<String> getSearchResults() {
		List<String> lookupNames = new ArrayList<String>();
		if (lookupTable.isDisplayed()) {
			List<WebElement> nameColumns = lookupTable.findElements(By.className("rich-sdt-column-cell-body"));
			for (WebElement nameColumn : nameColumns) {
				lookupNames.add(nameColumn.getText());
			}
		}
		return lookupNames;
	}

	public void navigate(Application app) {
		app.navigateToFlow(LOOKUPS_MAIN_FLOW);
	}
}
