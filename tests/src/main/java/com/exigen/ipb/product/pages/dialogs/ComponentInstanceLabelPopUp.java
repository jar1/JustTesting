package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Component instance label configuration popup for selenium2 tcs
 * @author mulevicius
 * @since 3.9
 */
public class ComponentInstanceLabelPopUp extends AbstractWebPage {
	
	@FindBy(id="instanceConfigForm:currComponentName")
	private WebElement currentComponentName;
	
	@FindBy(id="instanceConfigForm:instanceConfigEdit_instanceLabelExpression")
	private WebElement labelExpression;
	
	@FindBy(id="instanceConfigForm:instanceConfigOkBtn")
	private WebElement okButton;
	
	@FindBy(id="instanceConfigForm:instanceConfigRemoveBtn")
	private WebElement removeButton;
	
	@FindBy(id="instanceConfigForm:instanceConfigCancelBtn")
	private WebElement cancelButton;
	
	public ComponentInstanceLabelPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public String getCurrentComponentName() {
		return currentComponentName.getText();
	}
	
	public String getLabelExpression() {
		return labelExpression.getText();
	}

	public ComponentInstanceLabelPopUp setLabelExpression(String text) {
		labelExpression.clear();
		labelExpression.sendKeys(text);
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
