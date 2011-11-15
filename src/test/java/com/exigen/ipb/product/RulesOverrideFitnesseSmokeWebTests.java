package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductAddEditRulePage;
import com.exigen.ipb.product.pages.ProductAddEditRulePage.RuleFields;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductImportInfo;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * Selenium2 PF smoke test which checks rules override functionality
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.SmokeSuite.ProductSmoke.RuleOverride
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class RulesOverrideFitnesseSmokeWebTests extends AbstractProductSeleniumTests {	

	private static final String ATTRIBUTE_NAME_LABEL                = "Test1";
	private static final String TREE_NODE_RISKITEM                  = "RiskItem (RiskItem)";	
	private static final String RULE_NAME                           = "Override1";
	private static final String RULE_ACTION                         = "Data Gather";
	private static final String RULE_ASSERTION_EXPRESSION           = "RiskItem.Test1 == 'A'";
	private static final String RULE_ERROR_MESAGE                   = "Error for rule Override1";
	
	private ProductConsolidatedViewPage productConsolidatedViewPage;
	
	private static final ProductImportInfo productImportInfo = new ProductImportInfo("ruleOverrideSaving");
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(productImportInfo);
		productConsolidatedViewPage = navigateToProductView(productImportInfo.getProductCd(), productImportInfo.getProductVersion());
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Create new product</li>
	 * 	<li>Define 'Data Gather' action</li>
	 * 	<li>Connect 'Policy v2.0' and 'Risk Item v1.0' components</li>
	 * 	<li>For 'Risk Item' component create new 'Test1' attribute</li>
	 * 	<li>Select 'Displayble' check-box for attributes 'additionalIntExistsInd' and 'Test1'</li>
	 *	<li>Configure workspace</li>
	 * 	<li>For 'Test1' attribute add rule with 'Assertion Expression' = RiskItem.Test1 == 'A'</li>
	 * 	<li>Set Override option from authority level 'Level1'</li>
	 * 	<li>Deploy and activate product
	 *  	<ul><li>Check: proper product code is presented</li>
	 * 			<li>Check: product status is 'Activated'</li></ul></li>
	 * </ol>
	 */
	@Test
	public void shouldSaveRuleOverrideOptions() {
		// add rule
		productConsolidatedViewPage.configureRules()
				.expandTreeNode(TREE_NODE_RISKITEM)
				.selectTreeNode(ATTRIBUTE_NAME_LABEL)		
				.addRule()
				.setRuleName(RULE_NAME)
				.setOverride(true)
				.setAuthorityLevel(1)
				.setFieldValue(RuleFields.RuleAssertionExpressionText, RULE_ASSERTION_EXPRESSION)
				.setFieldValue(RuleFields.ErrorMsgText, RULE_ERROR_MESAGE)
				.setOverrides(ProductAddEditRulePage.OVERRIDES_DEFAULT)
				.selectAction(RULE_ACTION)
				.clickNext()
				.clickNext();

		// deploy and activate product
		productConsolidatedViewPage.deployProduct().activateProduct();
		
		assertTrue(productConsolidatedViewPage.existsProduct(productImportInfo.getProductCd()));
		assertTrue(productConsolidatedViewPage.isActivated());
		
		ProductAddEditRulePage productAddEditRulePage = productConsolidatedViewPage.configureRules()
			.expandTreeNode(TREE_NODE_RISKITEM)
			.selectTreeNode(ATTRIBUTE_NAME_LABEL)
			.editRule(RULE_NAME);
		
		assertTrue("Ovverride options should be saved", productAddEditRulePage.getOverride());		
	}

	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		deleteTestProduct(productImportInfo);
	}
}