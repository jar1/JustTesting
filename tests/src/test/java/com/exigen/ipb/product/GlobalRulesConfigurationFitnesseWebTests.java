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
 * Selenium2 test which checks global attributes rule configuration
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.TestGlobalRules.GlobalRulesConfiguration
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class GlobalRulesConfigurationFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                    = "testAutomation";
	private final static double PRODUCT_VERSION               = 1.0D;
	private final static String PRODUCT_DIR                   = "target/test-classes/products/rulesproduct.zip";	
		
	private static final String PRODUCT_NEW_EFF_DATE_V1       = "09/26/2014";
	private static final String PRODUCT_NEW_EFF_DATE_V2       = "11/26/2011";
	
	private static final String ATTRIBUTE_NAME                = "Test";
	private static final String TREE_NODE_RISKITEM            = "RiskItem (RiskItem)";
	private static final String TREE_NODE_GLOBAL              = "testAutomation_EP1";
	private static final String CRITERIA_GLOBAL               = "global";	
	
	private static final String SIMPLE_RULE_NAME              = "Test1Simple";
	private static final String GLOBAL_RULE_NAME              = "Test2Global";
	private static final String SIMPLE_RULE_DEFAULT_VALUE_V1  = "\"simple1st\"";
	private static final String SIMPLE_RULE_DEFAULT_VALUE_V2  = "\"simple2nd\"";
	private static final String GLOBAL_RULE_DEFAULT_VALUE     = "\"global\"";
	
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
	 * 	<li>Change product name, code and dates</li>
	 * 	<li>For 'Test1' attribute create rule 'Simple'</li>
	 * 	<li>For 'Test1' attribute create rules 'Global' and set it to GLOBAL
	 *  	<ul><li>Check: set global rule option 'NO' didn't set rule to global</li>
	 * 			<li>Check: set global rule option 'YES' sets rule to global</li></ul></li>
	 * 	<li>Select 'Test1' attribute
	 * 		<ul><li>Check: 'Global' rule version is "Global" </li></ul></li>
	 * 	<li>Changes rule 'Global' global effective date to PRODUCT_NEW_EFF_DATE_V1
	 *  	<ul><li>Check: 'Global' rule effective date changed</li></ul></li>
	 * 	<li>Activate current rule set</li>
	 * 	<li>Create new rule set version with date PRODUCT_NEW_EFF_DATE_V2</li>
	 * 	<li>Copy 'Simple' rule to new rule set version</li>
	 * 	<li>Select 'global' in filter drop-down
	 *  	<ul><li>Check: 'Global' rule version is "Global</li>
	 *  		<li>Check: 'Global' rule effective date is PRODUCT_NEW_EFF_DATE_V1</li></ul></li>
	 *  </ol>
	 */		
	@Test
	public void shouldCopyGlobalRulesRuleSet() {			
		// add Rule for Test1 attribute in RiskItem
		productRulesConfigurationPage.expandTreeNode(TREE_NODE_RISKITEM);
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME+1)		
				.addRule()
				.setRuleName(SIMPLE_RULE_NAME)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, SIMPLE_RULE_DEFAULT_VALUE_V1)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectAction("Data Gather")
				.clickNext();
		
		// add Rule for Test2 attribute
		productRulesConfigurationPage.expandTreeNode(TREE_NODE_RISKITEM);
		ProductAddEditRulePage productAddEditNewRulePage = productRulesConfigurationPage
				.selectTreeNode(ATTRIBUTE_NAME+2)		
				.addRule()
				.setRuleName(GLOBAL_RULE_NAME)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, GLOBAL_RULE_DEFAULT_VALUE)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)				
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectAction("Data Gather");
		
		// enable global, choose NO in popup
		productAddEditNewRulePage.setGlobalEnable(false);		
		assertFalse(GLOBAL_RULE_NAME + " should not be Global", productAddEditNewRulePage.isGlobal());	
			
		// enable global, choose YES in popup
		productAddEditNewRulePage.setGlobalEnable(true);		
		assertTrue(GLOBAL_RULE_NAME +" should be Global", productAddEditNewRulePage.isGlobal());
		
		// save rules
		productAddEditNewRulePage.clickNext();			

		// check if Global		
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME+2);
		assertEquals(GLOBAL_RULE_NAME +" should be Global",
				"Global", productRulesConfigurationPage.getRuleVersion(GLOBAL_RULE_NAME));
		
		// changes attribute global rule effective date
		productRulesConfigurationPage.editRule(GLOBAL_RULE_NAME)
				.setGlobalEffDate(PRODUCT_NEW_EFF_DATE_V1)
				.clickNext();
		
		// checks if date changed
		assertEquals(GLOBAL_RULE_NAME +" effective date haven't changed",
				PRODUCT_NEW_EFF_DATE_V1, productRulesConfigurationPage.getRuleEffDate(GLOBAL_RULE_NAME));
		
		// activate and create new Rule Set
		productRulesConfigurationPage.clickActivateVersion().createNewVersion(PRODUCT_NEW_EFF_DATE_V2);
		
		// copy rule to 2-nd version
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME+1)
				.copyRule(SIMPLE_RULE_NAME)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, SIMPLE_RULE_DEFAULT_VALUE_V2)
				.clickNext();
		
		// filter to only global
		productRulesConfigurationPage.clickActivateVersion()
				.filterRulesBy(CRITERIA_GLOBAL)
				.selectTreeNode(TREE_NODE_GLOBAL, "GLOBAL");
		
		// check if rule is global and date is changed
		assertEquals(GLOBAL_RULE_NAME +" version should be Global",
				"Global", productRulesConfigurationPage.getRuleVersion(GLOBAL_RULE_NAME));
		assertEquals(GLOBAL_RULE_NAME +" effective date haven't changed",
				productRulesConfigurationPage.getRuleEffDate(GLOBAL_RULE_NAME), PRODUCT_NEW_EFF_DATE_V1);
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
