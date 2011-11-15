package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductActionsPage.ProcessState;
import com.exigen.ipb.product.pages.ProductActionsPage.QuoteState;
import com.exigen.ipb.product.pages.ProductActionsPage.TxType;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductAction;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.RootEntityCreation
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class RootEntityCreationFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                    = "seleniumProduct";
	private final static double PRODUCT_VERSION               = 1.0D;
	private final static String PRODUCT_DIR                   = "target/test-classes/products/withPolicyRootAndWorkspace.zip";	

	private static final String COMPONENT_RISKITEM            = "RiskItem";	
	private static final String ATTRIBUTE_TEST_TYPE           = "String";
	private static final String ATTRIBUTE_TEST_NAME_LABEL     = "Test";
	
	private static final String ROOT_ENTITY_DEFAULT_NAME      = "Default Policy's Root Configuration";
	private static final String ROOT_ENTITY_DEFAULT_TYPE      = "Policy";		
	private static final String ROOT_ENTITY_RISKITEM_NAME     = "Risk Item Root Entity";	
	private static final String ROOT_ENTITY_RISKITEM_TYPE     = "Risk Item";	
	
	private static final String ENTRY_POINT_YES               = "Yes";
	private static final String ENTRY_POINT_NO                = "No";	
	
	private ProductConsolidatedViewPage productConsolidatedViewPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productConsolidatedViewPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'seleniumProduct' from 'target/test-classes/products/withPolicyRootAndWorkspace.zip'</li>
	 *	<li>Change product name, code and dates</li>
	 * 	<li>Go to update product data page</li>
	 * 	<li>Add new attribute for RiskItem</li>
	 * 	<li>Make all attributes displayable</li>
	 * 	<li>Save configuration</li>
	 * 	<li>Add new Root Entity
	 *  	<ul><li>Check: new entity added in drop down and list</li></ul></li>
	 * 	<li>Select new root entity</li>
	 * 	<li>Add actions</li>
	 * 	<li>Add components</li>
	 * 	<li>Create workspace
	 *  	<ul><li>Check: both entities with proper options are presented in list</li></ul></li>
	 * 	<li>Change New Entity to Entry Point
	 *  	<ul><li>Check: new entity is now marked as entry point</li></ul></li>
	 * 	<li>Deploy and activate product
	 *  	<ul><li>Check: proper product activated successfully</li></ul></li>
	 * </ol>
	 */		
	@Test
	public void shouldCreateNewRootEntity() {		
		// add attribute to RiskItem
		productConsolidatedViewPage.updateProductData()
				.configureComponent(COMPONENT_RISKITEM)
				.clickAddNewAttribute()
				.setDataType(ATTRIBUTE_TEST_TYPE)
				.setAttributeName(ATTRIBUTE_TEST_NAME_LABEL)
				.setAttributeLabel(ATTRIBUTE_TEST_NAME_LABEL)
				.add()
				.selectDisplaybleAll()
				.clickSave()
				.clickSave();
		
		// add new Root Entity
		productConsolidatedViewPage.addNewRootEntity(ROOT_ENTITY_RISKITEM_NAME);
		// check if new entity added
		assertTrue(ROOT_ENTITY_RISKITEM_NAME + " root entity should be added", 				
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_NAME)
				.containsAll(Arrays.asList(ROOT_ENTITY_DEFAULT_NAME, ROOT_ENTITY_RISKITEM_NAME))
				&&
				productConsolidatedViewPage.getRootEntityDropDown()
				.containsAll(Arrays.asList(ROOT_ENTITY_DEFAULT_NAME, ROOT_ENTITY_RISKITEM_NAME)));

		// select new root entity
		productConsolidatedViewPage.selectRootEntity(ROOT_ENTITY_RISKITEM_NAME);
		
		// update actions
		defineProductActions(ProcessState.quote, TxType.newBusiness, QuoteState.dataGather, 
				ProductAction.dataGather, ProductAction.quoteRulesOverrideUpdate, ProductAction.quoteInquiry);		
		defineProductActions(ProcessState.quote, TxType.newBusiness, QuoteState.premiumCalculated, 
				ProductAction.dataGather, ProductAction.quoteRulesOverrideUpdate, ProductAction.quoteInquiry, ProductAction.issue);

		// connect components
		productConsolidatedViewPage.updateProductData()
				.expandComponentSelection()
				.selectComponent("Policy", 2)
				.selectComponent("RiskItem", 1)
				.clickAddComponents()
				.clickSave();
			
		// create workspace
		productConsolidatedViewPage.configureWorkspace().clickSave();
		
		// check root entity list		
		assertTrue("Both enntities should be presented",
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_NAME)
				.containsAll(Arrays.asList(ROOT_ENTITY_DEFAULT_NAME, ROOT_ENTITY_RISKITEM_NAME))
				&&
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_TYPE)
				.containsAll(Arrays.asList(ROOT_ENTITY_DEFAULT_TYPE, ROOT_ENTITY_RISKITEM_TYPE))
				&&
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_VERSION)
				.containsAll(Arrays.asList("1", "1"))
				&&
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_ENTRYPOINT)
				.containsAll(Arrays.asList(ENTRY_POINT_YES, ENTRY_POINT_NO)));
		
		// change RiskItem to Entry Point YES
		productConsolidatedViewPage.configureRootEntity(ROOT_ENTITY_RISKITEM_NAME, ProductConsolidatedViewPage.ROOT_ENTITY_CONFIGURE)
				.setEntryPoint(true)
				.save();
		// check value changed
		assertTrue(ROOT_ENTITY_RISKITEM_NAME + " should be wntry point",
				productConsolidatedViewPage.getRootEntityColumn(ProductConsolidatedViewPage.ROOT_ENTITY_COLUMN_ENTRYPOINT)
				.containsAll(Arrays.asList(ENTRY_POINT_NO, ENTRY_POINT_YES)));
		
		// deploy and activate product		
		productConsolidatedViewPage.deployProduct().activateProduct();
		
		// check product code and activation
		assertTrue(PRODUCT_CD + " should be activated",
				productConsolidatedViewPage.existsProduct(PRODUCT_CD) && productConsolidatedViewPage.isActivated());
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
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
}
