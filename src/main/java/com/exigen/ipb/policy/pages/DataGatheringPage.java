
package com.exigen.ipb.policy.pages;

import static org.junit.Assert.fail;

import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.RuntimeDefaults;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents DataGathering page
 * 
 * @author giedrius
 * @author gzukas
 * @since 3.9
 */
public class DataGatheringPage extends AbstractWebPage implements LandingPage {

	private static final String QUOTE_FLOW = "new-quote-flow&currentProductCd=%s&customerNumber=%d";
	
	@FindBy(id="topSaveLink")
	private WebElement buttonSave;

	@FindBy(id="topSaveAndExitLink")
	private WebElement buttonSaveAndExit;

	@FindBy(id="topCancelLink")
	private WebElement buttonCancel ;

	@FindBy(id="topCreateQuoteVersionLink")
	private WebElement buttonCreateVersion;
	
	@FindBy(xpath = "//input[@value='Next']")
	private WebElement buttonNext;

	@FindBy(id="policyDataGatherForm:tabListList_1")
	private WebElement tabPanelLevel1;
	
	@FindBy(id="policyDataGatherForm:tabListList_2")
	private WebElement tabPanelLevel2;
	
	@FindBy(id="policyDataGatherForm:tabListList_3")
	private WebElement tabPanelLevel3;

	@FindBy(css="span.pathContext")
	private WebElement pathContext;
	
	@FindBy(id="policyDataGatherForm")
	private WebElement dataGatherForm;	
	
	private String productCd;
	
	private int customerNumber;
	
	public DataGatheringPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}

	public DataGatheringPage(WebDriver driver, PageConfiguration configuration, String productCd, int customerNumber) {
		this(driver, configuration);
		
		this.productCd = productCd;
		this.customerNumber = customerNumber;
	}
	
	public DataGatheringPage(WebDriver driver, PageConfiguration conf, String productCd) {
		this(driver, conf, productCd, RuntimeDefaults.DEFAULT_CUSTOMER);
	}

	/**
	 * Determines whether bread crumb is displayed.
	 * 
	 * @return  True if bread crumb is displayed, otherwise - false.
	 */
	public boolean isBreadCrumbDisplayed() {
		return ElementUtils.isDisplayed(pathContext);
	}
	
	/**
	 * @author mulevicius
	 * @param level Depth of tabs, starting from 1
	 * @param tabName
	 */
	public DataGatheringPage openTab(int level, String tabName) {
		try {
			determineTabLevelElement(level).findElement(By.linkText(tabName)).click();
			return this;
		} catch (NoSuchElementException e) {
			fail("Could not open tab for " + tabName + " in level " + level);
			return null;
		}
	}
	
	/**
	 * @author mulevicius
	 * @param level Depth of tabs, starting from 1
	 * @param tabName
	 */
	public boolean existsTab(int level, String tabName) {
		try {
			return determineTabLevelElement(level).findElement(By.linkText(tabName)) != null;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private WebElement determineTabLevelElement(int level) {
		switch(level) {
			case 1: return tabPanelLevel1; 
			case 2: return tabPanelLevel2; 
			case 3: return tabPanelLevel3; 
			default: throw new RuntimeException("Unsuported tab level passed to DataGatheringPage.selectTab(...)");
		}		
	}
	
	/**
	 * 
	 * @param level level in to search for
	 * @return label of currently selected tab
	 */
	public String getSelectedTab(int level) {
		WebElement tabPanelElement = determineTabLevelElement(level);
		WebElement element = tabPanelElement.findElement(By.className("selected"));
		return element.findElement(By.tagName("span")).getText();
	}

	public DataGatheringPage clickSave() {
		buttonSave.click();
		return this;
	}

	public QuoteConsolidatedViewPage clickSaveAndExit() {
		buttonSaveAndExit.click();
		return create(QuoteConsolidatedViewPage.class);
	}

	public void clickCancel() {
		buttonCancel.click();
	}

	public void clickCreateVersion() {
		buttonCreateVersion.click();
	}

	public void clickNext() {
		buttonNext.click();
	}
		
	public boolean isTabsDispalayed() {
		try {
			dataGatherForm.findElement(By.id("policyDataGatherForm:tabList"));
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isNextDispalayed() {
		try {
			dataGatherForm.findElement(By.id("policyDataGatherForm:next"));
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}	

	public void navigate(Application app) {
		app.navigateToFlow(String.format(QUOTE_FLOW, productCd, customerNumber));
	}
	
}
