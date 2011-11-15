package com.exigen.ipb.product.pages.dialogs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductWorkspacePage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**

 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */
public class WorkspaceCopyPopUp extends AbstractWebPage {
	
	@FindBy(id="copyWorkspace:workspaceName")
	private WebElement inputFieldNewName;
	
	@FindBy(id="copyWorkspace:saveBtn")
	private WebElement buttonSave;
	
	public WorkspaceCopyPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public WorkspaceCopyPopUp setCopyName(String newName) {
		inputFieldNewName.clear();
		inputFieldNewName.sendKeys(newName);
		
		return this;
	}
	
	public ProductWorkspacePage save() {
		buttonSave.click();
		return create(ProductWorkspacePage.class);
	}	
}