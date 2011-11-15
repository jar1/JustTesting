package com.exigen.ipb.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductAddEditRulePage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 PF smoke test which checks product overview ui conformance
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.DefaultValueValidation
 *   
 * @author mulevicius
 * @since 3.9
 */
public class RulesValueValidationFitnesseWebTests extends AbstractProductSeleniumTests {	

	private static final String ERROR_MSG =   "300charactersxxxxxxx300charactersxxxxxxx";
	
	private static final String DEFAULT_VALUE_EXP_2 = "global";
	private static final String RULE_ACTION = "Rating Attribute Value Change Event";
	private static final String DEFAULT_VALUE_EXP_1 = "simple1st";
	private static final String RULE_NAME_2 = "AddressLine2";
	private static final String RULE_NAME_1 = "AddressLine3";
	private static final String RULE_NAME_1_RENAMED = "AddressLine3renamed";
	private static final String CURRENT_ATTRIBUTE_2 = "territoryCd";
	private static final String CURRENT_ATTRIBUTE_1 = "itemName";
	private static final String COMPONENT_TREE_NODE = "RiskItem (RiskItem)";
	
	private final static String PRODUCT_CD                     = "seleniumProduct";
	private final static double PRODUCT_VERSION                = 1.0D;
	private final static String PRODUCT_DIR                    = "target/test-classes/products/withPolicyRootAndWorkspace.zip";
	
	private ProductRulesConfigurationPage productRulesConfigurationPage;

	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productRulesConfigurationPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).configureRules();
	}
	
	@Test
	public void testDefaultValueValidation() {
		// expand component node to be able to select attributes
		assertTrue("Component node "+ COMPONENT_TREE_NODE + " in tree was not found", 
				productRulesConfigurationPage.existsTreeNode(COMPONENT_TREE_NODE));
		
		// create two rules - one for each two attribute in same component
		productRulesConfigurationPage = productRulesConfigurationPage.expandTreeNode(COMPONENT_TREE_NODE)
				.selectTreeNode(CURRENT_ATTRIBUTE_1)
				.addRule()
				.setRuleName(RULE_NAME_1)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, DEFAULT_VALUE_EXP_1)
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectEvent(RULE_ACTION)
				.clickNext()
				.selectTreeNode(CURRENT_ATTRIBUTE_2)
				.addRule()
				.setRuleName(RULE_NAME_2)
				.setConditions(ProductAddEditRulePage.CONDITIONS_REQUIRED)
				.setFieldValue(ProductAddEditRulePage.RuleFields.DefExpressionText, DEFAULT_VALUE_EXP_2)
				.setFieldValue(ProductAddEditRulePage.RuleFields.ErrorMsgText, ERROR_MSG)
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectEvent(RULE_ACTION)
				.clickNext()
				.selectTreeNode(COMPONENT_TREE_NODE);
		
		// assert that rule names are correct
		assertTrue("Cannot find rule name " + RULE_NAME_1 + " in table",
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_1));
		assertTrue("Cannot find rule name " + RULE_NAME_2 + " in table", 
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_2));
		
		// rename one of the rules to different name
		productRulesConfigurationPage = productRulesConfigurationPage
				.editRule(RULE_NAME_1)
				.setRuleName(RULE_NAME_1_RENAMED)
				.clickNext()
				.selectTreeNode(COMPONENT_TREE_NODE);
		
		// assert that rule names are correct
		assertFalse("Unexpected rule name " + RULE_NAME_1 + " found - it is expected to be renamed",
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_1));
		assertTrue("Cannot find rule name " + RULE_NAME_2 + " in table",
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_2));	
		assertTrue("Cannot find rule name " + RULE_NAME_1_RENAMED + " in table",
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_1_RENAMED));
		
		// rename one of the rule names to rule name of the rule
		productRulesConfigurationPage = productRulesConfigurationPage
				.editRule(RULE_NAME_1_RENAMED)
				.setRuleName(RULE_NAME_2)
				.clickNext();
		
		// assert that error is displayed because of rule name uniqueness violation
		assertTrue("Error message related with rule name uniqueness was not displayed",
				productRulesConfigurationPage.isErrorPresented());
		
		// get back to rules configuration page and select component node
		productRulesConfigurationPage
				.clickBack()
				.clickCancel()
				.selectTreeNode(COMPONENT_TREE_NODE);
		
		// assert that rule names are correct
		assertFalse("Unexpected rule name " + RULE_NAME_1 + " found - it is expected to be renamed",
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_1));
		assertTrue("Cannot find rule name " + RULE_NAME_2 + " in table", 
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_2));	
		assertTrue("Cannot find rule name " + RULE_NAME_1_RENAMED + " in table", 
				productRulesConfigurationPage.isRuleNameExists(RULE_NAME_1_RENAMED));
		
		// deploy and activate
		ProductConsolidatedViewPage productConsolidatedViewPage = productRulesConfigurationPage
				.clickNext()
				.deployProduct()
				.activateProduct();
		
		// assert that deployed and activated successfully
		assertTrue("Product was NOT activated successfully after all performed test case steps",
				productConsolidatedViewPage.isActivated());
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
