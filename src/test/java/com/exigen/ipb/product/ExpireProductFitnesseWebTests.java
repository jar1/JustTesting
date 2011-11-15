package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.dialogs.ProductExpirationDialog;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * @category FitNesse
 * 
 * Covers:  EisTestCases.TestSuite.ProductSuite.ExpireProduct
 * 
 * @author gzukas
 * @since 3.9
 */
public class ExpireProductFitnesseWebTests extends AbstractProductSeleniumTests {

	private final static String PRODUCT_ZIP = "target/test-classes/products/expiration234.zip";	
	private final static String PRODUCT_CD = "expiration234";	
	private final static double PRODUCT_VERSION = 1.0;	
	private final static String EXPIRATION_DATE = "12/12/2021";
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_ZIP);
	}
	
	/**
	 * Tests whether product expiration date is changed.
	 * 
	 * Test case for imported product:
	 * <ol>
	 *   <li>Search for imported product</li>
	 *   <li>Change expiration date for this product</li>
	 *   <li>Navigate to 'View Rule Sets' screen</li>
	 *   <li>Verify expiration date</li>
	 * </ol>
	 */
	@Test
	public void shouldChangeProductExpireDate() {
		ProductSearchPage searchPage = create(ProductSearchPage.class);
		searchPage.navigate(getApplication());
		
		assertTrue("Product was not found", searchPage.searchForProduct(PRODUCT_CD));
		
		ProductExpirationDialog expireDialog = searchPage.clickExpire(0);
		expireDialog.setExpirationValue(EXPIRATION_DATE);
		expireDialog.clickOk();
		
		String actualExpirationDate = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION).viewRuleSets().getExpirationDate(0);
		
		assertEquals("Expirations dates should be equal", EXPIRATION_DATE, actualExpirationDate);
	}
		
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
