package com.exigen.ipb.product.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.ProductConfirmMergePopUp;
import com.exigen.ipb.product.pages.dialogs.ProductCreateUserComponentPopUp;
import com.exigen.ipb.product.pages.modules.AttributeModule;
import com.exigen.ipb.product.pages.modules.CommandsHistoryModule;
import com.exigen.ipb.product.pages.modules.ComponentInfoModule;
import com.exigen.ipb.product.pages.modules.PageModule;
import com.exigen.ipb.product.pages.modules.PageModules;
import com.exigen.ipb.product.pages.modules.TagModule;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.PageUtil;

/**
 * Class that represents main page of alternate component view. Used in 
 * Selenium2 tests.
 * 
 * @author gzukas
 * @author mulevicius
 * @since 3.9
 */
public class AlternativeProductDataPage extends AbstractWebPage {

	public static final String COMPONENT_PORTS_VIEW = "ComponentPortsView";
	public static final String COMPONENT_RELATIONS_VIEW = "ComponentRelationsView";
	public static final String ALL_COMPONENTS_VIEW = "AllComponentsView";
	public static final String COMPONENT_TAGS_VIEW = "ComponentTagsView";
	
	@FindBy(id="componentsDetails")
	private WebElement componentsDetails;
	
	@FindBy(id="treeForm:componentTree")
	private WebElement componentTree;
	
	@FindBy(id="topSaveLink")
	private WebElement topSaveButton;
	
	@FindBy(id="topCancelLink")
	private WebElement topCancelButton;
	
	@FindBy(id="componentButtonForm:replaceComponentButton")
	private WebElement replaceButton;

	@FindBy(id="treeForm:viewModeSelection")
	private WebElement viewModeSelection;
	
	@FindBy(id="plainComponentForm:createNewLink")
	private WebElement createUserCmpButton;
	
	@FindBy(id="plainComponentForm:removeUserComponentLink")
	private WebElement removeUserCmpButton;
	
	@FindBy(id="plainComponentForm:previewComponentButton")
	private WebElement previewUserCmpButton;

	@FindBy(id="treeForm:searchCriteria")
	private WebElement searchCriteria;
	
	@PageModule(TagModule.class)
	private TagModule tagModule;

	@PageModule(ComponentInfoModule.class)
	private ComponentInfoModule componentInfoModule;
	
	@PageModule(AttributeModule.class)
	private AttributeModule attributeModule;
	
	@PageModule(CommandsHistoryModule.class)
	private CommandsHistoryModule commandsHistoryModule;

	public AlternativeProductDataPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
		PageModules.initModules(driver, conf, this);
	}

	/**
	 * @return Component Info module that wraps 'Component Info' panel.
	 */
	public ComponentInfoModule getComponentInfoModule() {
		return componentInfoModule;
	}
	
	/**
	 * @return Attribute module that wraps 'Component Attributes' panel.
	 */
	public AttributeModule getAttributeModule() {
		return attributeModule;
	}

	/**
	 * @return Tagging module that wraps 'Component Tags' panel.
	 */
	public TagModule getTagModule() {
		return tagModule;
	}
	
	/**
	 * @return Commands History module that wraps 'Commands History' panel in alternative view.
	 */
	public CommandsHistoryModule getCommandsHistoryModule() {
		return commandsHistoryModule;
	}
	
	/**
	 * Searches component tree for given value.
	 * 
	 * @param value  Search criteria.
	 * @return
	 */
	public AlternativeProductDataPage search(String value) {
		searchCriteria.clear();
		searchCriteria.sendKeys(value != null ? value : "", Keys.RETURN);
		return this;
	}
	
	/**
	 * @param viewMode option to set view mode in choose box for tree (eg. ComponentPortsView)
	 */
	public AlternativeProductDataPage setViewMode(String viewMode) {
		viewModeSelection.findElement(By.cssSelector("option[value='"+ viewMode +"']")).click();
		return this;
	}
	
	public ProductCreateUserComponentPopUp openNewUserComponentPopUp() {
		createUserCmpButton.click();
		return create(ProductCreateUserComponentPopUp.class);
	}
	
	public AlternativeProductDataPage removeUserComponent() {
		removeUserCmpButton.click();
		return this;
	}
	
	public ProductComponentPreviewPage previewUserComponent() {
		PageUtil.waitTillElementLoaded(getDriver(), getWait(), "plainComponentForm:previewComponentButton");
		previewUserCmpButton.click();
		return create(ProductComponentPreviewPage.class);
	}
	
	/**
	 * Assigns component as a root. The name of component should be equal to
	 * value found in component table.
	 * 
	 * @param fullComponentName  The name of component as it appears in table.
	 */
	public AlternativeProductDataPage assignRoot(String fullComponentName) {
		return assignRoot(findComponent(fullComponentName));
	}
	
	/**
	 * Assigns component as a root.
	 * 
	 * @param name  The short name of component.
	 * @param version  The version of component.
	 * @return
	 */
	public AlternativeProductDataPage assignRoot(String name, double version) {
		return assignRoot(findComponent(name, version));
	}
	
	private AlternativeProductDataPage assignRoot(WebElement element) {
		element.findElement(By.linkText("Assign")).click();
		return this;
	}

	/**
	 * Connects new component to port. Port name should be equal to port node
	 * value (eg. billingInfo [1]).
	 * 
	 * @param portValue  The port value to connect to.
	 * @param componentName  The name of component.
	 * @param version  The version of component.
	 * @return
	 */
	public AlternativeProductDataPage connectComponent(String portValue, String componentName, double version) {
		selectTreeNode(portValue);
		WebElement componentRow = findComponent(componentName, version);//findComponentByName(componentName);

		// Somehow selenium selectors, except By.tagName do not help here. 
		for (WebElement actionLink : componentRow.findElements(By.tagName("a"))) {
			if (actionLink.getText().equals("Connect")) {
				actionLink.click();
				break;
			}
		}
		
		return this;
	}

	/**
	 * Determines whether component is displayed.
	 * 
	 * @param fullComponentName  The full name of component.
	 * @return  True if component is visible, otherwise - false.
	 */
	public boolean isComponentDisplayed(String fullComponentName) {
		return findComponent(fullComponentName) != null;
	}

	/**
	 * Determines whether component is displayed.
	 * 
	 * @param componentName  The name of component.
	 * @param version  The version of component.
	 * @return  True if component is visible, otherwise - false.
	 */
	public boolean isComponentDisplayed(String componentName, double version) {
		return findComponent(componentName, version) != null;
	}
	
	/**
	 * Selects node from component tree.
	 * @param nodeValue  The value of node to be selected.
	 */
	public AlternativeProductDataPage selectTreeNode(String nodeValue) {
		WebElement nodeElement = findTreeNodeByValue(nodeValue);
		if (nodeElement != null) {
			nodeElement.click();
		}
		
		return this;
	}

	/**
	 * Determines whether tree node with given value does exist.
	 * @param nodeValue  The value of node to be checked.
	 */
	public boolean isTreeNodeDisplayed(String nodeValue) {
		return findTreeNodeByValue(nodeValue) != null;
	}
	
	/**
	 * @return  Visible tree nodes in linear top-bottom manner.
	 */
	public List<String> getTreeNodes() {
		return ElementUtils.getTextValues(
				componentTree, By.cssSelector("td.rich-tree-node-text"));
	}
	
	/**
	 * Expands node in component tree by node label. If node is already expanded then nothing happens.
	 * 
	 * @param nodeValue  The label of node to expand.
	 */
	public AlternativeProductDataPage expandTreeNode(String nodeValue) {
		WebElement treeNode = findTriangleByNodeValue(nodeValue);
		if(treeNode != null) {
			WebElement treeNodeCollapsed = treeNode.findElement(By.className("rich-tree-node-handleicon-collapsed"));
			WebElement treeNodeExpanded = treeNode.findElement(By.className("rich-tree-node-handleicon-expanded"));
			if(treeNodeCollapsed == null || treeNodeExpanded == null) {
				return this;
			}
			
			if(treeNodeCollapsed.isDisplayed()) {
				treeNode.click();
			}
			
			if(!treeNodeCollapsed.isDisplayed() && treeNodeExpanded.isDisplayed()) {
				return this;
			}
			
		}
		
		return this;
	}
	
	/**
	 * Checks if tree node is expanded by node label.
	 * 
	 * @param nodeValue  The label of node to check if expanded.
	 * @return  True if node is expanded.
	 */
	public boolean isExpandedTreeNode(String nodeValue) {
		WebElement treeNode = findTriangleByNodeValue(nodeValue);
		if(treeNode != null) {
			WebElement treeNodeExpanded = treeNode.findElement(By.className("rich-tree-node-handleicon-expanded"));
			if(treeNodeExpanded != null && treeNodeExpanded.isDisplayed()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Replaces component by clicking on replace button.
	 * @return
	 */
	public ProductConfirmMergePopUp replace() {
		replaceButton.click();
		return create(ProductConfirmMergePopUp.class); 
	}
	
	/**
	 * Saves changes that have been made through this page.
	 * @return
	 */
	public ProductConsolidatedViewPage clickSave() {
		topSaveButton.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	/**
	 * Cancels changes that have been made through this page and returns to consolidated view.
	 * @return
	 */
	public ProductConsolidatedViewPage cancel() {
		topCancelButton.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
	/**
	 * Returns node element (table cell) of componentTree with given name.
	 * 
	 * @param nodeValue  The node of value to be found.
	 * @return  Node table cell element. If not found, null is returned instead.
	 */
	private WebElement findTreeNodeByValue(String nodeValue) {
		List<WebElement> textCells =
				componentTree.findElements(By.cssSelector("td.rich-tree-node-text"));
		
		for (WebElement td : textCells) {
			if (td.getText().equals(nodeValue)) {
				return td;
			}
		}
		
		return null;
	}

	/**
	 * Returns triangle element of componentTree for given name.
	 * 
	 * @param nodeValue  The node of value to be found.
	 * @return  Triangle element. If not found, null is returned instead.
	 */
	private WebElement findTriangleByNodeValue(String nodeValue) {
		List<WebElement> tableRows =
				componentTree.findElements(By.cssSelector("table.rich-tree-node"));
		
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.cssSelector("td.rich-tree-node-text"));
			if (td.getText().equals(nodeValue)) {
				return table.findElement(By.cssSelector("a.rich-tree-node-handle"));
			}
		}
		return null;
	}

	/**
	 * @param fullComponentName  The full name of component.
	 * @return  Component element (table row) of component found.
	 */
	private WebElement findComponent(String fullComponentName) {
		return findComponentById(formatComponentId(fullComponentName));
	}
	
	/**
	 * @param componentName  The name of component.
	 * @param version  The version of component.
	 * @return  Component element (table row) of component found.
	 */
	private WebElement findComponent(String componentName, double version) {
		return findComponentById(formatComponentId(componentName, version));
	}
	
	private WebElement findComponentById(String id) {
		componentsDetails.getAttribute("id");
		return componentsDetails.findElement(By.id(id));
	}
	
	/**
	 * Generates component ID from its full name.
	 * 
	 * @param fullComponentName  The full name of component.
	 * @return  The ID of component.
	 */
	private String formatComponentId(String fullComponentName) {
		return fullComponentName.replaceFirst(" " , "").replaceAll("[ |.]", "_");
	}
	
	/**
	 * Generates component ID from its name and version.
	 * 
	 * @param name  The name of component.
	 * @param version  The version of component.
	 * @return  The ID of component.
	 */
	private String formatComponentId(String name, double version) {
		return new StringBuilder(name)
			.append("Component_")
			.append(String.valueOf(version).replace(".", "_"))
			.toString();
	}

	public boolean isDisplayedCreateNewUserComponentButton() {
		if(createUserCmpButton != null) {
			return createUserCmpButton.isDisplayed();
		}
		return false;
	}
	
	public boolean isDisplayedRemoveUserComponentButton() {
		if(removeUserCmpButton != null) {
			return removeUserCmpButton.isDisplayed();
		}
		return false;
	}
}