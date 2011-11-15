package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test which checks attributes rules functionality in runtime application
 * 
 * Covers:  EisTestCases.TestSuite.ProductSuite.TestGlobalRules.GlobalRulesRuntime
 * 
 * @category FitNesse
 * @author jdaskevicius
 * @since 3.9
 * 
 */

public class GlobalRulesRuntimeFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final String PRODUCT_CD        = "ruleRuntime1888469";
	private static final double PRODUCT_VERSION   = 1.0;
	private static final String PRODUCT_DIR       = "target/test-classes/products/rulesproductRuntime.zip";
	private static final int CUSTOMER_NR          = 500000;
	
	private static final String TAB_RISKITEM      = "Risk Item";
	private static final String TAB_POLICY        = "Policy";	
	
	private static final String TEST1_VALUE_V1    = "simple1st";
	private static final String TEST1_VALUE_V2    = "simple2nd";
	private static final String TEST2_VALUE_V2    = "global";
	private static final String EFFECTIVE_DATE_1  = "11/26/2011";
	private static final String EFFECTIVE_DATE_2  = "09/27/2014";
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}	
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'ruleRuntime1888469' from 'target/test-classes/products/rulesproductRuntime.zip'</li>
	 *  <li>Deploy and activated imported product</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of product 'ruleRuntime1888469'</li>
	 *  <li>Open 'Risk Item' tab
	 * 	  	<ul><li>Check: tab 'Risk Item' opened successfully</li>
	 *    		<li>Check: 'Test1' field value is 'simple1st'</li>
	 *    		<li>Check: 'Test2' field value is empty</li></ul></li> 
	 *  <li>Open 'Policy' tab
	 * 	  	<ul><li>Check: tab 'Policy' opened successfully</li></ul></li>
	 *  <li>Set new date (TRANSACTION_EFFECTIVE_DATE_1) in 'transactionEffectiveDate' field</li>
	 *  <li>Open 'Risk Item' tab
	 * 	  	<ul><li>Check: tab 'Risk Item' opened successfully</li>
	 *    		<li>Check: 'Test1' field value is 'simple2nd'</li>
	 *    		<li>Check: 'Test2' field value is empty</li></ul></li>
	 *  <li>Open 'Policy' tab
	 * 	  	<ul><li>Check: tab 'Policy' opened successfully</li></ul></li>
	 *  <li>Set new date (TRANSACTION_EFFECTIVE_DATE_2) in 'transactionEffectiveDate' field</li>
	 *  <li>Open 'Risk Item' tab
	 * 	  	<ul><li>Check: tab 'Risk Item' opened successfully</li>
	 *    		<li>Check: 'Test1' field value is 'simple2nd'</li>
	 *    		<li>Check: 'Test2' field value is 'global'</li></ul></li>
	 * </ol>
	 *  
	 */	
	@Test
	public void shouldCheckGlobalRulesFunctionalityInRuntime() {
		// go to quote creation page		
		DataGatheringPage dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		// go to RiskItem tab and check default test fields values
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		GeneralRiskItemComponentPage riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		assertEquals("Test1 value is not [" + TEST1_VALUE_V1 + "]", 
				TEST1_VALUE_V1, riskItemPage.getTest1Value());		
		assertEquals("Test2 value is not EMPTY",
				"", riskItemPage.getTest2Value());
		
		// go to Policy tab and change transactionEffectiveDate
		assertTrue(TAB_POLICY + " tab cannot be found", dataGatherPage.existsTab(1, TAB_POLICY));
		dataGatherPage.openTab(1, TAB_POLICY);
		GeneralPolicyComponentPage policyPage = create(GeneralPolicyComponentPage.class);
		policyPage.enterTransactionEffectiveDate(EFFECTIVE_DATE_1);
		
		// go to RiskItem tab and check default test fields values
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		assertEquals("Test1 value is not [" + TEST1_VALUE_V2 + "]", 
				TEST1_VALUE_V2, riskItemPage.getTest1Value());
		assertEquals("Test2 value is not EMPTY", 
				"", riskItemPage.getTest2Value());
		
		// go to Policy tab and change transactionEffectiveDate
		assertTrue(TAB_POLICY + " tab cannot be found", dataGatherPage.existsTab(1, TAB_POLICY));
		dataGatherPage.openTab(1, TAB_POLICY);
		policyPage.enterTransactionEffectiveDate(EFFECTIVE_DATE_2);
		
		// go to RiskItem tab and check default test fields values
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		assertEquals("Test1 value is not [" + TEST1_VALUE_V2 + "]", 
				TEST1_VALUE_V2, riskItemPage.getTest1Value());
		assertEquals("Test2 value is not [" + TEST2_VALUE_V2 + "]", 
				TEST2_VALUE_V2, riskItemPage.getTest2Value());
		
		dataGatherPage.clickCancel();
	}	
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
		
	@Override
	public Application setUpApplication() {
		return new MainApplication(getDriver(), getConfiguration());
	}
}
