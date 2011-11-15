package com.exigen.ipb.product.runtime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that is used to verify if add/remove buttons are displayed correctly in runtime
 * 
 * @author mulevicius
 * @since 3.9
 */
public class ProductAddRemoveButtonsVisibilityRuntimeWebTests extends AbstractProductSeleniumTests {
	private final static String PRODUCT_DIR = "target/test-classes/products/addRemoveButtonVisibility.zip";
	private final static String PRODUCT_CD = "addRemoveButtonVis";
	private final static Double PRODUCT_VERSION = 1.0;
	private final static int CUSTOMER_NR = 500000;
	
	private DataGatheringPage dataGatherPage;
	private GeneralRiskItemComponentPage riskItemPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}
	
	/**
	 * Tests if add/remove buttons are displayed correctly in runtime
	 */
	@Test
	public void testRuntimeView() {
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		assertTrue("Other tab cannot be found", dataGatherPage.existsTab(1, "Other"));	
		dataGatherPage.openTab(1, "Other");
		assertTrue("Risk Item tab cannot be found", dataGatherPage.existsTab(2, "Risk Item"));
		dataGatherPage.openTab(2, "Risk Item");
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		assertTrue("Add button is not presented", riskItemPage.existsAddRiskItemButton());
		assertFalse("Remove button is presented", riskItemPage.existsRemoveRiskItemButton());
		
		riskItemPage.addRiskItem();
		
		assertTrue("Add button is not presented", riskItemPage.existsAddRiskItemButton());
		assertTrue("Remove button is not presented", riskItemPage.existsRemoveRiskItemButton());
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
