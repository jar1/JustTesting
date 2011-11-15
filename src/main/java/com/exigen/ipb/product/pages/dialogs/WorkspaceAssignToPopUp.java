package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductWorkspacePage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * 
 * @author mulevicius
 * @since 3.9
 */
public class WorkspaceAssignToPopUp extends AbstractWebPage {
	
	@FindBy(id="assignToForm:assignToFormTable:tbody_element")
	private WebElement assignToTable;
	
	@FindBy(id="assignToForm:saveBtn")
	private WebElement saveButton;
	
	public WorkspaceAssignToPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public WorkspaceAssignToPopUp assignTo(String actionName) {
		List<WebElement> rows = new ArrayList<WebElement>();
		rows.addAll(assignToTable.findElements(By.cssSelector("tr.evenRow")));
		rows.addAll(assignToTable.findElements(By.cssSelector("tr.oddRow")));
		
		for(WebElement tr : rows) {
			WebElement cell = tr.findElement(By.cssSelector("td.cell"));
			if(cell.getText().equals(actionName)) {
				WebElement checkBox = tr.findElement(By.tagName("input"));
				if(checkBox.getAttribute("type").equals("checkbox")) {
					ElementUtils.setSelected(checkBox, true);
					return this;
				}
			}
		}
		return null;
	}
	
	public ProductWorkspacePage clickSave() {
		saveButton.click();
		return create(ProductWorkspacePage.class);
	}
}
