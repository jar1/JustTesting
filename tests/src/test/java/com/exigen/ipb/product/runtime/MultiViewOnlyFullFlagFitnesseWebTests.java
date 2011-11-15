package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.MultiViewOnlyFullFlagComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.MultiViewOnlyFullFlag
 * 
 * @category FitNesse
 * @author gzukas
 * @since 3.9
 * 
 */
public class MultiViewOnlyFullFlagFitnesseWebTests extends AbstractProductSeleniumTests {

	private static final String PRODUCT_DIR = "target/test-classes/products/multiViewOnly.zip";	
	private static final String PRODUCT_CD = "multiViewOnly";	
	private static final double PRODUCT_VERSION = 1.0;

	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}

	/**
	 * Tests whether certain attributes of MultiViewOnlyComponent are displayed
	 * when new quote is created and tab of component is active.
	 */
	@Test
	public void attributesShouldBeDisplayed() {
		MultiViewOnlyFullFlagComponentPage componentPage =
				create(MultiViewOnlyFullFlagComponentPage.class, PRODUCT_CD);
		
		componentPage.navigate(getApplication());

		assertEquals("Only certain number of attributes should be invisible",
				4, ElementUtils.getInvisibleElements(componentPage).size());
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
