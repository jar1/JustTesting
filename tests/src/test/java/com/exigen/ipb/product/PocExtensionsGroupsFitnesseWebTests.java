package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductAddEditRulePage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.PocTests.PocExtensions
 *         EisTestCases.TestSuite.ProductSuite.PocTests.PocGroups
 *
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class PocExtensionsGroupsFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                     = "poctest";
	private final static double PRODUCT_VERSION                = 1.0D;
	private final static String PRODUCT_DIR                    = "target/test-classes/products/poctest.zip";	
	
	private static final String RULES_TREE_NODE_STATESPECIFICRISKITEM  = "StateSpecificRiskItem (StateSpecificRiskItem)";
	
	private static final String RULES_ITEMNAME_TREENODE        = "itemName";
	private static final String RULES_ITEMNAME_NAME            = "extension";
	private static final String RULES_ITEMNAME_NAME_GROUP      = "movetogroup";
	private static final String RULES_ITEMNAME_COPY_NAME       = "extensionCopy";
	private static final String RULES_ITEMNAME_NEW_NAME_GROUP  = "group2";
	
	private static final String RULES_ITEMNAME_ASSERTION_ASD   = "StateSpecificRiskItem.itemName==\"asd\"";
	private static final String RULES_ITEMNAME_ASSERTION_AZ    = "StateSpecificRiskItem.itemName==\"az\"";
	private static final String RULES_ITEMNAME_ASSERTION_CA    = "StateSpecificRiskItem.itemName==\"ca\"";
	private static final String RULES_ITEMNAME_ACTION          = "Data Gather";
	
	private static final String EXTENSIONS_AZ                  = "AZ";
	private static final String EXTENSIONS_CA                  = "CA";
	private static final String EXTENSIONS_CO                  = "CO";
	
	private ProductRulesConfigurationPage productRulesConfigurationPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productRulesConfigurationPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).configureRules();
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'poctest' from 'target/test-classes/products/poctest.zip'</li>
	 * 	<li>Change product name, code and dates</li>
	 * 	<li>Go to rules configuration page</li>
	 * 	<li>Expand StateSpecificRiskItem component</li>
	 * 
	 * 	<li>Add rule (assertion) to itemName
	 *  	<ul><li>Check: rule added</li></ul></li>
	 * 	<li>Add rule group AZ extension</li>
	 * 	<li>Add rule group CA extension</li>
	 * 
	 * 	<li>Copy rule
	 *  	<ul><li>Check: rule copied</li></ul></li>
	 * 	<li>Click Edit for newly copied rule
	 *  	<ul><li>Check: both rule extensions exists</li>
	 *  		<li>Check: assertion expression is proper</li></ul></li>
	 * 	<li>Click Edit for AZ extension
	 *  	<ul><li>Check: assertion expression of AZ is proper</li></ul></li>
	 * 	<li>Go back to base rule</li>
	 * 	<li>Click Edit for CA extension
	 *  	<ul><li>Check: assertion expression of CA is proper</li></ul></li>
	 * 	<li>Save configuration</li>
	 * 
	 * 	<li>Click Edit for newly copied rule
	 *  	<ul><li>Check: both rule extensions exists</li></ul></li>
	 * 	<li>Delete CA expression</li>
	 * 	<li>Save configuration
	 * 
	 * 	<li>Click Edit for newly copied rule
	 *  	<ul><li>Check: CA extension doesn't exist</li></ul></li>
	 * 	<li>Save configuration</li>
	 * 	<li>Go to product consolidated view</li>
	 * 	<li>Deploy and activate product
	 *  	<ul><li>Check: proper product is activated</li></ul></li>
	 * </ol> 
	 */		
	@Test
	public void shouldCheckPocExtensions() {		
		// expand StateSpecificRiskItem component
		productRulesConfigurationPage.expandTreeNode(RULES_TREE_NODE_STATESPECIFICRISKITEM);

		addRuleToAttribute(RULES_ITEMNAME_NAME, RULES_ITEMNAME_ASSERTION_ASD);
		
		// check if rule added
		assertTrue(RULES_ITEMNAME_NAME + " should be added",
				productRulesConfigurationPage.isRuleNameExists(RULES_ITEMNAME_NAME));		

		addExtensionToRule(RULES_ITEMNAME_ASSERTION_AZ);
		addExtensionToRule(RULES_ITEMNAME_ASSERTION_CA);

		// copy rule
		productRulesConfigurationPage.copyRule(RULES_ITEMNAME_NAME).setRuleName(RULES_ITEMNAME_COPY_NAME).clickNext();
		
		// check if rule copied
		assertTrue(RULES_ITEMNAME_COPY_NAME + " should be presented",
				productRulesConfigurationPage.isRuleNameExists(RULES_ITEMNAME_COPY_NAME));
		
		// click edit new copied rule
		ProductAddEditRulePage productAddEditRulePage = productRulesConfigurationPage.editRule(RULES_ITEMNAME_COPY_NAME);	
		
		// check if both extensions exists and assertion expression is proper
		assertEquals("Extensions (" + EXTENSIONS_AZ + ", " + EXTENSIONS_CA + ") should be presented",
				Arrays.asList(EXTENSIONS_AZ +" X", EXTENSIONS_CA +" X"), 
				productAddEditRulePage.expandRuleExtensions().getRuleExtensions());
		assertEquals("Expected different assertion expression",
				RULES_ITEMNAME_ASSERTION_ASD, productAddEditRulePage.getAssertionExpression());
		
		// check AZ assertion expression
		assertEquals("Expected different " + EXTENSIONS_AZ + " Assertion Expression",
				RULES_ITEMNAME_ASSERTION_AZ, productAddEditRulePage.editRuleExtension(EXTENSIONS_AZ).getAssertionExpression());
		
		// go back to base rule
		productAddEditRulePage.clickNavigateToBaseRule();		
		
		assertNotNull("Rule Extensions panel is not expanded", productAddEditRulePage.editRuleExtension(EXTENSIONS_CA));
		
		// check CA assertion expression
		assertEquals("Expected different " + EXTENSIONS_CA + " Assertion Expression",
				RULES_ITEMNAME_ASSERTION_CA, productAddEditRulePage.getAssertionExpression());
		
		// safe configuration
		productAddEditRulePage.clickNext();
		
		// click edit for copied rule
		productAddEditRulePage = productRulesConfigurationPage.editRule(RULES_ITEMNAME_COPY_NAME);
		
		// check if both extensions still exists
		assertEquals("Extensions (" + EXTENSIONS_AZ + ", " + EXTENSIONS_CA + ") should be presented",
				Arrays.asList(EXTENSIONS_AZ +" X", EXTENSIONS_CA +" X"), 
				productAddEditRulePage.expandRuleExtensions().getRuleExtensions());	
		
		// delete CA extension and save
		productAddEditRulePage.deleteRuleExtension(EXTENSIONS_CA, true).clickNext();
	
		// click edit for copied rule
		productAddEditRulePage = productRulesConfigurationPage.editRule(RULES_ITEMNAME_COPY_NAME);
		
		// check if CA extension doesn't presented
		assertEquals("Only one extension (" + EXTENSIONS_AZ + ") should be presented",
				Arrays.asList(EXTENSIONS_AZ +" X"), productAddEditRulePage.expandRuleExtensions().getRuleExtensions());
		
		// save configuration
		ProductConsolidatedViewPage productConsolidatedViewPage = productAddEditRulePage.clickNext().clickNext();
		
		// deploy and activate product and check
		productConsolidatedViewPage.deployProduct().activateProduct();		
		
		assertTrue(PRODUCT_CD + "product should be activated",
				productConsolidatedViewPage.existsProduct(PRODUCT_CD) && productConsolidatedViewPage.isActivated());

	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'poctest' from 'target/test-classes/products/poctest.zip'</li>
	 * 	<li>Change product name, code and dates</li>
	 * 	<li>Go to rules configuration page</li>
	 * 	<li>Expand StateSpecificRiskItem component</li>
	 * 
	 * 	<li>Add rule (assertion) to itemName
	 *  	<ul><li>Check: rule added</li></ul></li>
	 * 	<li>Click Move to Group for newly created rule</li>
	 * 	<li>Select AZ in popup and save</li>
	 * 
	 * 	<li>Switch to 'Configure All Rule Groups' view
	 *  	<ul><li>Check: newly created rule presented with proper group</li></ul></li>
	 * 	<li>Edit rule
	 *  	<ul><li>Check: AZ is in Applied to Group</li></ul></li>
	 * 	<li>Add new CA group
	 *  	<ul><li>Check: both groups presented in Applied to Group</li></ul></li>
	 * 	<li>Save configuration
	 * 		<ul><li>Check: rule presented with proper group with both groups</li></ul></li>
	 * 	<li>Create new rule</li>
	 * 	<li>Add group CO
	 *  	<ul><li>Check: CO in Applied to Group</li></ul></li>
	 * 	<li>Save configuration
	 *  	<ul><li>Check: both rules are presented with proper groups</li></ul></li>
	 * 	<li>Save configuration</li>
	 * 	<li>Deploy and activate product
	 *  	<ul><li>Check: proper product is activated</li></ul></li>
	 * 
	 */		
	@Test
	public void shouldCheckPocGroups() {		
		// expand StateSpecificRiskItem component
		productRulesConfigurationPage.expandTreeNode(RULES_TREE_NODE_STATESPECIFICRISKITEM);

		addRuleToAttribute(RULES_ITEMNAME_NAME_GROUP, RULES_ITEMNAME_ASSERTION_ASD);
		
		// check if rule added
		assertTrue(RULES_ITEMNAME_NAME_GROUP + " should be added", 
				productRulesConfigurationPage.isRuleNameExists(RULES_ITEMNAME_NAME_GROUP));	
		
		// move rule to AZ group
		productRulesConfigurationPage.moveToGroupRule(RULES_ITEMNAME_NAME_GROUP).selectRuleGroup(1).clickSave();
		
		// switch to all groups
		productRulesConfigurationPage.clickConfigureAllRuleGroups();
		
		// check if rule expression presented
		assertTrue(RULES_ITEMNAME_NAME_GROUP + " is not presented",
				productRulesConfigurationPage.getGroupViewRuleNames().contains(RULES_ITEMNAME_NAME_GROUP));	
		
		// check group
		assertTrue(EXTENSIONS_AZ + " is not presented in " + RULES_ITEMNAME_NAME_GROUP + " group",
				productRulesConfigurationPage.getGroupViewRuleGroups(RULES_ITEMNAME_NAME_GROUP).containsAll(Arrays.asList(EXTENSIONS_AZ)));
		
		// edit rule group
		ProductAddEditRulePage productAddEditRulePage = productRulesConfigurationPage.editRuleGroupExtension(RULES_ITEMNAME_NAME_GROUP);
		
		// check if rule applies to AZ group
		assertTrue("Rule should be applied to " + EXTENSIONS_AZ + " group",
				productAddEditRulePage.checkAppliesToGroup(EXTENSIONS_AZ +" X"));
		
		// add new group for CA
		productAddEditRulePage.clickAddGroupToRule().selectRuleGroup(1).clickAddNewGroups();
		
		// check if rule applies to AZ and CA groups
		assertTrue("Rule should be applied to " + EXTENSIONS_AZ + " and " + EXTENSIONS_CA + " groups",
				productAddEditRulePage.checkAppliesToGroup(EXTENSIONS_AZ +" X"+" "+ EXTENSIONS_CA+ " X"));
		
		// save configuration
		productAddEditRulePage.clickNext();	
		
		// check if rule expression presented
		assertTrue(RULES_ITEMNAME_NAME_GROUP + " is not presented",
				productRulesConfigurationPage.getGroupViewRuleNames().contains(RULES_ITEMNAME_NAME_GROUP));	
		
		// check groups
		assertTrue(EXTENSIONS_AZ + " and " + EXTENSIONS_CA + " is not presented in " + RULES_ITEMNAME_NAME_GROUP + " group",
				productRulesConfigurationPage.getGroupViewRuleGroups(RULES_ITEMNAME_NAME_GROUP)
				.containsAll(Arrays.asList(EXTENSIONS_AZ, EXTENSIONS_CA)));
		
		// create new rule
		productRulesConfigurationPage.clickGo();
		productAddEditRulePage = create(ProductAddEditRulePage.class);
		
		// add new group CO to rule
		productAddEditRulePage.setRuleName(RULES_ITEMNAME_NEW_NAME_GROUP)
			.clickAddGroupToRule()
			.selectRuleGroup(3)
			.clickAddNewGroups();
		
		// check group
		assertTrue("Rule should be applied to " + EXTENSIONS_CA + " group",
				productAddEditRulePage.checkAppliesToGroup(EXTENSIONS_CO +" X"));
		
		// save configuration
		productRulesConfigurationPage.clickNext();
		
		// check
		assertTrue(RULES_ITEMNAME_NAME_GROUP + " is not presented",
				productRulesConfigurationPage.getGroupViewRuleNames().contains(RULES_ITEMNAME_NAME_GROUP));	
		assertTrue(EXTENSIONS_AZ + " and " + EXTENSIONS_CA + " is not presented in " + RULES_ITEMNAME_NAME_GROUP + " group",
				productRulesConfigurationPage.getGroupViewRuleGroups(RULES_ITEMNAME_NAME_GROUP).containsAll(Arrays.asList(EXTENSIONS_AZ, EXTENSIONS_CA)));
		assertTrue(RULES_ITEMNAME_NEW_NAME_GROUP + " is not presented",
				productRulesConfigurationPage.getGroupViewRuleNames().contains(RULES_ITEMNAME_NEW_NAME_GROUP));
		assertTrue(EXTENSIONS_CO + " is not presented in " + RULES_ITEMNAME_NEW_NAME_GROUP + " group",
				productRulesConfigurationPage.getGroupViewRuleGroups(RULES_ITEMNAME_NEW_NAME_GROUP).containsAll(Arrays.asList(EXTENSIONS_CO)));
		
		ProductConsolidatedViewPage productConsolidatedViewPage = productRulesConfigurationPage
			.clickNext()
			.deployProduct()
			.activateProduct();		
		
		assertTrue(PRODUCT_CD + "should be activated",
				productConsolidatedViewPage.existsProduct(PRODUCT_CD) && productConsolidatedViewPage.isActivated());
		
	}
	
	private void addExtensionToRule(String value) {
		productRulesConfigurationPage.editRule(RULES_ITEMNAME_NAME)
			.createExtensionsForGroup(1)
			.setFieldValue(ProductAddEditRulePage.RuleFields.RuleAssertionExpressionText, value)
			.setFieldValue(ProductAddEditRulePage.RuleFields.ErrorMsgText, value)
			.clickNext();
	}
	
	private void addRuleToAttribute(String rule, String value) {
		productRulesConfigurationPage.selectTreeNode(RULES_ITEMNAME_TREENODE).addRule()
			.setRuleName(rule)
			.setFieldValue(ProductAddEditRulePage.RuleFields.RuleAssertionExpressionText, value)
			.setFieldValue(ProductAddEditRulePage.RuleFields.ErrorMsgText, value)
			.selectAction(RULES_ITEMNAME_ACTION)
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
