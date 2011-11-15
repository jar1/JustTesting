package com.exigen.ipb.product;

import org.junit.After;

import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.policy.pages.QuoteListPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.ProductsDeletePage;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Tests product deletion.
 *
 * @author ggrazevicius
 * @since 3.9
 */
public class ProductDeletionWebTests extends AbstractProductSeleniumTests {
	
	/*
	 * Product contains Policy and StateSpecificRiskItem components, one workspace, 3 tabs,
	 * base rule with an extension, tab visibility rule.
	 */
	private final static String PRODUCT_DIR = "target/test-classes/products/PFDT.zip";
	private final static String PRODUCT_CODE = "PFDT";
	private final static double PRODUCT_VERSION = 1.0;
	
	/**
	 * Test case:
	 * 1. Import sample product.
	 * 2. Create quote for product.
	 * 3. Go to product search page
	 * 4. Delete product from there
	 * 
	 *  -> Ensure the product is deleted.
	 * 
	 */
	@Test
	public void shouldDeleteProduct() {
		importTestProductAndActivate(PRODUCT_CODE, PRODUCT_VERSION, PRODUCT_DIR);

		QuoteListPage quoteListPage = create(QuoteListPage.class);
		quoteListPage.navigate(getApplication());
		
		// Create a quote
		GeneralPolicyComponentPage policyPage = quoteListPage.addNewQuote(PRODUCT_CODE, GeneralPolicyComponentPage.class);
		
		// Fill in policy data
		policyPage.fillDefaultMandatoryValuesForPolicy().clickNext();
		
		// Fill in RiskItem data
		GeneralRiskItemComponentPage riskItemPage = create(GeneralRiskItemComponentPage.class);
		riskItemPage.clickNext();
		// Done creating quote
		
		// Go to search page
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		// Find product
		productSearchPage.searchForProduct(PRODUCT_CODE);
		productSearchPage.selectProduct(PRODUCT_CODE);
		// Delete it
		ProductsDeletePage productsDeletePage = productSearchPage.deleteProduct();	
		
		// Should be one object to delete (the created quote)
		assertEquals("There should be one object for deletion", 1, productsDeletePage.getObjectCount());
		
		productsDeletePage.clickDeleteAndConfirm().clickBack();		
		productSearchPage = create(ProductSearchPage.class);
		
		productSearchPage.searchForProduct(PRODUCT_CODE);
		assertTrue("Product should not exist", productSearchPage.isSearchEmpty());
	}
	
	@After
	public void after() {
		getProductManager().deleteProduct(PRODUCT_CODE, PRODUCT_VERSION);
	}
	
}
