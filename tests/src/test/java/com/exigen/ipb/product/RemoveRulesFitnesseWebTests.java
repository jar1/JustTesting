package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductAddEditRulePage;
import com.exigen.ipb.product.pages.ProductAddEditRulePage.RuleFields;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.product.pages.dialogs.AttributeRemovalConfirmationPopUp;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.RemoveRules
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class RemoveRulesFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                        = "WorkspaceOrder";
	private final static double PRODUCT_VERSION                   = 1.0D;
	private final static String PRODUCT_DIR                       = "target/test-classes/products/workspaceorder.zip";

	private static final String COMPONENT_RISKITEM                = "RiskItem";

	private static final String ATTRIBUTE_DWELL1                  = "primaryResidence";
	private static final String ATTRIBUTE_DWELL2                  = "seqNo";
	private static final String ATTRIBUTE_DWELL3                  = "viewResidenceInd";
	private static final String ATTRIBUTE_RISKITEM1               = "territoryCd";
	private static final String TREE_NODE_RISKITEM                = "RiskItem (RiskItem)";
	private static final String TREE_NODE_DWELL                   = "Dwell (Dwell)";
	
	private static final String DWELL1_RULE_NAME                  = "condition";
	private static final String DWELL1_RULE_CONDITION_EXPRESSION  = "RiskItem.additionalIntExistsInd==\"asd\"";
	
	private static final String DWELL2_RULE_NAME                  = "expression";
	private static final String DWELL2_RULE_ASSERTION_EXPRESSION  = "RiskItem.seqNo==\"asd\"";
	private static final String DWELL2_RULE_ERROR                 = "error";
	
	private static final String DWELL3_RULE_NAME                  = "default";
	private static final String DWELL3_RULE_DEFAULT_EXPRESSION    = "RiskItem.itemName";
	
	private static final String RISKITEM1_RULE_NAME               = "simple";
	
	private ProductRulesConfigurationPage productRulesPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		// import product and configure rules
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productRulesPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.configureRules();	
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'WorkspaceOrder' from 'target/test-classes/products/workspaceorder.zip'</li>
	 * 	<li>Change product name, code and dates</li>
	 * 	<li>Go to rules configuration page</li>
	 * 	<li>Expand Dwell tree node</li>
	 * 	<li>For 'primaryResidence' attribute create rule 'condition' depend on RiskItem.additionalIntExistsInd</li>
	 * 	<li>For 'seqNo' attribute create rule 'expression' depend on RiskItem.seqNo</li>
	 * 	<li>For 'viewResidenceInd' attribute create rule 'default' depend on RiskItem.itemNam</li>
	 * 	<li>Expand RiskItem tree node</li>
	 * 	<li>For 'seqNo' attribute create rule 'simple'</li>
	 * 	<li>Click Dwell tree node
	 *  	<ul><li>Check: proper rules are presented</li></ul></li>
	 * 	<li>Click RiskItem tree node
	 *  	<ul><li>Check: proper rule is presented</li></ul></li>
	 * 	<li>Save configuration</li>
	 * 	<li>Go to data update page</li>
	 * 	<li>Click Configure to RiskItem component</li>
	 * 	<li>Select all RiskItem attributes and click Remove
	 *  	<ul><li>Check: all 4 rules presented in confirmation popup</li></ul></li>
	 * 	<li>Click Yes</li>
	 * 	<li>Save attribute removal configuration and whole component configuration</li>
	 * 	<li>Go to rules configuration page
	 *  	<ul><li>Check: no rules presented</li></ul></li>
	 * 	<li>Click Cancel</li>
	 * 	<li>Deploy product
	 *  	<ul><li>Check: product code is not get changed</li></ul></li>
	 * </ol>  
	 */
	@Test
	public void shouldCheckRemoveRulesFunctionality() {		
		
		// add Rule for 'primaryResidence' attribute in Dwell
		addRule(TREE_NODE_DWELL, ATTRIBUTE_DWELL1, DWELL1_RULE_NAME,
				RuleFields.ConditionExpressionText, DWELL1_RULE_CONDITION_EXPRESSION);

		// add Rule for 'seqNo' attribute in Dwell
		addRule(TREE_NODE_DWELL, ATTRIBUTE_DWELL2,  
				new RuleFields[] {RuleFields.RuleAssertionExpressionText, RuleFields.ErrorMsgText}, 
				new String[] 	 {DWELL2_RULE_ASSERTION_EXPRESSION, 	  DWELL2_RULE_ERROR},
				DWELL2_RULE_NAME);
		
		// add Rule for 'viewResidenceInd' attribute in Dwell
		addRule(TREE_NODE_DWELL, ATTRIBUTE_DWELL3, DWELL3_RULE_NAME,
				RuleFields.DefExpressionText, DWELL3_RULE_DEFAULT_EXPRESSION);
		
		// add Rule for 'territoryCd' attribute in RiskItem
		addRule(TREE_NODE_RISKITEM, ATTRIBUTE_RISKITEM1, RISKITEM1_RULE_NAME, null, null);
		
		// check if Dwell rules created
		productRulesPage.selectTreeNode(TREE_NODE_DWELL);		
		assertTrue(DWELL1_RULE_NAME + ", " + DWELL2_RULE_NAME + ", " + DWELL3_RULE_NAME + " rules should be presented",
				productRulesPage.isRuleNameExists(DWELL1_RULE_NAME)
				&& productRulesPage.isRuleNameExists(DWELL2_RULE_NAME)
				&& productRulesPage.isRuleNameExists(DWELL3_RULE_NAME));

		// check if RiskItem rules created
		productRulesPage.selectTreeNode(TREE_NODE_RISKITEM);
		assertTrue(RISKITEM1_RULE_NAME + " rule should be presented",
				productRulesPage.isRuleNameExists(RISKITEM1_RULE_NAME));
		
		// save configuration and remove attributes
		AttributeRemovalConfirmationPopUp attributeRemovalConfirmationPopUp = productRulesPage
				.clickNext()
				.updateProductData()
				.configureComponent(COMPONENT_RISKITEM)
				.selectAttributesAll()
				.clickRemove();

		// check if all rules presented
		assertTrue("All rules should be presented", 
				attributeRemovalConfirmationPopUp.getDependRules()
				.containsAll(Arrays.asList(DWELL1_RULE_NAME, DWELL2_RULE_NAME, DWELL3_RULE_NAME, RISKITEM1_RULE_NAME)));
		
		// click Yes and save
		attributeRemovalConfirmationPopUp.clickYes().clickSave().clickSave().configureRules();
		
		// check if no rules presented
		assertFalse("All rules should be deleted",
				productRulesPage.isRuleNameExists(DWELL1_RULE_NAME)
				&& productRulesPage.isRuleNameExists(DWELL2_RULE_NAME)
				&& productRulesPage.isRuleNameExists(DWELL3_RULE_NAME)
				&& productRulesPage.isRuleNameExists(RISKITEM1_RULE_NAME));
		
		// click cancel and deploy product
		ProductConsolidatedViewPage productConsolidatedViewPage = productRulesPage.clickCancel().deployProduct().activateProduct();	
		
		// check product code
		assertTrue(PRODUCT_CD + " should be activated",
				productConsolidatedViewPage.existsProduct(PRODUCT_CD) && productConsolidatedViewPage.isActivated());
	}

	/**
	 * Adds rule for attribute with provided field values. Automatically expands required nodes.
	 * @param componentNode
	 * @param attributeNode
	 * @param ruleName
	 * @param ruleField
	 * @param fieldValue
	 */
	private void addRule(String componentNode, String attributeNode, RuleFields[] ruleField, String[] fieldValue, String ruleName) {
		productRulesPage.expandTreeNode(componentNode);
		ProductAddEditRulePage productAddEditRulePage = productRulesPage
				.selectTreeNode(attributeNode)		
				.addRule()
				.setRuleName(ruleName);
		
		if(ruleField != null && fieldValue != null) {
			for(int i = 0; i < ruleField.length; i++) {
				if(ruleField[i] != null && fieldValue != null) {
					productAddEditRulePage.setFieldValue(ruleField[i], fieldValue[i]);
				}
			}
		}

		productRulesPage.clickNext();
	}
	
	private void addRule(String componentNode, String attributeNode, String ruleName, RuleFields ruleField, String fieldValue) {
		addRule(componentNode, attributeNode, new RuleFields[] {ruleField}, new String[] {fieldValue}, ruleName);
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
