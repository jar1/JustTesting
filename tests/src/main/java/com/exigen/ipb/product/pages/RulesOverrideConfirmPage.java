package com.exigen.ipb.product.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
*
* 
* @author jdaskevicius
* 
* @since 3.9
*/
public class RulesOverrideConfirmPage extends AbstractWebPage {
	
	@FindBy(id="errorsForm:msgList:0:messageSelect")
	private WebElement checkBoxOverride;
	
	@FindBy(id="errorsForm:msgList:0:overrideDuration:0")
	private WebElement radioDurationTerm;	
	            
	@FindBy(id="errorsForm:msgList:0:overrideReason")
	private WebElement dropDownReason;	

	@FindBy(id="errorsForm:overrideRules")
	private WebElement buttonSave;
	
	public RulesOverrideConfirmPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public RulesOverrideConfirmPage selectOverrideRule() {
		ElementUtils.setSelected(checkBoxOverride, true);
		ElementUtils.setSelected(radioDurationTerm, true);
		dropDownReason.findElement(By.cssSelector("option[value='"+ 1 +"']")).click();
		
		return this;
	}
	
	public void save() {
		buttonSave.click();
	}
	
}