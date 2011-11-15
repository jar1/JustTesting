package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import java.util.Arrays;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage.CopyProductPopUpFields;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.ProductImportInfo;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;


/**
 * Selenium2 PF smoke test which checks product create, copy, delete, BAM activity functions
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.CopyProductUiConf
 *         EisTestCases.TestSuite.ProductSuite.CopyProduct
 *   
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class ProductCopyFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private static final String PRODUCT_CD                   = "simpleProduct1234";		
	private static final ProductImportInfo productImportInfo = new ProductImportInfo(PRODUCT_CD);
	private static final String NEW_PRODUCT_CODE_COPY        = "pcc" + System.currentTimeMillis();
	private static final String NEW_PRODUCT_NAME_COPY        = "pnc" + System.currentTimeMillis();
	private static final String PRODUCT_DATE				 = "11/01/2011";
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(productImportInfo);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product</li>
	 * 	<li>Change product properties</li>
	 * 	<li>Deploy and Activate product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for newly created product
	 *  	<ul><li>Check: unique product found</li></ul></li>
	 * 	<li>Copy found product
	 *  	<ul><li>Check: data and elements in popup</li>
	 *  		<li>Check: mandatory fields are OK</li></ul></li>
	 * 	<li>Click Save button
	 *  	<ul><li>Check: error messages for all mandatory fields appears</li></ul></li>
	 * <ol>
	 */	
	@Test
	public void copyProductUIShouldBeAsExpected() {		
		// go to product search page	
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());

		// search for newly created product
		productSearchPage.enterProductCodeCriteria(PRODUCT_CD).enterProductNameCriteria(PRODUCT_CD).clickSearch();
		assertEquals(Arrays.asList(PRODUCT_CD), productSearchPage.getSearchResultCodes());		
		
		// copy newly created product
		ProductCopyPopUpPage productCopyPopUpPage = productSearchPage.copyProduct(PRODUCT_CD);
		
		// check if data and element in popup are OK
		assertEquals("Copied product code should be " + PRODUCT_CD,
				PRODUCT_CD + " (ver. 1.0)",productCopyPopUpPage.getCopiedProductValue());		
		assertTrue("Wrong fields/buttons in product copy UI",
				ElementUtils.getInvisibleElements(productCopyPopUpPage).isEmpty());
		
		// check if all fields are required
		assertTrue("Product Name field should be mandatory",
				productCopyPopUpPage.isFieldMandatory(CopyProductPopUpFields.productName));
		assertTrue("Product Code field should be mandatory",
				productCopyPopUpPage.isFieldMandatory(CopyProductPopUpFields.productCode));
		assertTrue("Product transaction Date field should be mandatory",
				productCopyPopUpPage.isFieldMandatory(CopyProductPopUpFields.usedForTransactionDate));
		assertTrue("Product Policy Effective Date field should be mandatory",
				productCopyPopUpPage.isFieldMandatory(CopyProductPopUpFields.usedForPolicyEffectiveDate));

		// click save and check for errors
		productCopyPopUpPage.clickSave();
		assertFalse("Empty field error should appear",
				productCopyPopUpPage.getFieldError(CopyProductPopUpFields.productName).isEmpty());
		assertFalse("Empty field error should appear",
				productCopyPopUpPage.getFieldError(CopyProductPopUpFields.productCode).isEmpty());
		assertFalse("Empty field error should appear",
				productCopyPopUpPage.getFieldError(CopyProductPopUpFields.usedForTransactionDate).isEmpty());
		assertFalse("Empty field error should appear",
				productCopyPopUpPage.getFieldError(CopyProductPopUpFields.usedForPolicyEffectiveDate).isEmpty());
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product</li>
	 * 	<li>Change product properties</li>
	 * 	<li>Deploy and Activate product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for newly created product
	 *  	<ul><li>Check: unique product found</li></ul></li>
	 * 	<li>Copy found product</li>
	 * 	<li>Fill in fields and save
	 * 		<ul><li>Check: product copied</li></ul></li>
	 * 	<li>Expand BAM activities panel</li>
	 *  	<ul><li>Check: copy BAM activity created with status Finished</li></ul>
	 *  <li>Click Cancel to go back to search page</li>
	 *  <li>Type in name and code and click search
	 *  	<ul><li>Check: unique product found</li></ul></li>
	 * <ol>
	 */	
	@Test
	public void shoulCopyProduct() {		
		// go to product search page	
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		// search for newly created product
		productSearchPage.enterProductCodeCriteria(PRODUCT_CD).clickSearch();
		assertEquals(PRODUCT_CD + " product should be found",
				Arrays.asList(PRODUCT_CD), productSearchPage.getSearchResultCodes());		
		
		// copy newly created product
		ProductConsolidatedViewPage productConsolidatedViewPage = productSearchPage.copyProduct(PRODUCT_CD)
				.enterFieldValue(CopyProductPopUpFields.productName, NEW_PRODUCT_NAME_COPY)
				.enterFieldValue(CopyProductPopUpFields.productCode, NEW_PRODUCT_CODE_COPY)				
				.enterFieldValue(CopyProductPopUpFields.usedForTransactionDate, PRODUCT_DATE)
				.enterFieldValue(CopyProductPopUpFields.usedForPolicyEffectiveDate, PRODUCT_DATE)
				.clickSave();
		
		// check if copied
		assertTrue("Product cod eshoul be " + NEW_PRODUCT_CODE_COPY,
				productConsolidatedViewPage.existsProduct(NEW_PRODUCT_CODE_COPY));
		
		// expand BAM activity panel
		productConsolidatedViewPage.expandBAMSection();		
		
		// check BAM activities		
		assertEquals("Wrong COPY activity BAM message", 
				"Copying the product " + PRODUCT_CD + " v. 1 to " + NEW_PRODUCT_NAME_COPY + " (" + NEW_PRODUCT_CODE_COPY + ")",
				productConsolidatedViewPage.getBAMActivityDescription(0));
		assertEquals("Wrong COPY activity BAM status",
				"Finished",
				productConsolidatedViewPage.getBAMActivityStatus(0));	
		
		// go back to search
		productConsolidatedViewPage.clickCancel();
		
		// search for newly copied product
		productSearchPage.enterProductCodeCriteria(NEW_PRODUCT_CODE_COPY).enterProductNameCriteria(NEW_PRODUCT_NAME_COPY).clickSearch();	
		assertEquals(NEW_PRODUCT_CODE_COPY + " product should be found",
				Arrays.asList(NEW_PRODUCT_CODE_COPY), productSearchPage.getSearchResultCodes());
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, 1.0D);
		getProductManager().deleteProduct(NEW_PRODUCT_CODE_COPY, 1.0D);		
	}
}