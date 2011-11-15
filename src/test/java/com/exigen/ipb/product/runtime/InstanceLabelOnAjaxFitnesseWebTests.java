package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.AjaxInstanceDwellComponentPage;
import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.ProductRuntimeSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductImportInfo;

/**
 * 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.InstanceLabelOnAjax
 * 
 * @category FitNesse
 * @author gzukas
 * @since 3.9
 *
 */
public class InstanceLabelOnAjaxFitnesseWebTests extends ProductRuntimeSeleniumTests {

	private final static String PRODUCT_PATH = "target/test-classes/products/ajaxinstance.zip";	
	private final static String PRODUCT_CD = "ajaxinstance";	
	private final static double PRODUCT_VERSION = 1.0;
	
	@Override
	protected ProductImportInfo getProductImportInfo() {
		return new ProductImportInfo(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_PATH);
	}
	
	/**
	 * Tests whether values of Dwell attributes are saved after series of events.
	 * 
	 * Test case for imported product:
	 * <ol>
	 *   <li>Create new quote</li>
	 *   <li>Open Dwell tab
	 *  	 <ul><li>Check value of attribute test3</li></ul></li>
	 *   <li>Add 2nd instance of Dwell
	 *   	<ul><li>Check name of first Dwell in the list</li></ul></li>
	 *   <li>Change value of primaryResidence attribute</li>
	 *   <li>Change value of test1 attribute
	 *   	<ul><li>Verify that changed values are reflected in list of dwells</li></ul></li>
	 *   <li>Add 3rd instance of Dwell</li>
	 *  	<ul><li>Verify that name of first dwell in list has not changed</li></ul></li>
	 * </ol>
	 * 
	 */
	@Test
	public void listShouldReflectChanges() {
		DataGatheringPage dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, 500000);
		dataGatherPage.navigate(getApplication());

		dataGatherPage.openTab(1, "Dwell");
		AjaxInstanceDwellComponentPage dwellPage = create(AjaxInstanceDwellComponentPage.class);
		
		assertEquals("Value of attribute test3 is incorrect",
				"override Rules", dwellPage.getAttributeValue(AjaxInstanceDwellComponentPage.ATTRIBUTE_TEST3));
		
		dwellPage.clickAdd();
		
		assertDwellName(dwellPage, "override Rules", 0);
		
		dwellPage.setAttributeValue(AjaxInstanceDwellComponentPage.ATTRIBUTE_PRIMARY_RESIDENCE, "123");
		dwellPage.setAttributeValue(AjaxInstanceDwellComponentPage.ATTRIBUTE_TEST1, "yes");
		assertDwellName(dwellPage, "yes override Rules", 1);
		
		dwellPage.setAttributeValue(AjaxInstanceDwellComponentPage.ATTRIBUTE_TEST2, "asd");
		assertDwellName(dwellPage, "yes asd override Rules", 1);
		
		dwellPage.setAttributeValue(AjaxInstanceDwellComponentPage.ATTRIBUTE_TEST4, "asd");
		assertDwellName(dwellPage, "yes asd override Rules asd", 1);
	}
	
	/**
	 * Verifies whether corresponding instance of Dwell has correct name.
	 * 
	 * @param dwellPage  The page of Dwell component.
	 * @param expectedName  Expected name of Dwell instance.
	 * @param index  The index of Dwell in the list.
	 */
	private void assertDwellName(AjaxInstanceDwellComponentPage dwellPage, String expectedName, int index) {
		assertEquals(String.format("Name of dwell #%s is incorrect", index),
				expectedName, dwellPage.getDwellName(index));
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
