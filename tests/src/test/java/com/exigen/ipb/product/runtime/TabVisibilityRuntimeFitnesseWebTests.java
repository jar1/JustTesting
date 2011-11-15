package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralCoverageComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * 
 * 
 * Covers:  EisTestCases.TestSuite.ProductSuite.TabVisibilityRuntime
 * 
 * @category FitNesse 
 * @author jdaskevicius
 * @since 3.9
 * 
 */

public class TabVisibilityRuntimeFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final String PRODUCT_CD       = "tabvisible345";
	private static final double PRODUCT_VERSION  = 1.0;
	private static final String PRODUCT_DIR      = "target/test-classes/products/tabvisible345.zip";
	private static final int CUSTOMER_NR         = 500000;

	private static final String TAB_RISKITEM     = "Risk Item";
	private static final String TAB_COVERAGE     = "Coverage";
	private static final String TAB_RATEACTION   = "Rate Action";
	
	private DataGatheringPage dataGatherPage;
	private GeneralRiskItemComponentPage riskItemPage;
	private GeneralCoverageComponentPage coveragePage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'tabvisible345 ' from 'target/test-classes/products/tabvisible345 .zip'</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of newly imported product</li>
	 *  <li>Open 'Risk Item' tab and prefill data to false expression</li>
	 *  <li>Click Next button
	 *  	<ul><li>Check: Coverage tab is not presented</li></ul></li>
	 * 
	 *  <li>Open 'Risk Item' tab and prefill data to true expression</li>
	 *  <li>Click Next button
	 *  	<ul><li>Check: Coverage tab is presented</li></ul></li>
	 *  
	 *  <li>Prefill Coverage tab with data</li>
	 *  <li>Open 'Risk Item' tab and prefill data back to false expression</li>
	 *  <li>Click Next button
	 *  	<ul><li>Check: Coverage tab is not presented</li></ul></li>
	 *  
	 *  <li>Open 'Risk Item' tab and prefill data to true expression</li>
	 *  <li>Click Next button
	 *  	<ul><li>Check: Coverage tab is presented</li>
	 *  		<li>Check: inputed data is not presented</li></ul></li>
	 * </ol>
	 *  
	 */	
	@Test
	public void shouldCheckTabVisibilityInRuntime() {
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		// open RiskItem tab
		assertTrue(TAB_RISKITEM + " tab cannot be found", dataGatherPage.existsTab(1, TAB_RISKITEM));
		dataGatherPage.openTab(1, TAB_RISKITEM);
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		// prefill data in RiskItem tab
		riskItemPage.setItemName("test1");
		riskItemPage.setTerritoryCd("test1");
		riskItemPage.setAdditionalInterestExists(true);
		dataGatherPage.clickNext();		
		
		// check Coverage tab is not presented
		assertEquals("Current tab is not " + TAB_RATEACTION, TAB_RATEACTION, dataGatherPage.getSelectedTab(1));
		
		// open RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);	
		
		// prefill data in RiskItem tab
		riskItemPage.setItemName("asd");
		dataGatherPage.clickNext();
		
		// check Coverage tab is presented
		assertEquals("Current tab is not " + TAB_COVERAGE, TAB_COVERAGE, dataGatherPage.getSelectedTab(2));
		
		// open Coverage tab
		assertTrue(TAB_COVERAGE + " tab cannot be found", dataGatherPage.existsTab(2, TAB_COVERAGE));	
		dataGatherPage.openTab(2, TAB_COVERAGE);
		coveragePage = create(GeneralCoverageComponentPage.class);		
		
		// prefill data in Coverage
		coveragePage.setLimitAmountValue("123");
		coveragePage.setCoverLevelCd("123");
		coveragePage.setDeductibleTypeCd("123");
		coveragePage.setDeductibleAmount("123");
		
		// open RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);
	
		// prefill data in RiskItem tab
		riskItemPage.setItemName("test2");
		dataGatherPage.clickNext();
		
		// check Coverage tab is not presented
		assertEquals("Coverage tab is not present", TAB_RATEACTION, dataGatherPage.getSelectedTab(1));
		
		// open RiskItem tab
		dataGatherPage.openTab(1, TAB_RISKITEM);
		
		// prefill data in RiskItem tab
		riskItemPage.setItemName("asd");		
		dataGatherPage.clickNext();
		
		// check Coverage tab is presented
		assertEquals("Coverage tab is not present", TAB_COVERAGE, dataGatherPage.getSelectedTab(2));		
		
		// open Coverage tab
		dataGatherPage.openTab(2, TAB_COVERAGE);	
		
		// check new coverage instance created
		assertTrue("New Coverage instance expected", coveragePage.getLimitAmountValue().isEmpty());
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
