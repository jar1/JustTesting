package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import java.util.Arrays;

import com.exigen.ipb.product.pages.ProductSearchPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.ProductImportInfo;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;


/**
 * Selenium2 PF smoke test which checks product create, copy, delete, BAM activity functions
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.ProductSearch
 *   
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class ProductSearchFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                      = "simpleProduct1234";	
	private final static ProductImportInfo productImportInfo 	= new ProductImportInfo(PRODUCT_CD);
	private static final String NEW_PRODUCT_CODE_WRONG          = "pcw" + System.currentTimeMillis();
	private static final String NEW_PRODUCT_NAME_WRONG          = "pnw" + System.currentTimeMillis();
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProductAndActivate(productImportInfo);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product</li>
	 * 	<li>Change product properties</li>
	 * 	<li>Deploy and Activate product</li>
	 * 	<li>Go to product search page
	 * 		<ul><li>Check: all page elements presented</li></ul></li>
	 * 	<li>Go back to product search page</li>
	 * 
	 * 	<li>Type right name in 'Product Name' search criteria</li>
	 * 	<li>Type wrong code in 'Product Code' search criteria
	 *		<ul><li>Check: no results found</li></ul></li>
	 * 	<li>Type wrong name in 'Product Name' search criteria</li>
	 * 	<li>Type right code in 'Product Code' search criteria
	 * 		<ul><li>Check: no results found</li></ul></li>
	 * 	<li>Type right name in 'Product Name' search criteria</li>
	 * 	<li>Type right code in 'Product Code' search criteria
	 * 		<ul><li>Check: only proper product found</li></ul></li>
	 * 	<li>Select wrong LoB in 'Line of Business' search criteria
	 *  	<ul><li>Check: no results found</li></ul></li>
	 * 	<li>Type right LoB in 'Line of Business' search criteria
	 *   	<ul><li>Check: only proper product found</li></ul></li>
	 * 	<li>Type wrong status in 'Status' search criteria
	 *   	<ul><li>Check: no results found</li></ul></li>
	 * 	<li>Type right status in 'Status' search criteria
	 *   	<ul><li>Check: only proper product found</li><ul></li>
	 * </ol>
	 */	
	@Test
	public void shouldSearchForProduct() {		
		// go to product search page	
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		// check search elements
		assertTrue("Different search page fields expected", 
				ElementUtils.getInvisibleElements(productSearchPage, ProductSearchPage.groupFields).isEmpty());
		assertTrue("Different search page buttons expected",
				ElementUtils.getInvisibleElements(productSearchPage, ProductSearchPage.groupButtons).isEmpty());

		//	positive name, negative code
		productSearchPage.enterProductCodeCriteria(NEW_PRODUCT_CODE_WRONG).enterProductNameCriteria(PRODUCT_CD).clickSearch();
		assertTrue("No product should be found", productSearchPage.isSearchEmpty());
		
		//	negative name, positive code
		productSearchPage.enterProductCodeCriteria(PRODUCT_CD).enterProductNameCriteria(NEW_PRODUCT_NAME_WRONG).clickSearch();		
		assertTrue("No product should be found", productSearchPage.isSearchEmpty());
		
		// positive name, positive code
		productSearchPage.enterProductCodeCriteria(PRODUCT_CD).enterProductNameCriteria(PRODUCT_CD).clickSearch();
		assertEquals(PRODUCT_CD + " should be found", Arrays.asList(PRODUCT_CD), productSearchPage.getSearchResultCodes());		
		
		//	negative LOB	
		productSearchPage.selectLineOfBusinessCriteria(LOB.Aircraft).clickSearch();
		assertTrue("No product should be found", productSearchPage.isSearchEmpty());
		
		// positive LOB
		productSearchPage.selectLineOfBusinessCriteria(LOB.Automobile).clickSearch();
		assertEquals(PRODUCT_CD + " should be found", Arrays.asList(PRODUCT_CD), productSearchPage.getSearchResultCodes());			
		
		//	negative Status	
		productSearchPage.selectStatusCriteria("Expired").clickSearch();
		assertTrue("No product should be found", productSearchPage.isSearchEmpty());
		
		// positive Status
		productSearchPage.selectStatusCriteria("Activated").clickSearch();
		assertEquals(PRODUCT_CD + " should be found", Arrays.asList(PRODUCT_CD), productSearchPage.getSearchResultCodes());		
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		deleteTestProduct(productImportInfo);
	}
}