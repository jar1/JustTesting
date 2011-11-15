package com.exigen.ipb.policy.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;






/**
 * Quote consolidated view page for selenium web test
 * 
 * @author sstundzia
 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */

public class QuoteConsolidatedViewPage extends AbstractWebPage {
	@FindBy(id="producContextInfoForm:moveToBox")
	private WebElement selectActionCombobox;
	
	@FindBy(id="producContextInfoForm:btnMoveToDecorete")
	private WebElement processSelectedActionButton;
	
	@FindBy(id="producContextInfoForm:policyDetail_policyStatusCdText")
	private WebElement policyStatus;
	
	@FindBy(id="productConsolidatedViewForm")
	private WebElement consolidatedViewForm;	
	
	@FindBy(id="producContextInfoForm:policyDetail_policyNumTxt")
	private WebElement quoteNumber;

	public QuoteConsolidatedViewPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	

	public void processAction(String actionLabel) {
		Select select = new Select(selectActionCombobox);
		
		select.selectByVisibleText(actionLabel);
		
		processSelectedActionButton.click();
	}
	
	public String getStatus() {
		try {
			return policyStatus.getText();
		} catch (NoSuchElementException e) {
			return null;
		}		
	}	
	
	public boolean isComponentHeaderDisplayed(String componentName) {
		String id = "productConsolidatedViewForm:add" + componentName;
		return ElementUtils.exists(consolidatedViewForm, By.id(id));
	}

	public String getQuoteNumber() {
		try {
			return quoteNumber.getText();
		} catch (NoSuchElementException e) {
			return null;
		}	
	}
	
}
