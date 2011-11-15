
package com.exigen.ipb.product.pages;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents a page displayed during product import, when there are
 * conflicting ConcreteUserComponents
 * 
 * @author ggrazevicius
 * @since 3.9
 */
public class CUCConflictPage extends AbstractWebPage {
	
	@FindBy(id="confirmCucOverwriteForm:confirmOverwrite")
	private WebElement overrideButton;
	
	@FindBy(id="confirmCucOverwriteForm:confirmIgnore")
	private WebElement skipButton;
	
	@FindBy(id="confirmCucOverwriteForm:confirmCancel")
	private WebElement cancelButton;
	
	public CUCConflictPage(WebDriver driver, PageConfiguration configuration) {
		super(driver, configuration);
	}
	
	public void clickOverride() {
		overrideButton.click();
	}
	
	public void clickSkip() {
		skipButton.click();
	}
	
	public void clickCancel() {
		cancelButton.click();
	}
}
