package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.selenium.tests.ProductRuntimeSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductImportInfo;

/**
 *
 * Covers: EisTestCases.TestSuite.ProductSuite.ImportProductRegression
 * 
 * @category FitNesse
 * @author gzukas
 * @since 3.9
 */
public class ProductImportRegressionFitnesseWebTests extends ProductRuntimeSeleniumTests {

	private static final ProductImportInfo[] PRODUCTS = {
		new ProductImportInfo("auto273"),
		new ProductImportInfo("auto317"),
		new ProductImportInfo("auto331"),
		new ProductImportInfo("auto362"),
		new ProductImportInfo("auto39")
	};

	private static final int DEFAULT_CUSTOMER = 500000;
	
	private ProductImportInfo currentProduct;
	
	@Override
	protected boolean beforeImport(ProductImportInfo product) {
		// Uses independent import instead.
		return false;
	}
	
	@Test
	public void shouldImportFrom273() {
		verifyProductImport(PRODUCTS[0]);
	}

	@Test
	public void shouldImportFrom317() {
		verifyProductImport(PRODUCTS[1]);
	}
	
	@Test
	public void shouldImportFrom331() {
		verifyProductImport(PRODUCTS[2]);
	}
	
	@Test
	public void shouldImportFrom362() {
		verifyProductImport(PRODUCTS[3]);
	}
	
	@Test
	public void shouldImportFrom39() {
		verifyProductImport(PRODUCTS[4]);
	}
		
	/**
	 * Verifies whether product was successfully imported by checking value of
	 * <code>AutoPolicy.productCd</code>
	 * 
	 * @param product  The information about product to be verified.
	 */
	private void verifyProductImport(ProductImportInfo product) {
		prepareProduct(product);
		
		assertEquals("Value of attribute 'Policy Type' was not as expected",
				product.getProductCd(), createQuote(product).getProductCd());
		
		// Marks for deletion
		currentProduct = product;
	}

	private GeneralPolicyComponentPage createQuote(ProductImportInfo product) {
		create(DataGatheringPage.class, product.getProductCd(), DEFAULT_CUSTOMER).navigate(getApplication());
		return create(GeneralPolicyComponentPage.class);
	}
	
	@Override
	protected ProductImportInfo getProductImportInfo() {
		return currentProduct;
	}
}
