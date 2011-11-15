package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductConsolidatedViewActions;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

import java.util.Arrays;

/**
 * Selenium2 PF smoke test which checks product overview ui conformance
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.ProductOverviewUiConf
 *   
 * @author mulevicius
 * 
 * @since 3.9
 */

public class ProductOverviewUiConfFitnesseWebTests extends AbstractProductSeleniumTests {	
	private final static String PRODUCT_CD                     = "seleniumProduct";
	private final static double PRODUCT_VERSION                = 1.0D;
	private final static String PRODUCT_DIR                    = "target/test-classes/products/withPolicyRootAndWorkspace.zip";
	private final static String PRODUCT_CD_V                   = PRODUCT_CD + " v1.0";
	
	private ProductConsolidatedViewPage productConsolidatedViewPage;

	@Override
	protected void afterCustomSettingsSetUp() {				
		// import product
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		// navigate to imported products consolidated view
		productConsolidatedViewPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	/**
	 * Main test method that checks if correct UI and values are displayed for product overview
	 */
	@Test
	public void productOverviewUIShouldBeAsExpected() {
		// check header
		assertEquals("Wrong product code in header", PRODUCT_CD_V, productConsolidatedViewPage.getProductCode());
		assertEquals("Wrong product name in header", PRODUCT_CD, productConsolidatedViewPage.getProductName());
		assertTrue("Wrong activated value in header", productConsolidatedViewPage.isActivated());
		assertTrue("Link to status history is not visible in header", productConsolidatedViewPage.linkStatusHistoryVisible());
		
		// check available actions
		List<String> availableActions = productConsolidatedViewPage.getActionsDropDownAsText();
		List<ProductConsolidatedViewActions> expectedActions = Arrays.asList(ProductConsolidatedViewActions.values());
		
		assertSelect(availableActions, expectedActions, "Expected actions in consolidated view drop down");
		
		// check table in version panel
		productConsolidatedViewPage.clickProductVersionsToggleControl();
		assertTrue("Product does not exist in version panel table", 
				productConsolidatedViewPage.productVersionExists(PRODUCT_VERSION));
		assertTrue("Wrong product activation status value in version panel table", 
				productConsolidatedViewPage.productVersionIsActive(PRODUCT_VERSION));
		assertTrue("Wrong product visibility status value in version panel table", 
				productConsolidatedViewPage.productVersionIsDeployed(PRODUCT_VERSION));
		
		// check product properties table
		assertEquals("Wrong product code in product properties table", 
				PRODUCT_CD, productConsolidatedViewPage.getPropertyProductCode(0));
		assertEquals("Wrong product name in product properties table", 
				PRODUCT_CD, productConsolidatedViewPage.getPropertyProductName(0));
		assertEquals("Wrong transaction date in product properties table", 
				"08/18/2011", productConsolidatedViewPage.getPropertyTransactionDate(0));
		assertEquals("Wrong effective date in product properties table", 
				"08/18/2011", productConsolidatedViewPage.getPropertyEffectiveDate(0));
		assertEquals("Wrong broad LOB in product properties table", 
				"Pleasure and Business", productConsolidatedViewPage.getPropertyBroadLOB(0));
		assertEquals("Wrong LOB in product properties table", 
				"Agriculture Applicant Information (AL1 only)", productConsolidatedViewPage.getPropertyLOB(0));
		assertEquals("Wrong allowed renewal types in product properties table", 
				"Automatic", productConsolidatedViewPage.getPropertyRenewalTypesAllowed(0));

		productConsolidatedViewPage.changeProductProperties().clickNext().deactivateProduct();
	}
	
	/**
	 * Verifies that every expectedAction is in availableActions list and that list sizes are equal
	 * @param availableActions
	 * @param expectedActions
	 * @param dropDown
	 */
	private void assertSelect(List<String> availableActions, List<ProductConsolidatedViewActions> expectedActions, String dropDown) {
		for(ProductConsolidatedViewActions expected : expectedActions) {
			assertTrue("Action " + expected.toString() + " is missing in " + dropDown, 
					availableActions.contains(expected.toString()));
		}
		assertEquals("Additional unexpected actions were present in " + dropDown, 
				expectedActions.size(), availableActions.size());
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