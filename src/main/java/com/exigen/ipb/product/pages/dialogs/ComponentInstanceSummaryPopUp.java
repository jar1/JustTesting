package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Component instance summary configuration popup for selenium2 tcs
 * @author mulevicius
 * @since 3.9
 */
public class ComponentInstanceSummaryPopUp extends AbstractWebPage {
	
	@FindBy(id="summaryForm:currComponentName")
	private WebElement currentComponentName;
	
	@FindBy(id="summaryForm:summaryEdit_instanceSummaryExpression")
	private WebElement summaryExpression;
	
	@FindBy(id="summaryForm:instanceConfigOkBtn")
	private WebElement okButton;
	
	@FindBy(id="summaryForm:summaryRemoveBtn")
	private WebElement removeButton;
	
	@FindBy(id="summaryForm:summaryCancelBtn")
	private WebElement cancelButton;
	
	public ComponentInstanceSummaryPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public String getCurrentComponentName() {
		return currentComponentName.getText();
	}
	
	public String getSummaryExpression() {
		return summaryExpression.getText();
	}

	public ComponentInstanceSummaryPopUp setSummaryExpression(String text) {
		summaryExpression.clear();
		summaryExpression.sendKeys(text);
		return this;
	}
	
	public AlternativeProductDataPage ok() {
		okButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage remove() {
		removeButton.click();
		return create(AlternativeProductDataPage.class);
	}
}
