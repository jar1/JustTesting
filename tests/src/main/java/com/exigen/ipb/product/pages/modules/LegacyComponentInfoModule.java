package com.exigen.ipb.product.pages.modules;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.product.pages.ProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * A module that represents a part of legacy product data page that is
 * provides component information.
 * 
 * @author sstundzia
 * @since 3.9
 */
public class LegacyComponentInfoModule extends AbstractParentedWebPage<ProductDataPage> {

	@FindBy(id="topSaveLink")
	private WebElement topSaveButton;
	
	@FindBy(id="iconcomponentConfigurationEditForm:configureAppRuleMenuGroup")
	private WebElement componentPropertiesPanel;
	
	@FindBy(id="componentConfigurationEditForm:optionalQuestion")
	private WebElement optionalQuestion;
	
	@FindBy(id="componentConfigurationEditForm:defaultOptionalQuestionAnswer")
	private WebElement defaultOptionalQuestionAnwser;
	
	public LegacyComponentInfoModule(WebDriver driver, PageConfiguration conf, ProductDataPage parentPage) {
		super(driver, conf, parentPage);
	}
	
	public LegacyComponentInfoModule expandComponentPropertiesPanel() {
		componentPropertiesPanel.click();
		
		return this;
	}
	
	public String getOptionalQuestion() {
		return optionalQuestion.getAttribute("value");
	}
	
	public LegacyComponentInfoModule setOptionalQuestion(String value) {
		optionalQuestion.clear();
		optionalQuestion.sendKeys(value, Keys.RETURN);
		
		return this;
	}
	
	public String getDefaultOptionalQuestionAnwser() {
		Select select = new Select(defaultOptionalQuestionAnwser);
		
		return select.getFirstSelectedOption().getText();
	}
	
	public LegacyComponentInfoModule setDefaultOptionalQuestionAnwser(String value) {
		Select select = new Select(defaultOptionalQuestionAnwser);
		select.selectByValue(value);
		
		return this;
	}
	
	/**
	 * Saves changes that have been made through this page.
	 * @return
	 */
	public ProductDataPage save() {
		topSaveButton.click();
		
		return this.parent();
	}

}
