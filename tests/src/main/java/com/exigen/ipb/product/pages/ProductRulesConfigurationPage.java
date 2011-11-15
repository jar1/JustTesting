package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.ProductMoveToGroupPopUp;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import org.springframework.util.StringUtils;

/**
 * Class that represents main product rules configuration screen. Used in 
 * Selenium2 tests.
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductRulesConfigurationPage extends AbstractWebPage {

	private static final String ACTION_ADD_RULE      = "addRule";
	private static final String ACTION_REMOVE_RULE   = "remove";
	private static final String HTML_ANCHOR_TAG		 = "a";
	private static final String HTML_DISABLED_ATTRIBUTE = "disabled";
	private static final String HTML_DISABLED_ATTRIBUTE_TRUE = "true";
	private static final String HTML_ONLICK_ATTRIBUTE	= "onclick";
	private static final String HTML_JS_CLICK_DISABLE	= "return false;";
	
	
	public static final String TYPE_RULE_VISIBILITY = "VISIBILITY";
	public static final String TYPE_RULE_GLOBAL     = "GLOBAL";
	public static final String TYPE_RULE_STANDARD   = "STANDARD";
// --------
	@FindBy(id="productUpdateForm:businessRulesTree")
	private WebElement rulesComponentTree;
	
	@FindBy(id="productUpdateForm:bussinessRuleGlobalTree")
	private WebElement rulesGlobalTree;	
	
	@FindBy(id="productUpdateForm:bussinessRuleTabVisTree")
	private WebElement rulesVisibilityTree;
	
	@FindBy(id="productUpdateForm:filterRulesBy")
	private WebElement dropDownFilterRulesBy;
	
	@FindBy(id="productUpdateForm:selectedRulesTable:tb")
	private WebElement ruleExtensionTable;
	
	@FindBy(id="productUpdateForm:selectedRulesTable:tb")
	private WebElement rulesTable;
	
	@FindBy(id="productUpdateForm:selectedTabVisRulesTable:tb")
	private WebElement rulesVisibilityTable;
	
	@FindBy(id="productUpdateForm:actionDropdown")
	private WebElement dropDownRuleActions;
	
	@FindBy(id="productUpdateForm:goBtn")
	private WebElement buttonGoActions;
	
	@FindBy(id="productUpdateForm:addTabVisibilityRule")
	private WebElement buttonAddTabVisibilytiRule;	
	
	@FindBy(id="confirmRemoveRuleActionDialog_form:buttonYes")
	private WebElement buttonRemoveRuleYes;	
	
	@FindBy(id="productUpdateForm:removeRulesBtn")
	private WebElement buttonRemoveAction;	
	
	@FindBy(id="productUpdateForm:swtichToGroupedRules")
	private WebElement linkConfigureAllRuleGroups;	
	
	@FindBy(id="productUpdateForm:ruleSetActivation")
	private WebElement buttonActivateVersion;
	
	@FindBy(id="productUpdateForm:ruleSetDeactivationButton")
	private WebElement buttonDeactivateVersion;
	
	@FindBy(id="productUpdateForm:inactiveRuleSetRemove")
	private WebElement buttonRemoveInactiveVersion;

// --------
	@FindBy(id="productUpdateForm:Next")
	private WebElement buttonNext;
	
	@FindBy(id="topCancelLink")
	private WebElement buttonCancel;	
	
	@FindBy(id="errorsForm:back")
	private WebElement buttonBackError;		
	
	@FindBy(id="contents")
	private WebElement contents;
	
// -------- 
	@FindBy(id="productUpdateForm:ruleSetDialog")
	private WebElement buttonNewVersion;

	@FindBy(id="ruleSetForm:newRuleSetEffDate_newRuleSetEffectiveDate")
	private WebElement inputNewRuleSetEffectiveDate;
	
	@FindBy(id="ruleSetForm:saveBtn")
	private WebElement buttonCreate;
	
// --------
	@FindBy(id="productUpdateForm:lastRuleSetVersion")
	private WebElement lastRuleSetVersionOutput;
	
	@FindBy(id="productUpdateForm:activeRuleSetVersion")
	private WebElement activeRuleSetVersionOutput;
	
	
	public ProductRulesConfigurationPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}	
	
	public ProductRulesConfigurationPage clickActivateVersion() {
		buttonActivateVersion.click();
		return this;
	}
	
	public ProductRulesConfigurationPage clickDeactivateVersion() {
		buttonDeactivateVersion.click();
		return this;
	}
	
	public ProductRulesConfigurationPage clickRemoveInactiveVersion() {
		buttonRemoveInactiveVersion.click();
		return this;
	}
	
	public ProductRulesConfigurationPage filterRulesBy(String criteria) {		
		WebElement filterBy = dropDownFilterRulesBy.findElement(By.cssSelector("option[value="+ criteria +"]"));
		
		if(filterBy != null) {			
			filterBy.click();
			return this;
		}		
		return null;		
	}
	
	public boolean isRuleNameExists(String ruleName) {
		List<WebElement> tableRows = rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				return true;
			}
			i++;
		}	
		return false;
	}
	
	public boolean checkRuleName(String ruleName, String type) {
		List<WebElement> tableRows;
		String path;
		
		if(type.equals(TYPE_RULE_VISIBILITY)) {
			tableRows = rulesVisibilityTable.findElements(By.cssSelector("tr.rich-table-row"));
			path = "productUpdateForm:selectedTabVisRulesTable:";
		}
		else {
			tableRows = rulesTable.findElements(By.cssSelector("tr.rich-table-row"));
			path = "productUpdateForm:selectedRulesTable:";
		}

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id(path+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				return true;
			}
			i++;
		}	
		return false;
	}
	
	public String getRuleDescription(String att) {
		List<WebElement> tableRows = rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(att)) {
				return table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_description")).getText();
			}
			i++;
		}	
		return "";
	}
	
	public String getRuleDescription(String ruleName, String type) {
		List<WebElement> tableRows;
		String path;
		if(type.equals("VISIBILITY")) {
			tableRows = rulesVisibilityTable.findElements(By.cssSelector("tr.rich-table-row"));
			path = "productUpdateForm:selectedTabVisRulesTable:";
		}
		else {
			tableRows = rulesTable.findElements(By.cssSelector("tr.rich-table-row"));
			path = "productUpdateForm:selectedRulesTable:";
		}

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id(path+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				return table.findElement(By.id(path+ i +":column_description")).getText();
			}
			i++;
		}	
		return "";
	}
	
	public String getRuleVersion(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				return table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_ruleSetVersionsString")).getText();
			}
			i++;
		}	
		return "";
	}
	
	public String getRuleEffDate(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				return table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":globalEffective")).getText();
			}
			i++;
		}		
		return "";
	}
	
	public void clickGo() {		
		buttonGoActions.click();
	}	
	
	public boolean selectRule(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":ruleIsSelected")).click();
				return true;
			}
			i++;
		}		
		return false;
	}
	
	public ProductAddEditRulePage addRule() {		
		WebElement addRule = dropDownRuleActions.findElement(By.cssSelector("option[value="+ ACTION_ADD_RULE +"]"));		
		addRule.click();
		buttonGoActions.click();
		
		return create(ProductAddEditRulePage.class);
	}
	public ProductAddEditRulePage editRule(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":editAttrRuleLink")).click();
				return create(ProductAddEditRulePage.class);
			}
			i++;
		}		
		return null;
	}	
	
	public ProductAddEditRulePage copyRule(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":copyAttrRuleLink")).click();
				return create(ProductAddEditRulePage.class);
			}
			i++;
		}		
		return null;
	}
	
	public ProductAddEditRulePage removeRule(String ruleName) {
		selectRule(ruleName);
		WebElement removeRule = dropDownRuleActions.findElement(By.cssSelector("option[value="+ ACTION_REMOVE_RULE +"]"));
		
		if(removeRule != null) {			
			removeRule.click();			
			buttonRemoveAction.click();
			buttonRemoveRuleYes.click();
			return create(ProductAddEditRulePage.class);
		}		
		return null;
	}
	
	public ProductAddEditTabVisibilityRulePage addTabVisibilityRule() {
		buttonAddTabVisibilytiRule.click();
		
		return create(ProductAddEditTabVisibilityRulePage.class);
	}	
	
	public ProductAddEditTabVisibilityRulePage editVisibilityRule(String att) {		
		List<WebElement> tableRows =
					rulesVisibilityTable.findElements(By.cssSelector("tr.rich-table-row"));		

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedTabVisRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(att)) {
				table.findElement(By.id("productUpdateForm:selectedTabVisRulesTable:"+ i +":editAttrRuleLink")).click();
				return create(ProductAddEditTabVisibilityRulePage.class);
			}
			i++;
		}		
		return null;
	}
	
	public ProductRulesConfigurationPage createNewVersion(String newEffDate) {
		buttonNewVersion.click();
		inputNewRuleSetEffectiveDate.clear();
		inputNewRuleSetEffectiveDate.sendKeys(newEffDate);
		buttonCreate.click();

		return this;
	}
	
	public ProductMoveToGroupPopUp moveToGroupRule(String ruleName) {		
		List<WebElement> tableRows =
				rulesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":column_name"));			
			if (td.getText().equals(ruleName)) {
				table.findElement(By.id("productUpdateForm:selectedRulesTable:"+ i +":moveRuleToGroupLink")).click();
				return create(ProductMoveToGroupPopUp.class);
			}
			i++;
		}		
		return null;
	}
	
	/**
	 * retrieves rule names from base rules screen
	 * @return 
	 */
	public List<String> getBasicRuleViewRuleName() {
		return extractViewRuleNamesByCssSelector("tr.rich-table-row");
	}
	
	/**
	 * retrieves rule names from all group screen
	 * @return 
	 */
	public List<String> getGroupViewRuleNames() {
		return extractViewRuleNamesByCssSelector("tr.rich-subtable-row");
	}
	
	public List<String> getGroupViewRuleGroups(String ruleName) {
		List<WebElement> tableRows = ruleExtensionTable.findElements(By.cssSelector("tr.rich-subtable-row"));
		List<String> groupList = new ArrayList<String>();	

		for (WebElement table : tableRows) {	
			WebElement td = table.findElement(By.cssSelector("td:nth-child(2)"));
			if (td.getText().equals(ruleName)) {				
				List<WebElement> elements = table.findElements(By.cssSelector("td:nth-child(3) li"));
				for (WebElement el : elements) {
					groupList.add(el.getText());					
				}	
				return groupList;
			}
		}	
		return null;
	}		
	
	private List<String> extractViewRuleNamesByCssSelector(String selector) {
		List<String> result = new ArrayList<String>();
		
		List<WebElement> tableRows = ruleExtensionTable.findElements(By.cssSelector(selector));
		for (WebElement row : tableRows) {			
			result.add(extractRuleName(row));
		}
		
		return result;
	}
	
	private String extractRuleName(WebElement rowElement) {
		return rowElement.findElement(By.cssSelector("td:nth-child(2)")).getText();
	}	
	
	public ProductAddEditRulePage editRuleGroupExtension(String ruleName) {
		List<WebElement> tableRows = ruleExtensionTable.findElements(By.cssSelector("tr.rich-subtable-row"));

		for (WebElement table : tableRows) {			
			if (table.findElement(By.cssSelector("td:nth-child(2)")).getText().equals(ruleName)) {
				table.findElement(By.cssSelector("td:nth-child(8) a")).click();
				return create(ProductAddEditRulePage.class);
			}
		}	
		return null;
	}		
	
	public boolean moveToBaseRuleGroup(String ruleName) {
		List<WebElement> tableRows = ruleExtensionTable.findElements(By.cssSelector("tr.rich-subtable-row"));

		for (WebElement table : tableRows) {			
			WebElement td = table.findElement(By.cssSelector("tr:nth-child(2)"));			
			if (td.getText().equals(ruleName)) {
				td.findElement(By.cssSelector("td:nth-child(8) a:nth-child(2)")).click();
				return true;
			}
		}	
		return false;
	}	

	public ProductRulesConfigurationPage clickConfigureAllRuleGroups() {		
		linkConfigureAllRuleGroups.click();
		
		return this;
	}	
	
	public boolean existsTreeNode(String nodeValue) {
		WebElement treeNode = findTriangleByNodeValue(nodeValue);
		return treeNode != null;
	}
	
	public boolean existsTreeNode(String nodeValue, String type) {
		WebElement treeNode = findTriangleByNodeValue(nodeValue, type);
		return treeNode != null;
	}
	
	private ProductRulesConfigurationPage expand(WebElement treeNode) {
		if(treeNode != null) {
			WebElement treeNodeExpanded = treeNode.findElement(By.className("rich-tree-node-handleicon-expanded"));
			if("none".equals(treeNodeExpanded.getCssValue("display"))) {
			   treeNode.click();
			}
		}
		return this;
	}
	
	public ProductRulesConfigurationPage expandTreeNode(String nodeValue) {	
		return expand(findTriangleByNodeValue(nodeValue));
	}
	
	public ProductRulesConfigurationPage expandTreeNode(String nodeValue, String type) {
		return expand(findTriangleByNodeValue(nodeValue, type));
	}
	
	private WebElement findTriangleByNodeValue(String nodeValue) {
		List<WebElement> tableRows =
				rulesComponentTree.findElements(By.cssSelector("table.rich-tree-node"));
		
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.cssSelector("td.rich-tree-node-text"));
			if (td.getText().equals(nodeValue)) {
				return table.findElement(By.cssSelector("a.rich-tree-node-handle"));
			}
		}
		return null;
	}
	
	private WebElement findTriangleByNodeValue(String nodeValue, String type) {
		List<WebElement> tableRows;		
		if(type.equalsIgnoreCase(TYPE_RULE_GLOBAL)) {
			tableRows = rulesGlobalTree.findElements(By.cssSelector("table.rich-tree-node"));
		}
		else if(type.equalsIgnoreCase(TYPE_RULE_VISIBILITY)) {
			tableRows = rulesVisibilityTree.findElements(By.cssSelector("table.rich-tree-node"));
		}
		else {
			tableRows = rulesComponentTree.findElements(By.cssSelector("table.rich-tree-node"));
		}
		
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.cssSelector("td.rich-tree-node-text"));
			if (td.getText().equals(nodeValue)) {
				return table.findElement(By.cssSelector("a.rich-tree-node-handle"));
			}
		}
		return null;
	}
	
	public ProductRulesConfigurationPage selectTreeNode(String nodeValue) {		
		WebElement nodeElement = findTreeNodeByValue(nodeValue, rulesComponentTree);
				
		if (nodeElement != null) {
			nodeElement.click();
		}		
		return this;
	}
	
	public ProductRulesConfigurationPage selectTreeNode(String nodeValue, String type) {		
		WebElement nodeElement = null;
		
		if(type.equalsIgnoreCase(TYPE_RULE_GLOBAL))
			nodeElement = findTreeNodeByValue(nodeValue, rulesGlobalTree);
		else if(type.equalsIgnoreCase(TYPE_RULE_VISIBILITY))
			nodeElement = findTreeNodeByValue(nodeValue, rulesVisibilityTree);
		else			
			nodeElement = findTreeNodeByValue(nodeValue, rulesComponentTree);
				
		if (nodeElement != null) {
			nodeElement.click();
		}		
		return this;
	}
	
	private WebElement findTreeNodeByValue(String nodeValue, WebElement element) {
		List<WebElement> textCells =
				element.findElements(By.cssSelector("td.rich-tree-node-text"));
		
		for (WebElement td : textCells) {
			if (td.getText().equals(nodeValue)) {
				return td;
			}
		}		
		return null;
	}
	
	public boolean isErrorPresented() {
		try {
			contents.findElement(By.id("errorsForm"));
			return true;
		} catch(NoSuchElementException e) {
			return false;
		}
	}	
	
	public ProductAddEditRulePage clickBack() {
		buttonBackError.click();
		return create(ProductAddEditRulePage.class);
	}

	/**
	 * indicates that edit link of given rule is enabled
	 * @param ruleName
	 * @return 
	 */
	public boolean isRuleEditable(String ruleName) {
		WebElement ruleRow = resolveRuleRowByName(ruleName);
		int rowIndex = resolveRowIndex(ruleRow);
		WebElement editLink = ruleRow.findElement(By.id("productUpdateForm:selectedRulesTable:"+rowIndex+":editAttrRuleLink"));
		return HTML_ANCHOR_TAG.equals(editLink.getTagName());
	}
	
	/**
	 * indicates that copy link of given rule is enabled
	 * @param ruleName
	 * @return 
	 */
	public boolean isRuleCopyable(String ruleName) {
		WebElement ruleRow = resolveRuleRowByName(ruleName);
		int rowIndex = resolveRowIndex(ruleRow);
		WebElement copyLink = ruleRow.findElement(By.id("productUpdateForm:selectedRulesTable:"+rowIndex+":copyAttrRuleLink"));
		return HTML_ANCHOR_TAG.equals(copyLink.getTagName());
	}
	
	/**
	 * indicates that checkbox in the row of given rule is enables
	 * @param ruleName
	 * @return 
	 */
	public boolean isRuleSelectable(String ruleName) {
		WebElement ruleRow = resolveRuleRowByName(ruleName);
		int rowIndex = resolveRowIndex(ruleRow);
		WebElement selectCheckbox = ruleRow.findElement(By.id("productUpdateForm:selectedRulesTable:"+rowIndex+":ruleIsSelected"));
		return !HTML_DISABLED_ATTRIBUTE_TRUE.equals(selectCheckbox.getAttribute(HTML_DISABLED_ATTRIBUTE));
	}
	
	/**
	 * indicates that move to group link of given rule is enabled
	 * @param ruleName
	 * @return 
	 */
	public boolean isRuleMovable(String ruleName) {
		WebElement ruleRow = resolveRuleRowByName(ruleName);
		int rowIndex = resolveRowIndex(ruleRow);
		WebElement ruleVersions = ruleRow.findElement(By.id("productUpdateForm:selectedRulesTable:"+rowIndex+":column_ruleSetVersionsString"));
		String[] versions = ruleVersions.getText().split(",");
		if(versions.length > 1) {
			return false;
		}
		WebElement moveLink = ruleRow.findElement(By.id("productUpdateForm:selectedRulesTable:"+rowIndex+":moveRuleToGroupLink"));
		return !HTML_JS_CLICK_DISABLE.equals(moveLink.getAttribute(HTML_ONLICK_ATTRIBUTE));
	}
	
	/**
	 * resolves rule row by name
	 * @param ruleName
	 * @return 
	 */
	private WebElement resolveRuleRowByName(String ruleName) {
		List<WebElement> rows = rulesTable.findElements(By.tagName("tr"));
		for(int i = 0; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			String currentRuleName = extractRuleName(row);
			if(ruleName.equals(currentRuleName)) {
				return row;
			}
		}
		throw new IllegalArgumentException("Could not resokve rule with name: "+ruleName);
	}
	
	/**
	 * resolves row index of given row
	 * @param rowElement
	 * @return 
	 */
	private int resolveRowIndex(WebElement rowElement) {
		WebElement firstCell = rowElement.findElement(By.cssSelector("td:nth-child(1)"));
		String firstCellId = firstCell.getAttribute("id");
		String[] idPaths = firstCellId.split(":");
		return Integer.parseInt(idPaths[2]);
	}
	
	/**
	 * indicates that checkbox select all in the header of rules table is enabled
	 * @return 
	 */
	public boolean isAllRulesSelectable() {
		WebElement selectAllCheckbox = contents.findElement(By.id("productUpdateForm:selectedRulesTable:checkAll"));
		return !HTML_DISABLED_ATTRIBUTE_TRUE.equals(selectAllCheckbox.getAttribute(HTML_DISABLED_ATTRIBUTE));
	}
	
	public boolean isRuleControlsEnabled(String ruleName) {
		return isRuleSelectable(ruleName)
				&& isRuleEditable(ruleName)
				&& isRuleCopyable(ruleName)
				&& isRuleMovable(ruleName);
	}
	
	public int getLastRuleSetVersion() {
		return Integer.parseInt(lastRuleSetVersionOutput.getText().trim());
	}
	
	public int getActiveRuleSetVersion() {
		String activatedVersion = activeRuleSetVersionOutput.getText().trim();
		return StringUtils.hasText(activatedVersion) ? Integer.parseInt(activatedVersion) : 0;
	}	
	
	public ProductConsolidatedViewPage clickNext() {
		buttonNext.click();	
	
		return create(ProductConsolidatedViewPage.class);
	}

	public ProductConsolidatedViewPage clickCancel() {
		buttonCancel.click();
	
		return create(ProductConsolidatedViewPage.class);
	}
	
}