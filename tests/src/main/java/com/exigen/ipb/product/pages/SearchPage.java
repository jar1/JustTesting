package com.exigen.ipb.product.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Web page representing application Search page
 * 
 * @author mulevicius
 * @since 3.9
 */
public class SearchPage extends AbstractWebPage {
    
    @FindBy(id = "searchForm:searchFormME_accountNumber")
    private WebElement accountNumber;

    @FindBy(id = "searchForm:searchFormME_customerNumber")
    private WebElement customerNumber;
    
    @FindBy (id = "searchForm:searchFormME_policyNumber")
    private WebElement policyNumber;
    
    @FindBy(id = "searchForm:searchBtn")
    private WebElement searchButton;

    @FindBy(id = "searchTable1Form:body_searchTable1:0:selectAccount")
    private WebElement searchResultAccountNumber1;
    
    @FindBy (id = "searchForm:searchFormME_accountNumber")
	private WebElement searchForAccountRadiobutton;
    
    @FindBy (id = "searchForm:entityTypeSelect:0")
    private WebElement searchForAccountRadioButton;
    
    @FindBy (id = "searchForm:entityTypeSelect:1")
    private WebElement searchForCustomerRadioButton;
    
    @FindBy (id = "searchForm:entityTypeSelect:2")
    private WebElement searchForQuoteRadioButton;
    
    @FindBy (id = "searchForm:entityTypeSelect:3")
    private WebElement searchForPolicyRadioButton;

	@FindBy (id = "searchForm:createAccountBtnAlway")
	private WebElement createAcctBtn;
	
	@FindBy (xpath = "//li[text()='Account not found']")
	private WebElement messageAccountNotFount;

	//search result table elements
    @FindBy(id = "searchTable1Form:body_searchTable1:0:column_accountName")
    private WebElement searchResultAccountName1;
    
    @FindBy (xpath = "//a[contains(@id, 'searchTable1Form:body_searchTable1:0:']")
    private WebElement searchResult1;
	
	public SearchPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public WebElement getAccountNumber() {
		return accountNumber;
	}
	
	public WebElement getCustomerNumber() {
		return customerNumber;
	}

	public WebElement getSearchButton() {
		return searchButton;
	}
	
	public WebElement getPolicyNumber() {
		return policyNumber;
	}

	public WebElement getSearchForAccountRadiobutton() {
		return searchForAccountRadiobutton;
	}
	
	public WebElement getSearchForPolicyRadioButton() {
		return searchForPolicyRadioButton;
	}
	
	public WebElement getCreateAccount() {
		return createAcctBtn;
	}
	
	public WebElement getMessageAccountNotFound() {
		return messageAccountNotFount;
	}
	
	public WebElement getSearchResultAccountNumber1() {
		return searchResultAccountNumber1;
	}

	public WebElement getSearchResultAccountName1() {
		return searchResultAccountName1;
	}
	
	public WebElement getSearchResult1() {
		return searchResult1;
	}
}

