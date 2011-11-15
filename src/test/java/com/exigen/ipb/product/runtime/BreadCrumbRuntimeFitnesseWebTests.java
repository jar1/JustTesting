package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.components.domain.OptionalQuestionAnswer;
import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralCoverageComponentPage;
import com.exigen.ipb.policy.pages.GeneralDwellComponentPage;
import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * 
 * 
 * Covers:  EisTestCases.TestSuite.ProductSuite.BreadCrumb
 *  
 * @category FitNesse
 * @author jdaskevicius
 * @since 3.9 * 
 * 
 */

public class BreadCrumbRuntimeFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final String PRODUCT_CD               = "breadcrumb";
	private static final double PRODUCT_VERSION          = 1.0;
	private static final String PRODUCT_DIR              = "target/test-classes/products/breadcrumb.zip";
	private static final int CUSTOMER_NR                 = 500000;	
	
	private static final String TAB_RISKITEM             = "Risk Item";
	private static final String TAB_DWEELL               = "dwell";
	private static final String TAB_COVERAGE             = "coverage";

	private static final String RISKITEM_TERRITORYCD     = "territoryCd";
	private static final String RISKITEM_TEST            = "Test";	
	private static final String DWELL_PRIMARYRESIDENCE   = "primaryResidence";
	private static final String DWELL_TEST               = "test";	
	private static final String COVERAGE_LIMITAMOUNT     = "limitAmount";
	private static final String COVERAGE_COVERLEVELCD    = "coverLevelCd";		

	private DataGatheringPage dataGatherPage;
	private GeneralRiskItemComponentPage riskItemPage;
	private GeneralDwellComponentPage dwellPage;
	private GeneralCoverageComponentPage coveragePage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'breadcrumb' from 'target/test-classes/products/breadcrumb.zip'</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of new product</li>
	 * 
	 *  <li>Open 'Risk Item' tab
	 *   	<ul><li>Check: Add button is      presented</li>
	 *   		<li>Check: fields     are not presented</li>
	 *   		<li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Open 'Dwell' tab
	 *   	<ul><li>Check: question   is  not presented</li>
	 *    		<li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Open 'Coverage' tab
	 *    	<ul><li>Check: fields     are not presented</li>
	 *   		<li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  
	 *  <li>Open 'Risk Item' tab</li>
	 *  <li>Add new RiskItem instance
	 *    	<ul><li>Check: fields     are     presented</li>
	 *    		<li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Open 'Dwell' tab
	 *    	<ul><li>Check: question   is      presented</li>
	 *   	 	<li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Set 'Yes' in question
	 *    	<ul><li>Check: BreadCrumb is      presented</li></ul></li>
	 *  <li>Set 'No' in question
	 *    	<ul><li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Set 'Yes' in question
	 *    	<ul><li>Check: fields     are     presented</li></ul></li>
	 *  <li>Add new 'Dwell' instance
	 *    	<ul><li>Check: instance list contains two rows</li>
	 *    		<li>Check: BreadCrumb is      presented</li></ul></li>
	 *  
	 *  <li>Open 'Risk Item' tab
	 *    	<ul><li>Check: BreadCrumb is  not presented</li></ul></li>
	 *  <li>Input "coverage" in test1 field</li>
	 *  <li>Open 'Coverage' tab
	 *    	<ul><li>Check: fields     are     presented</li>
	 *    		<li>Check: BreadCrumb is      presented</li></ul></li>
	 *   
	 *  <li>Open 'Risk Item' tab</li>
	 *  <li>Add new RiskItem instance
	 *    	<ul><li>Check: instance list contains two rows</li>
	 *   	 	<li>Check: BreadCrumb is      presented</li></ul></li>
	 *   <li>Open 'Dwell' tab
	 *    	<ul><li>Check: question   is      presented</li>
	 *    		<li>Check: BreadCrumb is      presented</li></ul></li>
	 *   <li>Open 'Coverage' tab
	 *    	<ul><li>Check: fields     are not presented</li>
	 *    		<li>Check: BreadCrumb is      presented</li></ul></li>
	 * </ol>
	 */	
	@Test
	public void shouldTestBreadCrumbRuntime() {
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());

		// go to RiskItem tab
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		// check Add button and no fields, also no BreadCrumb
		assertTrue("Add button is not presented", riskItemPage.existsAddRiskItemButton());
		assertFalse(RISKITEM_TERRITORYCD + " field is presented",
				riskItemPage.existsField(RISKITEM_TERRITORYCD, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertFalse(RISKITEM_TEST + "1" + " field is presented",
				riskItemPage.existsField(RISKITEM_TEST + "1" + "_stringValue", GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		assertFalse(RISKITEM_TEST + "7" + " field is presented",
				riskItemPage.existsField(RISKITEM_TEST + "7" + "_stringValue",	GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
		
		// go to Dwell tab
		assertTrue(TAB_DWEELL + " tab cannot be found", dataGatherPage.existsTab(1, TAB_DWEELL));
		dataGatherPage.openTab(1, TAB_DWEELL);
		dwellPage = create(GeneralDwellComponentPage.class);
		
		// check no question, also no BreadCrumb
		assertFalse("Optional Question label is visible", dwellPage.isOptionalQuestionLabelVisible());
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
		
		// go to Coverage tab
		assertTrue(TAB_COVERAGE + " tab cannot be found", dataGatherPage.existsTab(1, TAB_COVERAGE));
		dataGatherPage.openTab(1, TAB_COVERAGE);
		coveragePage = create(GeneralCoverageComponentPage.class);
		
		// check no fields, also no BreadCrumb
		assertFalse(COVERAGE_LIMITAMOUNT + " field is presented",
				coveragePage.existsField(COVERAGE_LIMITAMOUNT, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertFalse(COVERAGE_COVERLEVELCD + " field is presented",
				coveragePage.existsField(COVERAGE_COVERLEVELCD, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
				
		// go back to RiskItem tab and add new RiskItem
		dataGatherPage.openTab(1, TAB_RISKITEM);
				
		// add RiskItem instance
		riskItemPage.addRiskItem();
		
		// check fields, also no BreadCrumb
		assertTrue(RISKITEM_TERRITORYCD + " field is not presented",
				riskItemPage.existsField(RISKITEM_TERRITORYCD, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));	
		assertTrue(RISKITEM_TEST + "1" + " field is not presented",
				riskItemPage.existsField(RISKITEM_TEST + "1" + "_stringValue", GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		assertTrue(RISKITEM_TEST + "7" + " field is not presented",
				riskItemPage.existsField(RISKITEM_TEST + "7" + "_stringValue", GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());

		// go to Dwell tab and check 
		dataGatherPage.openTab(1, TAB_DWEELL);
				
		// check question, also no BreadCrumb
		assertTrue("Optional Question label is not visible", dwellPage.isOptionalQuestionLabelVisible());
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
	
		// set yes in question and check BreadCrumb
		dwellPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.Yes);
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());		
		
		// set no in question and check no BreadCrumb
		dwellPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.No);
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
	
		// set yes in question
		dwellPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.Yes);
		
		// check fields
		assertTrue(DWELL_PRIMARYRESIDENCE + " field is not presented",
				dwellPage.existsField(DWELL_PRIMARYRESIDENCE, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertTrue(DWELL_TEST + "1" + " field is not presented",
				dwellPage.existsField(DWELL_TEST + "1" + "_stringValue", GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		assertTrue(DWELL_TEST + "33" + " field is not presented",
				dwellPage.existsField(DWELL_TEST + "33" + "_stringValue", GeneralPolicyComponentPage.ATTRIBUTE_ADDITIONAL));
		
		// add new Dwell instance
		dwellPage.addDwell();
		
		// check instance list size and BreadCrumb
		assertEquals("Wrong Dwell instance list size", 2, dwellPage.getDwellListSize());
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());	
		
		// go back to RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);
		
		// check no BreadCrumb
		assertFalse("BreadCrumb is visible", dataGatherPage.isBreadCrumbDisplayed());
		
		// input "coverage" in test1 field
		riskItemPage.setTest1(TAB_COVERAGE);		
		
		// go to Coverage tab and check 
		assertTrue(TAB_COVERAGE + " tab cannot be found", dataGatherPage.existsTab(1, TAB_COVERAGE));
		dataGatherPage.openTab(1, TAB_COVERAGE);
		
		// check fields, also BreadCrumb
		assertTrue(COVERAGE_LIMITAMOUNT + " field is not presented",
				coveragePage.existsField(COVERAGE_LIMITAMOUNT, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertTrue(COVERAGE_COVERLEVELCD + " field is not presented",
				coveragePage.existsField(COVERAGE_COVERLEVELCD, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());
		
		// go back to RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);
		
		// add new RiskItem instance
		riskItemPage.addRiskItem();
		
		// check instance list size and BreadCrumb
		assertEquals("Wrong RiskItem instance list size", 2, riskItemPage.getRiskItemListSize());
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());
				
		// go to Dwell tab and check 
		dataGatherPage.openTab(1, TAB_DWEELL);
		
		// check question, also BreadCrumb
		assertTrue("Optional Question label is not visible", dwellPage.isOptionalQuestionLabelVisible());
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());
		
		// go to Coverage tab and check 
		dataGatherPage.openTab(1, TAB_COVERAGE);
		
		// check no fields, also BreadCrumb
		assertFalse(COVERAGE_LIMITAMOUNT + " field is presented",
				coveragePage.existsField(COVERAGE_LIMITAMOUNT, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertFalse(COVERAGE_COVERLEVELCD + " field is presented",
				coveragePage.existsField(COVERAGE_COVERLEVELCD, GeneralPolicyComponentPage.ATTRIBUTE_STANDARD));
		assertTrue("BreadCrumb is not visible", dataGatherPage.isBreadCrumbDisplayed());
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
