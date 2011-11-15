package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductDataPage;
import com.exigen.ipb.product.pages.dialogs.ProductComponentReplacePopUp;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.TestCompReplacement.TestComponentFilter
 *         EisTestCases.TestSuite.ProductSuite.TestCompReplacement.TestReplacementUi
 *         EisTestCases.TestSuite.ProductSuite.ComponentReplacement
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class ComponentReplaceFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                    = "comReplace";
	private final static double PRODUCT_VERSION               = 1.0D;
	private final static String PRODUCT_DIR                   = "target/test-classes/products/compReplace.zip";		
	
	private static final String COMPONENTS_POLICY	          = "Policy";
	private static final String COMPONENTS_POLICY_TYPE	      = "General";
	private static final String COMPONENTS_RISKITEM	          = "RiskItem";
	private static final String COMPONENTS_RISKITEM_TYPE      = "Risk Item";
	private static final String COMPONENTS_COVERAGE           = "Coverage";
	private static final String COMPONENTS_COVERAGE_TYPE      = COMPONENTS_COVERAGE;
	private static final String COMPONENTS_VEHICLE            = "Vehicle";

	private static final HashSet<String> RISKITEM_ATTRIBUTES  = new HashSet<String>(
				Arrays.asList("territoryCd", "itemName", "seqNo", "additionalIntExistsInd"));	
	private static final HashSet<String> CONNECTED_COMPS_POLICY_RISKITEM = new HashSet<String>(
				Arrays.asList(COMPONENTS_POLICY   + " (2.0)", COMPONENTS_RISKITEM + " (1.0)"));
	private static final HashSet<String> CONNECTED_COMPS_POLICY_VEHICLE = new HashSet<String>(
			Arrays.asList(COMPONENTS_POLICY   + " (2.0)", COMPONENTS_VEHICLE + " (2.0)"));
	
	private ProductConsolidatedViewPage productConsolidatedViewPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productConsolidatedViewPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * <li>Import product 'comReplace' from 'target/test-classes/products/compReplace.zip'</li>
	 * <li>Go to 'Update Product Data' page
	 *  	 <ul><li>Check: components are connected</li>
	 *    	 	<li>Check: 'Replace' link is presented for all connected components</li></ul></li>  
	 * <li>Click Replace link for RiskItem
	 *  	 <ul><li>Check: popup elements are presented</li></ul></li>
	 * <li>Click Cancel
	 *   	<ul><li>Check: no change are made</li></ul></li> 
	 * <li>Click Replace link for RiskItem and replace component
	 * 		<ul><li>Check: RiskItem replaced</li>
	 * 			<li>Check: no replace link for component</li></ul></li> 
	 * <li>Save configuration
	 *  	<ul><li>Check: product code is not changed</li></ul></li>
	 * </ol>
	 */		
	@Test
	public void shouldDisplayExpectedComponentReplacementUI() {
		// go to update product data ui
		ProductDataPage productDataPage = productConsolidatedViewPage.updateProductData();
			
		// check connected components and Replace link
		assertEquals(CONNECTED_COMPS_POLICY_RISKITEM + " is not connected",
				CONNECTED_COMPS_POLICY_RISKITEM, productDataPage.getConnectedComponentsNamesSet());
		assertTrue("Replace link is not presented", productDataPage.checkReplaceLink());

		// open replace popup for RiskItem and check
		ProductComponentReplacePopUp productComponentReplacePopUp = productDataPage.replaceComponent(COMPONENTS_RISKITEM);
		assertTrue("Incorrect UI elements displayed",
				ElementUtils.getInvisibleElements(productComponentReplacePopUp, "default").isEmpty());
		
		// click cancel and check no changes made
		productDataPage = productComponentReplacePopUp.clickCancel();
		assertEquals(CONNECTED_COMPS_POLICY_RISKITEM + " is not connected",
				CONNECTED_COMPS_POLICY_RISKITEM, productDataPage.getConnectedComponentsNamesSet());

		// replace RiskItem
		productDataPage.replaceComponent(COMPONENTS_RISKITEM).selectReplaceComponent(COMPONENTS_VEHICLE + " [2.0]").merge();

		// check replacement
		assertEquals(CONNECTED_COMPS_POLICY_VEHICLE + " is not connected",
				CONNECTED_COMPS_POLICY_VEHICLE, productDataPage.getConnectedComponentsNamesSet());	
		assertFalse("Replace should not be presented",	productDataPage.checkReplaceLink());
		
		// save configuration
		assertTrue(PRODUCT_CD + " should be opened",
				productDataPage.clickSave().existsProduct(PRODUCT_CD));
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'comReplace' from 'target/test-classes/products/compReplace.zip'</li>
	 *  <li>Go to 'Update Product Data' page</li>
	 *  <li>Click Replace for RiskItem</li>
	 *  <li>Replace with RiskItem [1.0]</li>
	 *  <li>Click Merge</li>
	 *  <li>Save configuration</li>
	 *  <li>Go to 'Update Product Data' page</li>
	 *  <li>Click Configure for RiskItem
	 * 	 	<ul><li>Check: attributes list is OK</li></ul></li>
	 *  <li>Save configuration</li>
	 *  <li>Deploy product
	 *   	<ul><li>Check: product is OK</li></ul></li>
	 * </ol>
	 */		
	@Test
	public void shouldReplaceComponent() {
		// replace RiskItem and save 
		ProductComponentsConfigurationPage productComponentsConfigurationPage = productConsolidatedViewPage
				.updateProductData()
				.replaceComponent(COMPONENTS_RISKITEM)
				.selectReplaceComponent(COMPONENTS_RISKITEM + " [1.0]")
				.merge()
				.clickSave()
				.updateProductData()
				.configureComponent(COMPONENTS_RISKITEM);

		// check if list is OK
		assertEquals(RISKITEM_ATTRIBUTES + " atributtes is not presented",
				RISKITEM_ATTRIBUTES, productComponentsConfigurationPage.getAttributesSet());
		
		// save configuration
		productComponentsConfigurationPage.clickSave().clickSave();
		
		// deploy and check product save configuration
		assertTrue(PRODUCT_CD + " should be opened",
				productConsolidatedViewPage.deployProduct().existsProduct(PRODUCT_CD));
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * <li>Import product 'compReplace' from 'target/test-classes/products/compReplace.zip'</li>
	 * <li>Go to 'Update Product Data' page</li>
	 * <li>Connect Coverage component and save configuration</li>
	 * <li>Go to 'Update Product Data' page</li>
	 * 
	 * <li>Get all available General type components</li>
	 * <li>Click Replace for Policy component
	 *   <ul><li>Check: drop down contains components of type General</li></ul></li>
	 * <li>Click Cancel</li>
	 * 
	 * <li>Get all available Risk Item type components</li>
	 * <li>Click Replace for RiskItem component
	 *   <ul><li>Check: drop down contains components of type Risk Item</li></ul></li>
	 * <li>Click Cancel</li>
	 * 
	 * <li>Get all available Coverage type components
	 * <li>Click Replace for Coverage component</li>
	 *   <ul><li>Check: drop down contains components of type Coverage</li></ul></li>
	 * <li>Click Cancel</li>
	 * <li>Save configuration
	 *   <ul><li>Check: product code is not changed</li></ul></li>
	 * </ol>
	 */		
	@Test
	public void shouldNotReplaceComponent() {
		// go to update product data ui
		ProductDataPage productDataPage = productConsolidatedViewPage
				.updateProductData()
				.expandComponentSelection()
				.selectComponent(COMPONENTS_COVERAGE, 1)
				.clickAddComponents()
				.clickSave()
				.updateProductData()
				.expandComponentSelection();
// ---------------------		
		// get all Policy components which are not user created
		List<String> componentsList = productDataPage.getSystemComponentsOfType(COMPONENTS_POLICY_TYPE);
		
		// open replace popup and check if all Policy components presented
		ProductComponentReplacePopUp productComponentReplacePopUp = productDataPage.replaceComponent(COMPONENTS_POLICY);
		assertTrue("Different components (type: " + COMPONENTS_POLICY_TYPE + ") list is expected", 
				componentsList.containsAll(productComponentReplacePopUp.getComponentsListAllOptions()));
		productComponentReplacePopUp.clickCancel();
// ---------------------	
		// get all RiskItem components which are not user created
		componentsList = productDataPage.getSystemComponentsOfType(COMPONENTS_RISKITEM_TYPE);
		
		// open replace popup and check if all Policy components presented
		productComponentReplacePopUp = productDataPage.replaceComponent(COMPONENTS_RISKITEM);
		assertTrue("Different components (type: " + COMPONENTS_RISKITEM_TYPE + ") list is expected", 
				componentsList.containsAll(productComponentReplacePopUp.getComponentsListAllOptions()));
		productComponentReplacePopUp.clickCancel();		
// ---------------------	
		// get all RiskItem components which are not user created
		componentsList = productDataPage.getSystemComponentsOfType(COMPONENTS_COVERAGE_TYPE);
		
		// open replace popup and check if all Policy components presented
		productComponentReplacePopUp = productDataPage.replaceComponent(COMPONENTS_COVERAGE);
		assertTrue("Different components (type: " + COMPONENTS_COVERAGE_TYPE + ") list is expected",
				componentsList.containsAll(productComponentReplacePopUp.getComponentsListAllOptions()));
		productComponentReplacePopUp.clickCancel();
		
		// save configuration
		assertTrue(PRODUCT_CD + " should be opened",
				productDataPage.clickSave().existsProduct(PRODUCT_CD));
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
