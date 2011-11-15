package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Class that represents initial pop up for user component creation. Used in Selenium2 tests.
 * 
 * mulevicius: technically this "page" (popup) should be included in AlternativeProductDataPage, 
 * but due to expected bloatness of AlternativeProductDataPage it will have its own class.
 * 
 * @author mulevicius
 * @since 3.9
 */
public class ProductCreateUserComponentPopUp  extends AbstractWebPage {
	
	@FindBy(id="createNewComponentForm:componentName")
	private WebElement componentNameTextBox;
	
	@FindBy(id="createNewComponentForm:componentDescription")
	private WebElement componentDescriptionTextArea;
	
	@FindBy(id="createNewComponentForm:cancelBtn")
	private WebElement cancelButton;
	
	@FindBy(id="createNewComponentForm:saveBtn")
	private WebElement saveButton;
	
	public ProductCreateUserComponentPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public AlternativeProductDataPage cancel() {
		cancelButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage createUserComponent(String name, String description) {
		componentNameTextBox.sendKeys(name);
		componentDescriptionTextArea.sendKeys(description);
		saveButton.click();
		return create(AlternativeProductDataPage.class);
	}

}
