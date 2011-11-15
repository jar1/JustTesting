package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralCoverageComponentPage;
import com.exigen.ipb.policy.pages.QuoteConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.dialogs.WorkspaceConfigureEntityPopUp;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.MainApplication;

/**
 * Selenium2 test which checks workspace entity view functionality
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.WorkspaceOrder
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */

public class WorkspaceOrderFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD             = "WorkspaceOrder";
	private final static double PRODUCT_VERSION        = 1.0;
	private final static String PRODUCT_DIR            = "target/test-classes/products/workspaceorder.zip";

	private static final String COMPONENT_RISKITEM     = "Risk Item";
	private static final String COMPONENT_RISKITEM_NS  = "RiskItem";
	private static final String COMPONENT_COVERAGE     = "Coverage";
	private static final String COMPONENT_POLICY       = "Policy";
	private static final String COMPONENT_DWELL        = "Dwell";
	
	private static final String DEDUCTIBLE_AMOUNT      = "123";

	private ProductConsolidatedViewPage productConsolidatedPage;
	private WorkspaceConfigureEntityPopUp workspaceEntityPopup;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		productConsolidatedPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * <li>Import product 'WorkspaceOrder'</li>
	 * <li>Change product name, code and dates</li>
	 * 
	 * <li>Go to 'Configure Workspace' page</li>
	 * <li>Open 'Configure Entity View' popup<ul>
	 *  <li>Check: 'Available Components' list has all components</li>
	 *  <li>Check: 'Selected Components'  list has no components</li>
	 * </ul></li>
	 * <li>Add 'Risk Item' component<ul>
	 * 	 <li>Check: 'Available Components' list has all components except 'Risk Item'</li>
	 *   <li>Check: 'Selected Components'  list has only 'Risk Item' component</li>
	 * </ul></li>
	 * <li>Click 'Add All' button<ul>
	 * 	 <li>Check: 'Available Components' list has no components</li>
	 *   <li>Check: 'Selected Components'  list has all components</li>
	 * </ul></li>
	 * <li>Save workspace configuration</li>
	 * 
	 * <li>Go to 'Configure Workspace' page
	 * <li>Open 'Configure Entity View' popup<ul>
	 * 	 <li>Check: 'Available Components' list has no components</li>
	 *   <li>Check: 'Selected Components'  list has all components</li>
	 * </ul></li>
	 * <li>Remove 'Risk Item' component<ul>
	 * 	 <li>Check: 'Available Components' list has only 'Risk Item' component</li>
	 *   <li>Check: 'Selected Components'  list has all components except 'Risk Item'</li>
	 * </ul></li>
	 * <li>Click 'Remove All' button<ul>
	 *   <li>Check: 'Available Components' list has all components</li>
	 *   <li>Check: 'Selected Components'  list has no components</li>
	 * </ul></li>
	 * <li>Save workspace configuration</li>
	 *  
	 * <li>Go to 'Configure Workspace' page</li>
	 * <li>Open 'Configure Entity View' popup</li>
	 * <li>Click 'Add All' button</li>
	 * <li>Select 'Risk Item' and click 'Last'  button</li>
	 * <li>Select 'Policy'    and click 'First' button</li>
	 * <li>Select 'Dwell'     and click 'Up'    button<ul>
	 *   <li>Check: component order is proper</li>
	 * </ul></li>
	 * <li>Save workspace configuration</li>
	 *  
	 * <li>Go to 'Configure Workspace' page</li>
	 * <li>Open 'Configure Entity View' popup<ul>
	 * 	 <li>Check: 'Available Components' list has no components</li>
	 *   <li>Check: 'Selected Components'  list has all components with proper defined order</li>
	 * </ul></li>
	 * <li>Save workspace configuration</li>
	 * <li>Deploy and activate product</li>
	 * 
	 * <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 * <li>Go to 'Quote' creation of newly imported and edited product</li>
	 * <li>Open 'Risk Item' tab</li>
	 * <li>Open 'Dwell'     tab</li>
	 * <li>Open 'Coverage'  tab</li>
	 * <li>Input data in 'Deductible Amount' field</li>
	 * <li>Click 'Save and Exit' top button<ul>
	 *   <li>Check: 'Policy'   is displayed in Quote Consolidated View</li>
	 *   <li>Check: 'Dwell'    is displayed in Quote Consolidated View</li>
	 *   <li>Check: 'Coverage' is displayed in Quote Consolidated View</li>
	 *   <li>Check: 'RiskItem' is displayed in Quote Consolidated View</li>
	 * </ul></li>
	 * </ol>
	 */		
	@Test
	public void reorderingShouldChangeOrderOfComponents() {
		// go to workspace configuration page and open entity configuration popup
		workspaceEntityPopup = openWorkspaceEntityConfigurationPopup(productConsolidatedPage);

		assertTrue("Selected component list should be empty", workspaceEntityPopup.getSelectedComponentsList().isEmpty());
		assertOrder(workspaceEntityPopup.getAvailableComponentsList(),
				COMPONENT_RISKITEM, COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);

		// add Risk Item
		workspaceEntityPopup.addComponent(COMPONENT_RISKITEM);
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(), COMPONENT_RISKITEM);
		assertOrder(workspaceEntityPopup.getAvailableComponentsList(),
				COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);
	
		// add all
		workspaceEntityPopup.addComponentsAll();
		assertTrue("Available component list should ne empty", workspaceEntityPopup.getAvailableComponentsList().isEmpty());
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(),
				COMPONENT_RISKITEM, COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);

		// save workspace configuration
		productConsolidatedPage = workspaceEntityPopup.clickSave().clickSave();
// ---------------------------------
		// go to workspace configuration page and open entity configuration popup
		workspaceEntityPopup = openWorkspaceEntityConfigurationPopup(productConsolidatedPage);

		assertTrue("Available component list should ne empty", workspaceEntityPopup.getAvailableComponentsList().isEmpty());
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(),
				COMPONENT_RISKITEM, COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);
		
		// remove Risk Item
		workspaceEntityPopup.removeComponent(COMPONENT_RISKITEM);
		assertOrder(workspaceEntityPopup.getAvailableComponentsList(), COMPONENT_RISKITEM);
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(),
				COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);
		
		// remove all
		workspaceEntityPopup.removeComponentsAll();
		assertTrue("Selected component list should ne empty", workspaceEntityPopup.getSelectedComponentsList().isEmpty());
		assertOrder(workspaceEntityPopup.getAvailableComponentsList(),
				COMPONENT_RISKITEM, COMPONENT_COVERAGE, COMPONENT_POLICY, COMPONENT_DWELL);
		
		productConsolidatedPage = workspaceEntityPopup.clickSave().clickSave();		
// ---------------------------------
		// go to workspace configuration page and open entity configuration popup
		openWorkspaceEntityConfigurationPopup(productConsolidatedPage)
			.addComponentsAll()
			.setLast(COMPONENT_RISKITEM)
			.setFirst(COMPONENT_POLICY)
			.setUp(COMPONENT_DWELL);
		
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(),
				COMPONENT_POLICY, COMPONENT_DWELL, COMPONENT_COVERAGE, COMPONENT_RISKITEM);
		
		productConsolidatedPage = workspaceEntityPopup.clickSave().clickSave();		
// ---------------------------------
		// go to workspace configuration page and open entity configuration popup
		workspaceEntityPopup = openWorkspaceEntityConfigurationPopup(productConsolidatedPage);

		assertTrue("Available component list should ne empty", workspaceEntityPopup.getAvailableComponentsList().isEmpty());
		assertOrder(workspaceEntityPopup.getSelectedComponentsList(),
				COMPONENT_POLICY, COMPONENT_DWELL, COMPONENT_COVERAGE, COMPONENT_RISKITEM);
		
		// save workspace configuration, deploy and activate product
		workspaceEntityPopup.clickSave().clickSave().deployProduct().activateProduct();
		
// ---------------------------------
		// go to runtime 
		DataGatheringPage dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD);
		dataGatherPage.navigate(new MainApplication(getDriver(), getConfiguration()));

		// open tabs
		dataGatherPage
			.openTab(1, COMPONENT_RISKITEM)
			.openTab(1, COMPONENT_DWELL)
			.openTab(2, COMPONENT_COVERAGE);

		// set deductible amount and save
		QuoteConsolidatedViewPage quoteConsolidatedViewPage =
				create(GeneralCoverageComponentPage.class)
					.setDeductibleAmount(DEDUCTIBLE_AMOUNT)
					.clickSaveAndExit();	

		// check all components labels
		assertTrue("Header of Policy component was not displayed",
				quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENT_POLICY));		
		assertTrue("Header of Dwell component was not displayed",
				quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENT_DWELL));
		assertTrue("Header of Coverage component was not displayed",
				quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENT_COVERAGE));
		assertTrue("Header of RiskItem component was not displayed",
				quoteConsolidatedViewPage.isComponentHeaderDisplayed(COMPONENT_RISKITEM_NS));
	}

	private WorkspaceConfigureEntityPopUp openWorkspaceEntityConfigurationPopup(
			ProductConsolidatedViewPage productPage) {
		
		return productPage.configureWorkspace().clickConfigureEntityView();
	}
	
	private <T> void assertOrder(List<T> list, T... items) {
		Set<T> expectedSet = new HashSet<T>(Arrays.asList(items));
		Set<T> actualSet = new HashSet<T>(list);
		
		assertEquals("Lists are not equal or items have different orders", expectedSet, actualSet);
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
