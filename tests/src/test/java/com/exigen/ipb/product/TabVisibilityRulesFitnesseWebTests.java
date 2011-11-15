package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductAddEditTabVisibilityRulePage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.TabVisibilityRules
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class TabVisibilityRulesFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                     = "WorkspaceOrder";
	private final static double PRODUCT_VERSION                = 1.0D;
	private final static String PRODUCT_DIR                    = "target/test-classes/products/workspaceorder.zip";
	
	private static final String FILTER_TYPE                    = "VISIBILITY";
	private static final String FILTER_TABS_VISIBILITY         = "tabs_tabvis";
	private static final String FILTER_TABS_VISIBILITY_ROOT    = "default";
	private static final String TABS_RISKITEM                  = "Risk Item";
	private static final String TABS_POLICY                    = "Policy";
	private static final String TABS_DWELL                     = "Dwell";

	private static final String FIELD_INPUT_RIGHT              = "asd";
	
	private static final String[] DWELL_RULE                   = {
		"DwelRuleName456", 
		"Description for Dwell", 
		"RiskItem.itemName==\"" + FIELD_INPUT_RIGHT + "\""};
	
	private static final String[] RISKITEM_RULE                = {
		"RiskItemRuleName456",
		"Description for RiskItem",
		"Dwell.seqNo==\"" + FIELD_INPUT_RIGHT + "\""};

	private static final String[] POLICY_RULE                   = {
		"PolicyName456",
		"Description for Policy",
		"true"};
	
	private static final String DWELL_RULE_DESCRIPTION_EDIT    = "Dwell description is edited";	
	private static final String RISKITEM_RULE_NAME_EDIT        = "RiskItemRuleName654";
	
	private ProductRulesConfigurationPage rulesConfigurationPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		rulesConfigurationPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).configureRules();
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'WorkspaceOrder' from 'target/test-classes/products/workspaceorder.zip'</li>
	 *  <li>Change product name, code and dates</li>
	 *  <li>Go to rules configuration page</li>
	 *  <li>Select 'Tab (Visibility Rules)' in filter drop-down menu</li>
	 *  <li>Expand root and Dwell component</li>
	 *
	 *  <li>Add new rule for Dwell</li>
	 *  <li>Select root component
	 * 		<ul><li>Check: created rule with proper name and description presented</li></ul></li>
	 * 
	 *  <li>Add new rule for RiskItem</li>
	 *  <li>Select root component
	 * 		<ul><li>Check: created rules with proper names and descriptions presented</li></ul></li>
	 * 
	 *  <li>Edit Dwell rule description</li>
	 *  <li>Select root component
	 * 		<ul><li>Check: created rules with proper names and descriptions (edited) presented</li></ul></li>
	 *  
	 *  <li>Edit RiskItem rulename</li>
	 *  <li>Select root component
	 * 		<ul><li>Check: created rules with proper names (edited) and descriptions presented</li></ul></li>
	 *  
	 *   <li>Try to add rule with same name for Policy</li>
	 *   <li>On error click back and input different unique name</li>
	 *   <li>Select root component
	 * 		 <ul><li>Check: created rules with proper names and descriptions presented</li></ul></li>
	 *  
	 *   <li>Save configuration</li>
	 *   <li>Deploy product
	 * 		 <ul><li>Check: product code is proper</li></ul></li>
	 * </ol>
	 */		
	@Test
	public void shouldAddEditTabVisibilityRules() {		
		// filter by visibility rules
		rulesConfigurationPage.filterRulesBy(FILTER_TABS_VISIBILITY);

		// add tab visibility rule for Dwell tab
		rulesConfigurationPage
				.expandTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE)
				.expandTreeNode(TABS_DWELL, FILTER_TYPE);
		
		// add tab visibility rule for Dwell
		addTabVisibilityRule(TABS_DWELL, DWELL_RULE[0], DWELL_RULE[1], DWELL_RULE[2]);		
		// select root element
		rulesConfigurationPage.selectTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);
		// check rule is presented		
		assertTrue(DWELL_RULE[0] + " rule should be added with description " + DWELL_RULE[1],
				rulesConfigurationPage.checkRuleName(DWELL_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(DWELL_RULE[0], FILTER_TYPE).equals(DWELL_RULE[1]));

		// add tab visibility rule fro RiskItem
		addTabVisibilityRule(TABS_RISKITEM, RISKITEM_RULE[0], RISKITEM_RULE[1], RISKITEM_RULE[2]);		
		// select root element
		rulesConfigurationPage.selectTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);
		// check rules are presented
		assertTrue(DWELL_RULE[0] + " rule should exist with description " + DWELL_RULE[1],
				rulesConfigurationPage.checkRuleName(DWELL_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(DWELL_RULE[0], FILTER_TYPE).equals(DWELL_RULE[1]));		
		assertTrue(RISKITEM_RULE[0] + " rule should exist with description " + RISKITEM_RULE[1],
				rulesConfigurationPage.checkRuleName(RISKITEM_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(RISKITEM_RULE[0], FILTER_TYPE).equals(RISKITEM_RULE[1]));
		
		// edit Dwell description
		rulesConfigurationPage.editVisibilityRule(DWELL_RULE[0])
				.setDescription(DWELL_RULE_DESCRIPTION_EDIT)
				.clickNext();
		// select root element
		rulesConfigurationPage.selectTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);
		// check rules are presented
		assertTrue(DWELL_RULE[0] + " rule should exist with description " + DWELL_RULE_DESCRIPTION_EDIT,
				rulesConfigurationPage.checkRuleName(DWELL_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(DWELL_RULE[0], FILTER_TYPE).equals(DWELL_RULE_DESCRIPTION_EDIT));		
		assertTrue(RISKITEM_RULE[0] + " rule should exist with description " + RISKITEM_RULE[1],
				rulesConfigurationPage.checkRuleName(RISKITEM_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(RISKITEM_RULE[0], FILTER_TYPE).equals(RISKITEM_RULE[1]));

		// edit RiskItem name
		rulesConfigurationPage.editVisibilityRule(RISKITEM_RULE[0])
				.setName(RISKITEM_RULE_NAME_EDIT)
				.clickNext();
		// select root element
		rulesConfigurationPage.selectTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);
		// check rules are presented
		assertTrue(DWELL_RULE[0] + " rule should exist with description " + DWELL_RULE_DESCRIPTION_EDIT,
				rulesConfigurationPage.checkRuleName(DWELL_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(DWELL_RULE[0], FILTER_TYPE).equals(DWELL_RULE_DESCRIPTION_EDIT));		
		assertTrue(RISKITEM_RULE_NAME_EDIT + " rule should exist with description " + RISKITEM_RULE[1],
				rulesConfigurationPage.checkRuleName(RISKITEM_RULE_NAME_EDIT, FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(RISKITEM_RULE_NAME_EDIT, FILTER_TYPE).equals(RISKITEM_RULE[1]));
		
		// try to add tab visibility rule with same name for Policy tab and get error
		rulesConfigurationPage.expandTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);		
		rulesConfigurationPage.selectTreeNode(TABS_POLICY, FILTER_TYPE).addTabVisibilityRule();		
		ProductAddEditTabVisibilityRulePage addEditRulePage = create(ProductAddEditTabVisibilityRulePage.class);
		
		addEditRulePage.setName(RISKITEM_RULE_NAME_EDIT)
				.setEnabled(true)
				.setDescription(POLICY_RULE[1])
				.setExpression(POLICY_RULE[2])
				.clickNext();
		// check error and go back
		assertTrue("Error should be displayed", rulesConfigurationPage.isErrorPresented());
		rulesConfigurationPage.clickBack();		
		// set unique name and save
		addEditRulePage.setName(POLICY_RULE[0]).clickNext();	
	
		// select root element
		rulesConfigurationPage.selectTreeNode(FILTER_TABS_VISIBILITY_ROOT, FILTER_TYPE);
		// check all rules are presented
		assertTrue(DWELL_RULE[0] + " rule should exist with description " + DWELL_RULE_DESCRIPTION_EDIT,
				rulesConfigurationPage.checkRuleName(DWELL_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(DWELL_RULE[0], FILTER_TYPE).equals(DWELL_RULE_DESCRIPTION_EDIT));		
		assertTrue(RISKITEM_RULE_NAME_EDIT + " rule should exist with description " + RISKITEM_RULE[1],
				rulesConfigurationPage.checkRuleName(RISKITEM_RULE_NAME_EDIT, FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(RISKITEM_RULE_NAME_EDIT, FILTER_TYPE).equals(RISKITEM_RULE[1]));
		assertTrue(POLICY_RULE[0] + " rule should exist with description " + POLICY_RULE[1],
				rulesConfigurationPage.checkRuleName(POLICY_RULE[0], FILTER_TYPE) &&
				rulesConfigurationPage.getRuleDescription(POLICY_RULE[0], FILTER_TYPE).equals(POLICY_RULE[1]));

		// save configuration
		ProductConsolidatedViewPage productConsolidatedViewPage = rulesConfigurationPage.clickNext();		
		// deploy product
		productConsolidatedViewPage.deployProduct();
		// check product code
		assertTrue("Product code should be " + PRODUCT_CD, productConsolidatedViewPage.existsProduct(PRODUCT_CD));
	}

	private void addTabVisibilityRule(String treeNode, String name, String desc, String exp) {
		// add tab visibility rule for RiskItem tab
		rulesConfigurationPage.selectTreeNode(treeNode, FILTER_TYPE)
				.addTabVisibilityRule()
				.setName(name)
				.setEnabled(true)
				.setDescription(desc)
				.setExpression(exp)
				.clickNext();
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
}