package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralStateSpecificRiskItemComponentPage;
import com.exigen.ipb.policy.pages.QuoteConsolidatedViewPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.StateCd;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;

/**
 * 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.PocTests.PocRuntime
 *
 * @category FitNesse
 * @author jdaskevicius * 
 * @since 3.9
 */

public class PocRuntimeFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private static final String PRODUCT_CD              = "pocruntime";
	private static final double PRODUCT_VERSION         = 1.0;
	private static final String PRODUCT_DIR             = "target/test-classes/products/pocruntime.zip";	
	private static final int CUSTOMER_NR                = 500000;
	
	private static final String TAB_RISKITEM            = "Risk Item";

	private static final String FIELD_TERRITORYCD       = "territoryCd";
	private static final String FIELD_ITEMNAME          = "itemName";
	private static final String FIELD_TEST1             = "additionalFields_test1_stringValue";
	private static final String FIELD_STATEPROVINCECD   = "location_address_stateProvCd";
	
	private static final String[] DATA_ITEMS = new String[] {"itemName", "test1"};
	private static final String[] DATA_PRODUCTS = new String[] {"pocruntime4220144", "pocruntime4220360"};
	private static final StateCd[] DATA_STATES = new StateCd[] {StateCd.ASD, StateCd.CO};
	
	private DataGatheringPage dataGatherPage;
	private GeneralStateSpecificRiskItemComponentPage stateSpecificRiskItemPage;
	private QuoteConsolidatedViewPage quotePage;

	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}	

	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'pocruntime' from 'target/test-classes/products/pocruntime.zip'</li>
	 *  <li>Deploy and activate</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of new product</li>
	 *  <li>Open Risk Item tab</li>
	 *  <li>Fill in fields</li>
	 *  
	 *  <li>Select CT state and click Next
	 *  	<ul><li>Check: error message appears</li></ul></li>
	 *  <li>Select AZ state and click Next
	 *  	<ul><li>Check: error message appears</li></ul></li>
	 *  <li>Select CA state and click Next
	 *  	<ul><li>Check: error message appears</li></ul></li>
	 *  <li>Select CO state and click Next
	 *  	<ul><li>Check: two error messages appear</li></ul></li>
	 *  
	 *  <li>Change test1 field value to "co"</li>
	 * 	<li>Click Next
	 *		 <ul><li>Check: only one error message appears</li></ul></li>
	 * 	<li>Change itemName field value to "asd"</li>
	 * 	<li>Click Next
	 *		 <ul><li>Check: no error messages appear</li></ul></li>
	 * 
	 * 	<li>Go back to Risk Item tab</li>
	 * 	<li>Select CA state and click Next
	 *		 <ul><li>Check: error message appears</li></ul>
	 * 	<li>Change itemName field value to "ca"</li>
	 * 	<li>Click Next
	 *		 <ul><li>Check: no error messages appear</li></ul></li>
	 *  <li>Click top 'Save&Exit' button
	 *		 <ul><li>Check: quote consolidated view opened</li></ul></li>
	 * </ol>
	 *  
	 */		
	@Test
	public void shouldCheckPocInRuntime() {		
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());

		// go to RiskItem tab
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		stateSpecificRiskItemPage = create(GeneralStateSpecificRiskItemComponentPage.class);
		
		// fill data
		stateSpecificRiskItemPage.setField(FIELD_TERRITORYCD, "aaa")
			.setField(FIELD_TEST1, "aaa")		
			.setField(FIELD_ITEMNAME, "aaa")
			.setAdditionalInterestExists(true)
			.setAdditionalInterestExists(false);		
		
		// select CT in State/Province dropdown and check for error
		stateSpecificRiskItemPage.selectInField(FIELD_STATEPROVINCECD, StateCd.CT.name()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS[0], DATA_PRODUCTS[0], StateCd.ASD);
		
		// select AZ in State/Province dropdown and check for error
		stateSpecificRiskItemPage.selectInField(FIELD_STATEPROVINCECD, StateCd.AZ.name()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS[0],  DATA_PRODUCTS[0], StateCd.AZ);
		
		// select CA in State/Province dropdown and check for error
		stateSpecificRiskItemPage.selectInField(FIELD_STATEPROVINCECD, StateCd.CA.name()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS[0],  DATA_PRODUCTS[0], StateCd.CA);
		
		// select CO in State/Province dropdown and check for error
		stateSpecificRiskItemPage.selectInField(FIELD_STATEPROVINCECD, StateCd.CO.name()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS, DATA_PRODUCTS, DATA_STATES);
		
		// change test1 value to co and check for error
		stateSpecificRiskItemPage.clearField(FIELD_TEST1);
		stateSpecificRiskItemPage.setField(FIELD_TEST1, StateCd.CO.name().toLowerCase()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS[0],  DATA_PRODUCTS[0], StateCd.ASD);

		// change itemName value to asd and check for error
		stateSpecificRiskItemPage.clearField(FIELD_ITEMNAME);
		stateSpecificRiskItemPage.setField(FIELD_ITEMNAME, StateCd.ASD.name().toLowerCase()).next();		
		assertTrue("No error messages expected", stateSpecificRiskItemPage.getErrorList().isEmpty());
		
		// go back to RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);
		
		// select CA in State/Province dropdown and check for error
		stateSpecificRiskItemPage.selectInField(FIELD_STATEPROVINCECD, StateCd.CA.name()).next();
		assertErrorMessageDisplayedFor(DATA_ITEMS[0],  DATA_PRODUCTS[0], StateCd.CA);
		
		// change itemName value to ca and check for no error
		stateSpecificRiskItemPage.clearField(FIELD_ITEMNAME);
		stateSpecificRiskItemPage.setField(FIELD_ITEMNAME, StateCd.CA.name().toLowerCase()).next();		
		assertTrue("No error messages expected", stateSpecificRiskItemPage.getErrorList().isEmpty());
		
		// save and go to quote consolidated view
		stateSpecificRiskItemPage.saveAndExit();
		quotePage = create(QuoteConsolidatedViewPage.class);
		
		// check status
		assertEquals("Quote consolidated view with Data gathering status expected",
				"Data Gathering", quotePage.getStatus());
	}
	
	private void assertErrorMessageDisplayedFor(String name, String productCd, StateCd item) {
		assertErrorMessageDisplayedFor(new String[] {name}, new String[] {productCd}, new StateCd[] {item});
	}
	
	private void assertErrorMessageDisplayedFor(String[] names, String[] productCd, StateCd[] items) {
		assertEquals("Error messages are wrong/missing", 
				createErrorSetFor(names, productCd, items), 
				stateSpecificRiskItemPage.getErrorSet());
	}

	private Set<String> createErrorSetFor(String[] names, String[] productCd, StateCd[] items) {
		Set<String> errorSet = new HashSet<String>();
		for(int i = 0; i < items.length; i++) {
			errorSet.add(createErrorMsg(names[i], productCd[i], items[i].name().toLowerCase()));
		}
		return errorSet;
	}
	
	private String createErrorMsg(String item, String productCd, String value) {
		return String.format("StateSpecificRiskItem.%1$s==\"%2$s\" (%3$s) [for StateSpecificRiskItem.%1$s]", item, value, productCd);
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
