package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.product.pages.ProductAddEditRulePage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * Selenium2 test which checks global attributes rules creation/edition/deletion
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.TestGlobalRules.GlobalRulesAdmin
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class GlobalRulesAdminFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private static final String ACTION_DATA_GATHER 				 = "Data Gather";
	private static final String PRODUCT_CD                       = "testAutomation";
	private static final double PRODUCT_VERSION                  = 1.0D;
	private static final String PRODUCT_DIR                      = "target/test-classes/products/rulesproduct.zip";	
	
	private static final String PRODUCT_EFF_DATES                = "11/01/2010";
	private static final String PRODUCT_NEW_EFF_DATE             = "11/11/2011";
	
	private static final String CRITERIA_GLOBAL                  = "global";
	private static final String APPLIED_COMPONENT_ATTRIBUTE      = "applied_component_attribute";
	
	private static final String ATTRIBUTE_NAME                   = "Test1";
	private static final String TREE_NODE_RISKITEM               = "RiskItem (RiskItem)";
	private static final String TREE_NODE_GLOBAL                 = "testAutomation_EP1";
	
	private static final String GLOBAL_RULE_NAME_1               = "Global1";
	private static final String GLOBAL_RULE_NAME_2               = "Global2";
	private static final String SIMPLE_RULE_NAME_1               = "Simple1";
	private static final String SIMPLE_RULE_NAME_2               = "Simple2";
	private static final String GLOBAL_RULE_DEFAULT_VALUE        = "\"global\"";	
	private static final String GLOBAL_RULE_NEW_DESCRIPTION      = "Global1Description";
	
	private ProductRulesConfigurationPage productRulesConfigurationPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productRulesConfigurationPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).configureRules();
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'testAutomation' from 'target/test-classes/products/rulesproduct.zip'</li>
	 *	<li>Change product name, code and dates</li>
	 * 	<li>For 'Test1' attribute create rules 'Global1' and 'Global2' and set it to GLOBAL
	 *  	<ul><li>Check: set global rule option 'NO' didn't set rule to global</li>
	 *   		<li>Check: set global rule option 'YES' sets rule to global</li></ul></li>
	 *	<li>For 'Test1' attribute create rule 'Simple1' and 'Simple2</li>
	 * 	<li>Select 'global' in filter drop-down
	 *   	<ul><li>Check: only 'Global1' and 'Global2' rules presented</li></ul></li>
	 * 	<li>Change 'Global1' rule description
	 *   	<ul><li>Check: 'Global1' rule description changed</li></ul></li>
	 * 	<li>Deploy product</li>
	 * 	<li>Changes rule 'Global1' global effective date
	 *   	<ul><li>Check: 'Global1' rule effective date changed</li>
	 *   		<li>Check: 'Global2' rule effective date not changed</li></ul></li>
	 * 	<li>Remove 'Global1' rule
	 *   	<ul><li>Check: 'Global1' is deleted successfully </li>
	 *  		<li>Check: 'Global2' is not deleted</li>
	 *  		<li>Check: 'Simple1' is not deleted</li>
	 *  		<li>Check: 'Simple2' is not deleted</li></ul></li>
	 *  </ol>
	 */	
	@Test
	public void shouldCreateDeleteManipulateNewRules() {
	
		// expand RiskItem and select attribute in tree view
		productRulesConfigurationPage.expandTreeNode(TREE_NODE_RISKITEM);
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME);		

		createGlobalRule(GLOBAL_RULE_NAME_1);
		createGlobalRule(GLOBAL_RULE_NAME_2);
		createSimpleRule(SIMPLE_RULE_NAME_1);
		createSimpleRule(SIMPLE_RULE_NAME_2);
		
		// filters only global rules
		productRulesConfigurationPage.clickActivateVersion()
			.filterRulesBy(CRITERIA_GLOBAL)
			.selectTreeNode(TREE_NODE_GLOBAL, "GLOBAL");
		
		// check if global rules presented
		assertEquals(GLOBAL_RULE_NAME_1 +" should be Global",
				"Global", productRulesConfigurationPage.getRuleVersion(GLOBAL_RULE_NAME_1));
		assertEquals(GLOBAL_RULE_NAME_2 +" should be Global",
				"Global", productRulesConfigurationPage.getRuleVersion(GLOBAL_RULE_NAME_2));
		
		// change one attribute rule description
		productRulesConfigurationPage.filterRulesBy(APPLIED_COMPONENT_ATTRIBUTE)
				.selectTreeNode(TREE_NODE_RISKITEM)
				.editRule(GLOBAL_RULE_NAME_1)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DescriptionText, GLOBAL_RULE_NEW_DESCRIPTION)
				.clickNext();		
		
		// check if description get changed
		assertEquals(GLOBAL_RULE_NAME_1 +" description haven't changed",
				GLOBAL_RULE_NEW_DESCRIPTION, productRulesConfigurationPage.getRuleDescription(GLOBAL_RULE_NAME_1));
		
		// deploy products and changes attribute rule global effective date 
		productRulesConfigurationPage.clickNext().deployProduct()
				.configureRules()
				.editRule(GLOBAL_RULE_NAME_2)
				.setGlobalEffDate(PRODUCT_NEW_EFF_DATE)
				.clickNext();
		
		// check if proper date changed
		assertEquals(GLOBAL_RULE_NAME_1 +" wrong effective date", 
				PRODUCT_EFF_DATES, productRulesConfigurationPage.getRuleEffDate(GLOBAL_RULE_NAME_1));
		assertEquals(GLOBAL_RULE_NAME_2 +" effective date haven't changed",
				PRODUCT_NEW_EFF_DATE, productRulesConfigurationPage.getRuleEffDate(GLOBAL_RULE_NAME_2));
		
		// remove attribute rule
		productRulesConfigurationPage.removeRule(GLOBAL_RULE_NAME_1);

		// check if attribute rule deleted
		assertTrue(GLOBAL_RULE_NAME_1 + " should be deleted",
				!productRulesConfigurationPage.isRuleNameExists(GLOBAL_RULE_NAME_1));
		assertTrue(GLOBAL_RULE_NAME_2 + " should exists",
				productRulesConfigurationPage.isRuleNameExists(GLOBAL_RULE_NAME_2));
		assertTrue(SIMPLE_RULE_NAME_1 + " should existsv",
				productRulesConfigurationPage.isRuleNameExists(SIMPLE_RULE_NAME_1));
		assertTrue(SIMPLE_RULE_NAME_2 + " should exists",
				productRulesConfigurationPage.isRuleNameExists(SIMPLE_RULE_NAME_2));
	}

	private void createSimpleRule(String ruleName) {
		productRulesConfigurationPage.addRule()
			.setRuleName(ruleName)
			.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)
			.selectAction(ACTION_DATA_GATHER)
			.clickNext();
	}
	
	private void createGlobalRule(String ruleName) {
		ProductAddEditRulePage productAddEditNewRulePage = productRulesConfigurationPage.addRule()
				.setRuleName(ruleName)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, GLOBAL_RULE_DEFAULT_VALUE)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)					
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectAction(ACTION_DATA_GATHER);
		
		productAddEditNewRulePage.setGlobalEnable(false);		
		assertFalse(ruleName +" should not be Global", productAddEditNewRulePage.isGlobal());	
		
		productAddEditNewRulePage.setGlobalEnable(true);		
		assertTrue(ruleName +" should not be Global", productAddEditNewRulePage.isGlobal());
		
		productAddEditNewRulePage.clickNext();	
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
