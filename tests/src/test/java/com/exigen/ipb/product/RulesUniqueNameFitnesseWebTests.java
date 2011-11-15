package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.product.pages.ProductAddEditRulePage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.RulesUniqueName
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class RulesUniqueNameFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                    = "testAutomation";
	private final static double PRODUCT_VERSION               = 1.0D;
	private final static String PRODUCT_DIR                   = "target/test-classes/products/rulesproduct.zip";	

	private static final String ATTRIBUTE_NAME_1              = "Test1";
	private static final String ATTRIBUTE_NAME_2              = "Test2";
	private static final String TREE_NODE_RISKITEM            = "RiskItem (RiskItem)";
	
	private static final String TEST1_RULE_NAME               = "Test1Rule";
	private static final String TEST1_RULE_NAME_V2            = "Test1RuleRenamed";
	private static final String TEST1_RULE_DEFAULT_VALUE      = "\"Test1Default\"";
	
	private static final String TEST2_RULE_NAME               = "Test2Rule";
	private static final String TEST2_RULE_DEFAULT_VALUE      = "\"Test2Default\"";
	private static final String TEST2_RULE_ERROR_255CH        = generateString("qwertyuiopasdfghjklzxcvbnm0123456789", 255);
	
	private ProductRulesConfigurationPage productRulesConfigurationPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productRulesConfigurationPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).configureRules();
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'testAutomation' from 'target/test-classes/products/rulesproduct.zip'</li>
	 *  <li>Change product name, code and dates</li>
	 *  <li>Go to rules configuration page</li>
	 *  <li>Expand RiskItem tree node</li>
	 *  <li>For 'Test1' attribute create rule 'Test1Rule'</li>
	 *  <li>For 'Test2' attribute create rule 'Test2Rule'</li>
	 *  <li>Click RiskItem tree node
	 *  	<ul><li>Check: 'Test1Rule' and 'Test2Rule' rules presented</li></ul></li>
	 *  <li>Change 'Test1Rule' rule name
	 *  	<ul><li>Check: rule name changed successfully</li></ul></li>
	 *  <li>Try to change 'Test1Rule' rule name to name that already exists
	 *  	<ul><li>Check: error occurs</li></ul></li>
	 *  <li>Cancel name change
	 *  	<ul><li>Check: no changes made</li></ul></li>
	 *  <li>Save configuration</li>
	 *  <li>Deploy product
	 * 		<ul><li>Check: product code is not get changed</li></ul></li>
	 * </ol>  
	 */		
	@Test
	public void shouldCheckRulesUniqueNameValidation() {		
		// add Rule for Test1 attribute in RiskItem
		productRulesConfigurationPage.expandTreeNode(TREE_NODE_RISKITEM);
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME_1)		
				.addRule()
				.setRuleName(TEST1_RULE_NAME)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, TEST1_RULE_DEFAULT_VALUE)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)				
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.clickNext();
		
		// add Rule for Test2 attribute in RiskItem
		productRulesConfigurationPage.expandTreeNode(TREE_NODE_RISKITEM);
		productRulesConfigurationPage.selectTreeNode(ATTRIBUTE_NAME_2)		
				.addRule()
				.setRuleName(TEST2_RULE_NAME)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, TEST2_RULE_DEFAULT_VALUE)
				.setFieldValue(ProductAddEditRulePage.RuleFields.ErrorMsgText, TEST2_RULE_ERROR_255CH)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.clickNext();
		
		// check rules added
		productRulesConfigurationPage.selectTreeNode(TREE_NODE_RISKITEM);
		assertTrue(TEST1_RULE_NAME + " should be added", productRulesConfigurationPage.isRuleNameExists(TEST1_RULE_NAME));
		assertTrue(TEST2_RULE_NAME + " should be added", productRulesConfigurationPage.isRuleNameExists(TEST2_RULE_NAME));

		// change rule name and check
		productRulesConfigurationPage = productRulesConfigurationPage.editRule(TEST1_RULE_NAME)
				.setRuleName(TEST1_RULE_NAME_V2)
				.clickNext();
		productRulesConfigurationPage.selectTreeNode(TREE_NODE_RISKITEM);
		assertTrue(TEST1_RULE_NAME + " should be renamed to " + TEST1_RULE_NAME_V2,
				productRulesConfigurationPage.isRuleNameExists(TEST1_RULE_NAME_V2)
				&& !productRulesConfigurationPage.isRuleNameExists(TEST1_RULE_NAME));
		assertTrue(TEST2_RULE_NAME + " should exists", productRulesConfigurationPage.isRuleNameExists(TEST2_RULE_NAME));
		
		// try to change rule name to existent
		ProductAddEditRulePage productAddEditNewRulePage = productRulesConfigurationPage
				.editRule(TEST1_RULE_NAME_V2)
				.setRuleName(TEST2_RULE_NAME);	
		
		productAddEditNewRulePage.clickNext();		
				
		// check if error shown
		assertTrue("Error should be displayed", productRulesConfigurationPage.isErrorPresented());
		productRulesConfigurationPage.clickBack();
		
		// click cancel and check if no chages made
		productRulesConfigurationPage = productAddEditNewRulePage.clickCancel();
		productRulesConfigurationPage.selectTreeNode(TREE_NODE_RISKITEM);
		assertTrue(TEST1_RULE_NAME_V2 + " should exists",
				productRulesConfigurationPage.isRuleNameExists(TEST1_RULE_NAME_V2));
		assertTrue(TEST2_RULE_NAME + " should exists",
				productRulesConfigurationPage.isRuleNameExists(TEST2_RULE_NAME));		
		
		// save configuration and deploy
		ProductConsolidatedViewPage productConsolidatedViewPage = productRulesConfigurationPage.clickNext().deployProduct();
		// check product code
		assertTrue("Product code should be " + PRODUCT_CD, productConsolidatedViewPage.existsProduct(PRODUCT_CD));
	}
	
	public static String generateString(String characters, int length) {
	    char[] text = new char[length];
	    Random rng = new Random();
	    for (int i = 0; i < length; i++) {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    return new String(text);
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
