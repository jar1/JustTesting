package com.exigen.ipb.product;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.QuoteConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.MainApplication;

/**
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.TestComponentLabelInConsolidatedView
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class ComponentLabelInConsolidatedViewFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private static final String PRODUCT_CD_LABEL_VISIBLE      = "clVis";
	private static final String PRODUCT_CD_LABEL_NOT_VISIBLE  = "clNotVis";
	private static final double PRODUCT_VERSION               = 1.0D;
	private static final String PRODUCT_DIR                   = "target/test-classes/products/";	

	private static final String COMPONENTS_RISKITEM_NS	      = "RiskItem";
	
	ProductConsolidatedViewPage productConsolidatedViewPage;
	ProductComponentsConfigurationPage componentPage;
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'clVis' from 'target/test-classes/products/clVis.zip'</li>
	 * 	<li>Go to RiskItem configuration page</li>
	 * 	<li>UNSELECT 'Show Block Label In Consolidated View' and 'Show In Consolidated View'</li>
	 * 	<li>Save and deploy product</li>
	 *  <li>Go to RiskItem configuration page
	 *  	<ul><li>Check: 'Show Block Label In Consolidated View' and 'Show In Consolidated View' is not selected</li></ul></li>
	 * </ol>
	 */
	@Test
	public void componentLabelOptionShouldBeUnSelected() {
		// import product
		importTestProduct(PRODUCT_CD_LABEL_VISIBLE, PRODUCT_VERSION, PRODUCT_DIR + PRODUCT_CD_LABEL_VISIBLE + ".zip");
		
		// unselect checkbox
		setShowInConsolidatedView(PRODUCT_CD_LABEL_VISIBLE, false);
		
		// checkbox are not selected
		assertFalse("'Show Block Label In Consolidated View' should not be selected",
				componentPage.isShowBlockLabelInConsolidatedViewSelected());
		assertFalse("'Show In Consolidated View' should not be selected",
				componentPage.isShowInConsolidatedViewSelected());
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import product 'clVis' from 'target/test-classes/products/clVis.zip'</li>
	 * 	<li>Go to RiskItem configuration page</li>
	 * 	<li>SELECT 'Show Block Label In Consolidated View' and 'Show In Consolidated View'</li>
	 * 	<li>Save and deploy product</li>
	 *  <li>Go to RiskItem configuration page
	 *  	<ul><li>Check: 'Show Block Label In Consolidated View' and 'Show In Consolidated View' is selected</li></ul></li>
	 * </ol>
	 */
	@Test
	public void componentLabelOptionShouldBeSelected() {
		// import product
		importTestProduct(PRODUCT_CD_LABEL_VISIBLE, PRODUCT_VERSION, PRODUCT_DIR + PRODUCT_CD_LABEL_VISIBLE + ".zip");
		
		// select checkbox
		setShowInConsolidatedView(PRODUCT_CD_LABEL_VISIBLE, true);

		// checkbox are selected
		assertTrue("'Show Block Label In Consolidated View' should be selected",
				componentPage.isShowBlockLabelInConsolidatedViewSelected());
		assertTrue("'Show In Consolidated View' should be selected",
				componentPage.isShowInConsolidatedViewSelected());	
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Import and activate product 'clVis' from 'target/test-classes/products/clVis.zip'</li>
	 * 	<li>Go to runtime app for 'Customer Number' = 500000</li>
	 * 	<li>Go to 'Quote' creation of newly imported</li>
	 * 	<li>Open 'Risk Item' tab</li>
	 * 	<li>Click 'top 'Save and exit' button
	 *  	<ul><li>Check: Riskitem component label is presented in quote consolidated view</li></ul>
	 *  </li>
	 * </ol>
	 */
	@Test
	public void componentLabelShouldBeDisplayed() {
		// import product
		importTestProductAndActivate(PRODUCT_CD_LABEL_VISIBLE, PRODUCT_VERSION, PRODUCT_DIR + PRODUCT_CD_LABEL_VISIBLE + ".zip");

		GeneralPolicyComponentPage policyPage = create(GeneralPolicyComponentPage.class, PRODUCT_CD_LABEL_VISIBLE);
		policyPage.navigate(new MainApplication(getDriver(), getConfiguration()));
		
		QuoteConsolidatedViewPage quoteConsolidatedViewPage = policyPage
				.openTab(1, "Risk Item")
				.clickSaveAndExit();

		// check RiskItem block Label is presented
		assertTrue("Components header should be displayed " + COMPONENTS_RISKITEM_NS, 
					quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENTS_RISKITEM_NS));
	}
	
	/**
	 * Test Case:
	 * <ol>	
	 * 	<li>Import and activate product 'clNotVis' from 'target/test-classes/products/clNotVis.zip'</li>
	 * 	<li>Go to runtime app for 'Customer Number' = 500000</li>
	 * 	<li>Go to 'Quote' creation of newly imported and edited product</li>
	 * 	<li>Open 'Risk Item' tab</li>
	 * 	<li>Click 'top 'Save and exit' button
	 *  	<ul><li>Check: Riskitem component label is not presented in quote consolidated view</li></ul></li>
	 * </ol>
	 */
	@Test
	public void componentLabelShouldNotBeDisplayed() {
		// import product
		importTestProductAndActivate(PRODUCT_CD_LABEL_NOT_VISIBLE, PRODUCT_VERSION, PRODUCT_DIR + PRODUCT_CD_LABEL_NOT_VISIBLE + ".zip");

		GeneralPolicyComponentPage policyPage = create(GeneralPolicyComponentPage.class, PRODUCT_CD_LABEL_NOT_VISIBLE);
		policyPage.navigate(new MainApplication(getDriver(), getConfiguration()));
				
		QuoteConsolidatedViewPage quoteConsolidatedViewPage = policyPage
				.openTab(1, "Risk Item")
				.clickSaveAndExit();

		// check RiskItem block Label is presented
		assertFalse("Components header should not bedisplayed " + COMPONENTS_RISKITEM_NS, 
					quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENTS_RISKITEM_NS));
	}
	
	private ProductComponentsConfigurationPage setShowInConsolidatedView(String productCd, boolean status) {
		// select/unselect 'Show Block Label In Consolidated View' and 'Show In Consolidated View' and deploy
		productConsolidatedViewPage = navigateToProductView(productCd, PRODUCT_VERSION)
				.updateProductData()
				.configureComponent(COMPONENTS_RISKITEM_NS)
				.expand()
				.setShowBlockLabelInConsolidatedView(status)
				.setShowInConsolidatedView(status)
				.clickSave()
				.clickSave()
				.deployProduct();
		
		// go to RiskItem configuration page
		componentPage = productConsolidatedViewPage.
				updateProductData()
				.configureComponent(COMPONENTS_RISKITEM_NS)
				.expand();
		
		return componentPage;
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD_LABEL_VISIBLE, PRODUCT_VERSION);
		getProductManager().deleteProduct(PRODUCT_CD_LABEL_NOT_VISIBLE, PRODUCT_VERSION);
	}	
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
