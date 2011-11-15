package com.exigen.ipb.policy.pages;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.LandingPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.RuntimeDefaults;

/**
 * Page Object class that represents quote list page.
 * 
 * @author ggrazevicius
 * @author gzukas
 * @since 3.9
 *
 */
public class QuoteListPage extends AbstractWebPage implements LandingPage {

	private static final String QUOTE_MAIN_FLOW_FORMAT = 
			"quote-main-flow&customerNumber=%d";
	
	@FindBy(id="quoteForm:selectedProduct")
	private WebElement dropDownSelectProduct;
	
	@FindBy(id="quoteForm:new")
	private WebElement buttonAddNewQuote;
	
	private int customerNumber;
	
	public QuoteListPage(WebDriver driver, PageConfiguration conf) {
		this(driver, conf, RuntimeDefaults.DEFAULT_CUSTOMER);
	}
	
	public QuoteListPage(WebDriver driver, PageConfiguration conf, int customerNumber) {
		super(driver, conf);
		this.customerNumber = customerNumber;
	}
	
	/**
	 * Adds new quote of given product.
	 * 
	 * @param productCode  The code of product.
	 * @param componentPageClass  The page class of first component.
	 */
	public <T extends AbstractWebPage> T addNewQuote(String productCode, Class<T> componentPageClass) {
		new Select(dropDownSelectProduct).selectByValue(productCode);
		buttonAddNewQuote.click();
		
		return create(componentPageClass);
	}

	public void navigate(Application app) {
		app.navigateToFlow(String.format(QUOTE_MAIN_FLOW_FORMAT, customerNumber));
	}
}
