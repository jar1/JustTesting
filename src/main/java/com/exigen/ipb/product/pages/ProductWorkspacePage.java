package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.WorkspaceAssignToPopUp;
import com.exigen.ipb.product.pages.dialogs.WorkspaceConfigureEntityPopUp;
import com.exigen.ipb.product.pages.dialogs.WorkspaceCopyPopUp;
import com.exigen.ipb.product.pages.dialogs.WorkspaceCreateNewPopUp;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * 
 * @author mulevicius
 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */
public class ProductWorkspacePage extends AbstractWebPage {
	
	@FindBy(id="productUpdateForm:workspaceConfigTree")
	private WebElement workspaceTree;
	
	@FindBy(id="topSaveLink")
	private WebElement saveButton;
	
	@FindBy(id="productUpdateForm:body_avaibleComponentsTable:0:assignComponent")
	private WebElement topAvailableBlocksAssign;
	
	@FindBy(id="productUpdateForm:entityViewConfigurationDialogButton")
	private WebElement configureEntityButton;
	
	@FindBy(id="productUpdateForm:assignToLink")
	private WebElement assignToLink;
	
	@FindBy(id="productUpdateForm:addNewTab")
	private WebElement actionAdd;
	
	@FindBy(id="productUpdateForm:newTabName")
	private WebElement inputFieldNewNameTab;	
	
	@FindBy(id="productUpdateForm:addNewTabOK")
	private WebElement buttonAddNewTabOK;	
	
	@FindBy(id="productUpdateForm:renameCurrent")
	private WebElement actionRename;
	
	@FindBy(id="productUpdateForm:renameTabNameInput")
	private WebElement inputFieldRenameTab;
	
	@FindBy(id="productUpdateForm:renameCurrentOK")
	private WebElement buttonRenameTabOK;
		
	@FindBy(id="productUpdateForm:removeCurrent")
	private WebElement actionRemove;	
	
	@FindBy(id="confirmRemoveTabActionDialog_form:buttonYes")
	private WebElement buttonRemoveTabYes;
	
	@FindBy(id="productUpdateForm:assignedActions")
	private WebElement fieldAssignedActions;
	
	@FindBy(id="productUpdateForm:createNewWorkspaceLink")
	private WebElement linkCreateNew;
	
	@FindBy(id="productUpdateForm:copyWorkspaceLink")
	private WebElement linkCopy;		
	
	@FindBy(id="productUpdateForm:removeWorkspaceLink")
	private WebElement linkRemove;	
	
	@FindBy(id="confirmRemoveWorkspaceActionDialogForm:okBtn")
	private WebElement buttonRemoveWorkspaceYes;
	
	@FindBy(id="productUpdateForm:workspaceSelect")
	private WebElement dropDownWorkspace;	
	
	@FindBy(id="productUpdateForm:workspaceTypeSelect")
	private WebElement dropDownWorkspaceStyle;	
	
	@FindBy(id="productUpdateForm:styleSheetSelect")
	private WebElement dropDownStyleSheet;
	
	@FindBy(id="productUpdateForm:milestoneInd")
	private WebElement checkBoxMilestone;
	
	@FindBy(id="productUpdateForm:entityViewConfigurationDialogButton")
	private WebElement buttonConfigureEntityView;
	
	
	public ProductWorkspacePage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public ProductWorkspacePage clickTreeNode(String nodeValue) {
		getNodeByValue(nodeValue).click();
		return this;
	}
	
	public WebElement getNodeByValue(String nodeValue) {
		List<WebElement> textCells = workspaceTree.findElements(By.cssSelector("td.rich-tree-node-text"));
		
		for (WebElement td : textCells) {
			if (td.getText().equals(nodeValue)) {
				return td;
			}
		}
		
		return null;
	}
	
	public boolean isTreeNodeExists(String nodeValue) {	
		if(getNodeByValue(nodeValue)==null)
			return false;
		
		return true;
	}
	
	public WorkspaceConfigureEntityPopUp configureEntityView() {
		configureEntityButton.click();
		return create(WorkspaceConfigureEntityPopUp.class);
	}
	
	public ProductWorkspacePage assignTop() {
		topAvailableBlocksAssign.click();
		return this;
	}
	
	public WorkspaceAssignToPopUp openAssignToPopUp() {
		assignToLink.click();
		return create(WorkspaceAssignToPopUp.class);
	}
	
	public String getAssignedActions() {
		return fieldAssignedActions.getText();
	}
	
	public ProductWorkspacePage addTab(String tabName) {
		actionAdd.click();
		inputFieldNewNameTab.clear();
		inputFieldNewNameTab.sendKeys(tabName);
		buttonAddNewTabOK.click();
		
		return this;
	}
	
	public ProductWorkspacePage removeTab(String tabName) {
		clickTreeNode(tabName);
		actionRemove.click();
		buttonRemoveTabYes.click();
		
		return this;
	}
	
	public ProductWorkspacePage renameTab(String tabName, String newName) {
		clickTreeNode(tabName);
		actionRename.click();
		inputFieldRenameTab.clear();
		inputFieldRenameTab.sendKeys(newName);
		buttonRenameTabOK.click();
		
		return this;
	}
	
	public WorkspaceCreateNewPopUp clickCreateNewWorkspace() {
		linkCreateNew.click();
			
		return create(WorkspaceCreateNewPopUp.class);
	}
	
	public ProductWorkspacePage createWorkspace(String newName) {
		clickCreateNewWorkspace().setNewName(newName).save();
			
		return this;
	}
	
	public WorkspaceCopyPopUp clickCopyWorkspace() {
		linkCopy.click();
			
		return create(WorkspaceCopyPopUp.class);
	}
	
	public ProductWorkspacePage copyWorkscpace(String newName) {
		clickCopyWorkspace().setCopyName(newName).save();
			
		return this;
	}
	
	public ProductWorkspacePage removeWorkspace() {
		linkRemove.click();
		buttonRemoveWorkspaceYes.click();
			
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp clickConfigureEntityView() {
		buttonConfigureEntityView.click();
			
		return create(WorkspaceConfigureEntityPopUp.class);
	}
	
	public String getCurrentWorkspace() {
		return dropDownWorkspace.findElement(By.cssSelector("option[selected='selected']")).getText();
	}
	
	public ProductWorkspacePage selectWorkspace(String nameWorkspace) {
		dropDownWorkspace.findElement(By.cssSelector("option[value='"+ nameWorkspace +"']")).click();
		
		if(!getCurrentWorkspace().equals(nameWorkspace)) {
			Assert.fail(nameWorkspace + " is not selected");
		}
		
		return this;
	}

	public ProductWorkspacePage selectWorkspaceStyle(String workspaceStyle) {		
		dropDownWorkspaceStyle.findElement(By.cssSelector("option[value='"+ workspaceStyle +"']")).click();
		
		return this;
	}
	
	public ProductWorkspacePage selectMilestone(boolean status) {		
		ElementUtils.setSelected(checkBoxMilestone, status);
		
		return this;
	}
	
	public List<String> getWorkspaceStyleList() {		
		List<WebElement> styleWorkspaceElements = dropDownWorkspaceStyle.findElements(By.tagName("option"));
		List<String> styleWorkpsaceOptions = new ArrayList<String>();	
		
		for (WebElement arr : styleWorkspaceElements) {
			styleWorkpsaceOptions.add(arr.getText());
		}
		
		return styleWorkpsaceOptions;
	}
	
	public List<String> getStyleSheetList() {		
		List<WebElement> styleSheetElements = dropDownStyleSheet.findElements(By.tagName("option"));
		List<String> styleSheetOptions = new ArrayList<String>();	
		
		for (WebElement arr : styleSheetElements) {
			styleSheetOptions.add(arr.getText());
		}

		return styleSheetOptions;
	}
	
	public List<String> getWorkspaceList() {		
		List<WebElement> workspaceElements = dropDownWorkspace.findElements(By.tagName("option"));
		List<String> workspaceOptions = new ArrayList<String>();
		
		for (WebElement arr : workspaceElements) {
			workspaceOptions.add(arr.getText());
		}
		
		return workspaceOptions;
	}
	
	public ProductConsolidatedViewPage clickSave() {
		saveButton.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	
}