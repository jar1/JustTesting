package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductActionsPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.ProductAction;

/**
 * Page Object that represents pop-up of assignable actions in product actions page.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AssignActionDialog extends AbstractParentedWebPage<ProductActionsPage> {

	private static final String ASSIGN_ID_FORMAT = 
			"assignToActionForm:assignToFormTable:%d:selectInd";
	
	@FindBy(id="assignToActionForm:assignToFormTable")
	private WebElement formTable;
	
	@FindBy(id="assignToActionForm:saveBtn")
	private WebElement saveButton;
	
	private boolean cacheActions = true;
	
	private List<ProductAction> cachedActions;

	public AssignActionDialog(WebDriver driver, PageConfiguration conf, ProductActionsPage parent) {
		super(driver, conf, parent);
	}
	
	/**
	 * @return  A list of assignable actions.
	 */
	public List<ProductAction> getActions() {
// test fails: for some reason cachedActions != null on first run
//		if (cachedActions != null) {
//			return cachedActions;
//		}
		
		List<ProductAction> actions = new ArrayList<ProductAction>();
		
		List<WebElement> spans = formTable.findElements(By.tagName("span"));
		for (WebElement span : spans) {
			actions.add(ProductAction.valueOf(ElementUtils.getText(span, getDriver())));
		}
		
//		if (cacheActions) {
//			cachedActions = actions;
//		}
		
		return actions;
	}
	
	/**
	 * Assigns specified action to current state.
	 * 
	 * @param action  The action to be assigned
	 * @return
	 */
	public AssignActionDialog assign(ProductAction action) {
		String assignId = String.format(ASSIGN_ID_FORMAT, getActions().indexOf(action));
		formTable.findElement(By.id(assignId)).click();
		
		return this;
	}
	
	/**
	 * Assigns one or more actions to current state.
	 * 
	 * @param actions  Actions to be assigned.
	 * @return
	 */
	public AssignActionDialog assign(ProductAction... actions) {
		for (ProductAction action : actions) {
			assign(action);
		}
		return this;
	}

	/**
	 * Saves state of assigned actions.
	 * @return
	 */
	public ProductActionsPage clickSave() {
		saveButton.click();
		return parent();
	}
	
	/**
	 * Cancels any changes made.
	 * @return
	 */
	public ProductActionsPage cancel() {
		return parent();
	}
	
	/**
	 * Determines whether actions are cached.
	 * @return  True if cached, otherwise - false.
	 */
	public boolean isCacheActions() {
		return cacheActions;
	}
	
	/**
	 * Changes whether actions are cached or not.
	 * 
	 * @param cacheActions  Indicator.
	 */
	public void setCacheActions(boolean cacheActions) {
		this.cacheActions = cacheActions;
		if (!cacheActions) {
			clearCachedActions();
		}
	}
	
	/**
	 * Clear all cached actions.
	 */
	public void clearCachedActions() {
		cachedActions = null;
	}
	
}
