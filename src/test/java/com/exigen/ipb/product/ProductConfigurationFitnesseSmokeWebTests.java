package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.domain.PolicyChangesAllowedAt;
import com.exigen.ipb.policy.domain.RenewalTypesAllowed;
import com.exigen.ipb.product.pages.ProductActionsPage.ProcessState;
import com.exigen.ipb.product.pages.ProductActionsPage.QuoteState;
import com.exigen.ipb.product.pages.ProductActionsPage.TxType;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage.CopyProductPopUpFields;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.BroadLOB;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.PolicyTerm;
import com.exigen.ipb.selenium.utils.ProductAction;
import com.exigen.ipb.selenium.utils.ProductImportInfo;
import com.exigen.ipb.selenium.utils.ProductType;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * Selenium2 PF smoke test which checks product create, copy, delete, BAM activity functions
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.SmokeSuite.ProductSmoke.ProductConfiguration
 *         EisTestCases.SmokeSuite.ProductSmoke.ProductCopy
 *         EisTestCases.SmokeSuite.ProductSmoke.ProductDelete
 *         EisTestCases.SmokeSuite.ProductSmoke.ProductBam
 *   
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class ProductConfigurationFitnesseSmokeWebTests extends AbstractProductSeleniumTests {	
	
	private static final String CREATED_PRODUCT_CODE_NAME          = "ps"  + System.currentTimeMillis();
	private static final String COPIED_PRODUCT_CODE_NAME           = "psc" + System.currentTimeMillis();
	private static final String IMPORTED_EMPTY_PRODUCT             = "emptySeleniumProd";
	private static final ProductImportInfo productImportInfo       = new ProductImportInfo(IMPORTED_EMPTY_PRODUCT);
	
	private ProductConsolidatedViewPage productConsolidatedViewPage;	
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Go to product search page</li>
	 * 	<li>Create new 'Policy Product' type product</li>
	 * 	<li>In new product creation page fill in ALL fields and click 'Next' button</li>
	 *  	<ul><li>Check: product with inputed code successfully created</li></ul></li>
	 * 	<li>Define actions for product</li>
	 * 	<li>Connect 'Policy (2.0)' and 'RiskItem (1.0)' components to product</li>
	 * 	<li>Configure workspace</li>
	 * 	<li>Deploy product</li>
	 * 	<li>Activate product
	 *  	<ul><li>Check: proper product code is presented</li>
	 *  		<li>Check: product status is 'Activated'</li></ul></li>
	 * </ol> 
	 */	
	@Test
	public void shouldCreateNewProduct() {		
		// create new product
		productConsolidatedViewPage = createProduct(CREATED_PRODUCT_CODE_NAME);
				
		// check if product created
		assertTrue(CREATED_PRODUCT_CODE_NAME + " product should be presented",
				productConsolidatedViewPage.existsProduct("dfhyd"));

		// define actions
		defineProductActions(ProcessState.quote, TxType.newBusiness, QuoteState.dataGather, 
				ProductAction.dataGather, ProductAction.quoteRulesOverrideUpdate, ProductAction.quoteInquiry);
		defineProductActions(ProcessState.quote, TxType.newBusiness, QuoteState.premiumCalculated, 
				ProductAction.quoteRulesOverrideUpdate, ProductAction.quoteInquiry, ProductAction.issue);

		// connect components
		productConsolidatedViewPage.updateProductData()
				.expandComponentSelection()
				.selectComponent("Policy", 2)
				.selectComponent("RiskItem", 1)
				.clickAddComponents()
				.clickSave();
		
		// create workspace
		productConsolidatedViewPage.configureWorkspace().clickSave();
		
		// deploy and activate product
		productConsolidatedViewPage.deployProduct().activateProduct();
		
		// check if product is activated
		assertTrue(CREATED_PRODUCT_CODE_NAME + " product should be activated",
				productConsolidatedViewPage.existsProduct(CREATED_PRODUCT_CODE_NAME) && productConsolidatedViewPage.isActivated());
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Go to product search page</li>
	 * 	<li>Create new 'Policy Product' type product</li>
	 * 	<li>In new product creation page fill in ALL fields and click 'Next' button</li>
	 * 	<li>Define actions for product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for newly created product</li>
	 * 	<li>Copy newly created product
	 * 		<ul><li>Check: product with new code copied successfully</li></ul></li> 
	 * </ol>
	 */	
	@Test
	public void shouldCopyProduct() {		
		// import product
		importTestProduct(productImportInfo);

		// copy product and check
		productConsolidatedViewPage = copyProduct(IMPORTED_EMPTY_PRODUCT, COPIED_PRODUCT_CODE_NAME);
		assertTrue(COPIED_PRODUCT_CODE_NAME + " product should be presented",
				productConsolidatedViewPage.existsProduct(COPIED_PRODUCT_CODE_NAME));
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Go to product search page</li>
	 * 	<li>Create new 'Policy Product' type product</li>
	 * 	<li>In new product creation page fill in ALL fields and click 'Next' button</li>
	 * 	<li>Define actions for product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for newly created product</li>
	 * 	<li>Copy newly created product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for copied product</li>
	 * 	<li>Delete copied product
	 *  	<ul><li>Check: copied product is deleted successfully</li></ul></li>
	 * </ol> 
	 */	
	@Test
	public void shouldDeleteProduct() {			
		// import product
		importTestProduct(productImportInfo);
		
		// go to product search page
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		// search for imported product
		productSearchPage.searchForProduct(IMPORTED_EMPTY_PRODUCT);
		
		// select product
		productSearchPage = productSearchPage.selectProduct(IMPORTED_EMPTY_PRODUCT)
				.deleteProduct()
				.clickDeleteAndConfirm()
				.clickBack();
		
		productSearchPage.searchForProduct(IMPORTED_EMPTY_PRODUCT);		
		assertTrue(IMPORTED_EMPTY_PRODUCT + " should be deleted", productSearchPage.isSearchEmpty());
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Go to product search page</li>
	 * 	<li>Create new 'Policy Product' type product</li>
	 * 	<li>In new product creation page fill in ALL fields and click 'Next' button</li>
	 * 	<li>Define action for product</li>
	 * 	<li>Connect 'Policy (2.0)' component to product</li>
	 * 	<li>Configure workspace</li>
	 * 	<li>Deploy product</li>
	 * 	<li>Activate product</li>
	 * 	<li>Go to product search page</li>
	 * 	<li>Search for newly created product</li>
	 * 	<li>Open newly created product</li>
	 * 	<li>Expand BAM activities section
	 *  	<ul><li>Check: new product creation BAM activity presented with status 'Finished'</li>
	 *  		<li>Check: actions update for product BAM activity presented with status 'Finished'</li>
	 *  		<li> Check: blocks configuration save BAM activity presented with status 'Finished'</li>
	 *  		<li>Check: workspace configuration update BAM activity presented with status 'Finished'</li>
	 *  		<li>Check: product deployment BAM activity presented with status 'Finished'</li></ul></li>
	 * </ol> 
	 */	
	@Test
	public void shouldCheckProductBAMActivities() {			
		// create new product and check (activity Nr1)
		productConsolidatedViewPage = createProduct(CREATED_PRODUCT_CODE_NAME);

		// define actions (activity Nr2)
		defineProductActions(ProcessState.quote, TxType.newBusiness, QuoteState.dataGather, ProductAction.dataGather);
		
		// connect components (activity Nr3)
		productConsolidatedViewPage.updateProductData()
				.expandComponentSelection()
				.selectComponent("Policy", 2)
				.clickAddComponents()
				.clickSave();
		
		// create workspace (activity Nr4)
		productConsolidatedViewPage.configureWorkspace().clickSave();
		
		// deploy and activate product (activity Nr5)
		productConsolidatedViewPage.deployProduct().activateProduct();
		
		// go to product search page	
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		// search and open product
		productConsolidatedViewPage = productSearchPage.searchAndOpenProduct(CREATED_PRODUCT_CODE_NAME).expandBAMSection();		
		
		// check activity Nr1		
		assertEquals("Wrong product creation BAM activity message",
				"New product creation: '" + CREATED_PRODUCT_CODE_NAME + "' v. 1",				
				productConsolidatedViewPage.getBAMActivityDescription(4));
		assertEquals("Product creation BAM activity status should be Finished",
				"Finished",	productConsolidatedViewPage.getBAMActivityStatus(4));
		
		// check activity Nr2		
		assertEquals("Wrong product update BAM activity message",
				"Actions update for product " + CREATED_PRODUCT_CODE_NAME + " v. 1",
				productConsolidatedViewPage.getBAMActivityDescription(3));		
		assertEquals("Product update BAM activity status should be Finished",
				"Finished", productConsolidatedViewPage.getBAMActivityStatus(3));
		
		// check activity Nr3		
		assertEquals("Wrong product component configuration BAM activity message",
				"Blocks configuration saved",
				productConsolidatedViewPage.getBAMActivityDescription(2));
		assertEquals("Product component configuration BAM activity status should be Finished",
				"Finished",	productConsolidatedViewPage.getBAMActivityStatus(2));
		
		// check activity Nr4		
		assertEquals("Wrong product workspase configuration BAM activity message",
				"Workspace configuration update",
				productConsolidatedViewPage.getBAMActivityDescription(1));
		assertEquals("Product workspase configuration BAM activity status should be Finished",
				"Finished",	productConsolidatedViewPage.getBAMActivityStatus(1));
		
		// check activity Nr5		
		assertEquals("Wrong product deploy BAM activity message",
				"Product " + CREATED_PRODUCT_CODE_NAME + " v. 1 deployed",
				productConsolidatedViewPage.getBAMActivityDescription(0));
		assertEquals("Product deploy BAM activity status should be Finished",
				"Finished",	productConsolidatedViewPage.getBAMActivityStatus(0));

	}

	private ProductConsolidatedViewPage createProduct(String productCodeName) {
		// go to product search page		
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		productConsolidatedViewPage = productSearchPage.createProduct(ProductType.POLICY_PRODUCT)
				.enterProductCode(productCodeName)
				.enterProductName(productCodeName)
				.enterAppliesToPolicyEffectiveDate("01/16/2010")
				.enterAppliesToEndorsementEffectiveDate("01/15/2011")
				.setBroadLineOfBusiness(BroadLOB.PERSONAL_LINES)
				.setLineOfBusiness(LOB.Automobile)
				.setPolicyTermType(PolicyTerm.MONTHLY)
				.setPolicyChangesAllowedAt(PolicyChangesAllowedAt.RENEWAL_AND_MIDTERM)
				.setRenewalTypesAllowed(RenewalTypesAllowed.AUTOMATIC)
				.enterDescription("description")
				.clickNext();
		
		return productConsolidatedViewPage;
	}
	
	private ProductConsolidatedViewPage defineProductActions(ProcessState processState, TxType txType, QuoteState quoteState, 
															ProductAction... actions) {
		productConsolidatedViewPage.updateProductActions()
				.editActions(processState, txType, quoteState)
				.assign(actions)
				.clickSave()
				.clickSave();		
		
		return productConsolidatedViewPage;
	}
	
	private ProductConsolidatedViewPage copyProduct(String productCodeName, String copyProductCodeName) {
		// go to product search page
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		
		// search for product
		productSearchPage.searchForProduct(productCodeName);
		
		// copy product
		productConsolidatedViewPage = productSearchPage
				.copyProduct(productCodeName)
				.enterFieldValue(CopyProductPopUpFields.productName, copyProductCodeName)
				.enterFieldValue(CopyProductPopUpFields.productCode, copyProductCodeName)
				.enterFieldValue(CopyProductPopUpFields.usedForTransactionDate, "09/29/2011")
				.enterFieldValue(CopyProductPopUpFields.usedForPolicyEffectiveDate, "09/29/2011")
				.clickSave();
		
		return productConsolidatedViewPage;
	}

	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(IMPORTED_EMPTY_PRODUCT, 1.0D);
		getProductManager().deleteProduct(CREATED_PRODUCT_CODE_NAME, 1.0D);
		getProductManager().deleteProduct(COPIED_PRODUCT_CODE_NAME, 1.0D);
	}
}