package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;

/**
 *
 *
 * @author ggrazevicius
 * @since 3.9
 * 
 */
public class TabVisibilityRuleWebTests extends AbstractProductSeleniumTests {

	private final static String PRODUCT_DIR = "target/test-classes/products/TVP-product.zip";
	private final static String PRODUCT_CD = "TVP";
	private final static Double PRODUCT_VERSION = 1.0;
	private final static int CUSTOMER_NR = 500000;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}

	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product TVP</li>
	 *  <li>Navigate to new quote flow for customer #500000</li>
	 * 	<li>Create new quote for product 'TVP'</li>
	 * 	<li>Fill mandatory values for Policy</li>
	 * 	<li>Fill in 'LT' as country to trigger TabVisRule to hide 'Risk Item' tab</li>
	 * 	<li>Click 'next'
	 *  	<ul><li>Expected: 'Risk Item' tab is hidden, user is directed to next tab ('Rate Action')</li></ul></li>
	 * 	<li>Select policy tab</li>
	 * 	<li>Change Country value to something else to unhide 'Risk Item' tab</li>
	 * 	<li>Click 'next'
	 *  	<ul><li>Expected: User is directed to 'Risk Item' tab</li></ul></li>
	 * 	<li>Fill itemName -> '123'</li>
	 * 	<li>Click 'add' to add new riskItem</li>
	 * 	<li>Fill itemName -> 'asd' (makes Coverage tab visible)</li>
	 * 	<li>Click 'next'
	 * 		<ul><li>Expected: user is directed to 'Coverage' tab</li></ul></li>
	 * 	<li>Change context to first 'Risk Item'
	 *  	<ul><li>Expected: 'Coverage' is hidden, user is directed to 'Rate Action' tab</li></ul></li>
	 * </ol>
	 */
	@Test
	public void shouldTabVisibilityRules() throws InterruptedException {		
		DataGatheringPage dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());

		dataGatherPage.openTab(1, "Policy");		
		GeneralPolicyComponentPage policyPage = create(GeneralPolicyComponentPage.class);
		
		policyPage.fillDefaultMandatoryValuesForPolicy();	
		policyPage.enterCountry("LT");	
		policyPage.clickNext();
		
		assertEquals("Current tab must be 'Rate Action'", "Rate Action", policyPage.getSelectedTab(1));
		
		// Go back to Policy and change Country value so RiskItem tab reappears
		dataGatherPage.openTab(1, "Policy");

		policyPage.enterCountry("U");
		dataGatherPage.clickNext();
		
		assertEquals("Current tab must be 'Risk Item'", "Risk Item", dataGatherPage.getSelectedTab(2));
		
		GeneralRiskItemComponentPage riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		riskItemPage.setItemName("123");
		riskItemPage.addRiskItem();
		riskItemPage.setItemName("asd");
		riskItemPage.setAdditionalInterestExists(true);
		
		dataGatherPage.clickNext();
				
		assertEquals("Current tab must be 'Coverage'", "Coverage", dataGatherPage.getSelectedTab(2));
		
		// Change context
		clickHidden("#policyDataGatherForm\\\\:items0_0");
		assertEquals("Current tab must be 'Rate Action'", "Rate Action", policyPage.getSelectedTab(1));
	}
	
	private void clickHidden(String selector) {
		JavascriptExecutor jse = (JavascriptExecutor) getDriver();
		
		jse.executeScript("jQuery('" + selector + "').click()");
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
