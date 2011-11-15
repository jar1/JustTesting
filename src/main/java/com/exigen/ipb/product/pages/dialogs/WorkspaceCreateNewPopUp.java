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
public class WorkspaceCreateNewPopUp extends AbstractWebPage {
	
	@FindBy(id="crateNewWorkspaceForm:workspaceName")
	private WebElement inputFieldNewName;
	
	@FindBy(id="crateNewWorkspaceForm:saveBtn")
	private WebElement buttonSave;
	
	public WorkspaceCreateNewPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public WorkspaceCreateNewPopUp setNewName(String newName) {
		inputFieldNewName.clear();
		inputFieldNewName.sendKeys(newName);
		
		return this;
	}
	
	public ProductWorkspacePage save() {
		buttonSave.click();
		return create(ProductWorkspacePage.class);
	}	
}