package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductWorkspacePage;

import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.MainApplication;

/**
 * Selenium2 test which checks workspace functionality
 * 
 * @category FitNesse
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.WorkspaceTest
 *         EisTestCases.TestSuite.ProductSuite.WorkspaceNavigation
 * 
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class WorkspaceTestFitnesseWebTests extends AbstractProductSeleniumTests {	
	
	private final static String PRODUCT_CD                    = "breadcrumb";
	private final static double PRODUCT_VERSION               = 1.0D;
	private final static String PRODUCT_DIR                   = "target/test-classes/products/breadcrumb.zip";	
	
	private static final String PRODUCT_NAME_CD               = "ws"+System.currentTimeMillis();
	
	private static final String TABS_POLICY                   = "Policy";
	private static final String TABS_RISKITEM                 = "Risk Item";
	private static final String TABS_COVERAGE                 = "coverage";
	private static final String TABS_DWELL                    = "dwell";
	private static final String TABS_DWELL_RENAMED            = "DwellRanamed";
	private static final String TABS_NEW_1STTAB               = "1stTab";
	
	private static final String ACTIONS_DATA_GATHER           = "Data Gather [dataGather]";
	private static final String ACTIONS_INQUIRY               = "Inquiry [quoteInquiry]";
	private static final String ACTIONS_ISSUE                 = "Issue [issue]";

	private static final String WORKSPACE_DEFAULT_NAME        = "default";
	private static final String WORKSPACE_NEW_NAME            = "2nd";
	private static final String WORKSPACE_COPY_NAME           = "Copy";
	
	private static final String WORKSPACE_STYLE_TAB           = "tab";
	private static final String WORKSPACE_STYLE_WIZARD        = "wizard";
	
	private static final List<String> WORKSPACE_STYLE_LIST    = Arrays.asList("Tab Style", "Wizard Style", "Tree Style");

	private ProductWorkspacePage workspacePage;
	private ProductConsolidatedViewPage productConsolidatedViewPage;
	private DataGatheringPage dataGatherPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		// import product
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		// WARNING: Due to caching issues the following instructions are needed to be performed.
		productConsolidatedViewPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductProperties()
				.enterProductCode(PRODUCT_NAME_CD)
				.enterProductName(PRODUCT_NAME_CD)
				.clickNext();
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'breadcrumb' from 'target/test-classes/products/breadcrumb.zip'</li>
	 *  <li>Change product name, code and dates</li>
	 *  <li>Go to 'Configure Workspace' page</li>
	 *  <li>Remove tab
	 *   	<ul><li>Check: tab removed</li></ul></li>
	 *  <li>Rename tab
	 *   	<ul><li>Check: tab renamed</li></ul></li>
	 *  <li>Assign actions to workspace
	 *   	<ul><li>Check: actions assigned successfully</li></ul></li>
	 *  <li>Create new workspace and save configuration</li>
	 *  <li>Go to 'Configure Workspace' page
	 *   	<ul><li>Check: new workspace is available</li></ul></li>
	 *  <li>Select new workspace
	 *   	<ul><li>Check: new workspace selected</li></ul></li>
	 *  <li>Add tab to new workspace
	 *   	<ul><li>Check: tab added</li></ul></li>
	 *  <li>Assign actions to new workspace
	 *   	<ul><li>Check: actions assigned successfully</li></ul></li>
	 *  <li>Go back to default workspace
	 *   	<ul><li>Check: assigned actions changed</li>
	 *  		<li>Check: 'Workspace Style' drop down options</li>
	 *  	 	<li>Check: 'Style Sheet' drop down options</li></ul></li>
	 *  <li>Copy workspace
	 *   	<ul><li>Check: workspace copied successfully</li></ul></li>
	 *  <li>Go to new workspace and delete it
	 * 		<ul><li>Check: new workspace deleted successfully</li></ul></li>
	 *  <li>Save workspace configuration
	 * 		<ul><li>Check: configuration saved without any errors</li></ul></li>
	 *  </ol>
	 *  
	 */		
	@Test	
	public void shouldRenameRemoveAddCopyWorkspaceAndTabs() {
		// go to workspace configuration
		workspacePage = productConsolidatedViewPage.configureWorkspace();
				
		// remove tab
		workspacePage.removeTab(TABS_COVERAGE);
		assertFalse(TABS_COVERAGE + " should be removed", workspacePage.isTreeNodeExists(TABS_COVERAGE));
		
		// rename tab
		workspacePage.renameTab(TABS_DWELL, TABS_DWELL_RENAMED);
		assertTrue(TABS_DWELL + " should be renamed to " + TABS_DWELL_RENAMED,				
				workspacePage.isTreeNodeExists(TABS_DWELL_RENAMED) && !workspacePage.isTreeNodeExists(TABS_DWELL));
		
		// assign actions to workspace
		workspacePage.openAssignToPopUp()
				.assignTo(ACTIONS_DATA_GATHER)
				.assignTo(ACTIONS_INQUIRY)
				.assignTo(ACTIONS_ISSUE)
				.clickSave();		
		assertEquals("Different assigned actions expected",
				ACTIONS_DATA_GATHER + ", " + ACTIONS_INQUIRY + ", " + ACTIONS_ISSUE,
				workspacePage.getAssignedActions());

		// create new workspace and save
		workspacePage.createWorkspace(WORKSPACE_NEW_NAME).clickSave().configureWorkspace();			
		assertEquals(WORKSPACE_NEW_NAME +" should be created",
				Arrays.asList(WORKSPACE_DEFAULT_NAME, WORKSPACE_NEW_NAME),
				workspacePage.getWorkspaceList());
		
		// select new workspace and add tab to new workspace
		workspacePage.selectWorkspace(WORKSPACE_NEW_NAME).clickTreeNode(PRODUCT_NAME_CD).addTab(TABS_NEW_1STTAB);
		assertTrue(TABS_NEW_1STTAB + " should be added", workspacePage.isTreeNodeExists(TABS_NEW_1STTAB));
		
		// assign actions to new workspace
		workspacePage.openAssignToPopUp()
				.assignTo(ACTIONS_DATA_GATHER)
				.assignTo(ACTIONS_INQUIRY)
				.clickSave();		
		assertEquals("Different assigned actions expected",
				ACTIONS_DATA_GATHER + ", " + ACTIONS_INQUIRY,
				workspacePage.getAssignedActions());
		
		// go back to default workspace and check assigned actions
		workspacePage.selectWorkspace(WORKSPACE_DEFAULT_NAME);
		assertEquals("Different assigned actions expected",
				ACTIONS_ISSUE,	workspacePage.getAssignedActions());

		// check 'Workspace Style' and 'Style Sheet' drop down options
		assertEquals("Workspace style dropdown should be " + WORKSPACE_STYLE_LIST,
				WORKSPACE_STYLE_LIST, workspacePage.getWorkspaceStyleList());
		
		// copy workspace
		workspacePage.copyWorkscpace(WORKSPACE_COPY_NAME);
		assertEquals(WORKSPACE_COPY_NAME + " should be created",
				Arrays.asList(WORKSPACE_DEFAULT_NAME, WORKSPACE_NEW_NAME, WORKSPACE_COPY_NAME),
				workspacePage.getWorkspaceList());
		
		// delete new workspace
		workspacePage.selectWorkspace(WORKSPACE_NEW_NAME).removeWorkspace();
		assertEquals(WORKSPACE_NEW_NAME + " should be deleted",
				Arrays.asList(WORKSPACE_DEFAULT_NAME, WORKSPACE_COPY_NAME),
				workspacePage.getWorkspaceList());			

		// save configuration
		assertTrue("Product code should be " + PRODUCT_NAME_CD,
				workspacePage.clickSave().existsProduct(PRODUCT_NAME_CD));
	}
	
	/**
	 * Test Case:
	 * <ol>
	 *  <li>Import product 'breadcrumb' from 'target/test-classes/products/breadcrumb.zip'</li>
	 *  <li>Change product name, code and dates</li>
	 * 
	 *  <li>Go to 'Configure Workspace' page</li>
	 *  <li>Change Workspace Style to 'Wizard Style'</li>
	 *  <li>Deploy and activate product</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of newly imported product
	 *  	<ul><li>Check: tabs panel is not presented</li>
	 *  		<li>Check: next button is presented</li></ul></li>
	 *  
	 *  <li>Go to admin app, open product and go to 'Configure Workspace' page</li>
	 *  <li>Select 'Policy' tab and select Milestone check-box</li>
	 *  <li>Deploy and activate product</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of newly imported product
	 *  	<ul><li>Check: tabs panel is not presented</li>
	 *  		<li>Check: next button is not presented</li></ul></li>
	 *  
	 *  <li>Go to admin app, open product and go to 'Configure Workspace' page</li>
	 *  <li>Delete all tabs except 'Policy'</li>
	 *  <li>Select 'Wizard Style'</li>
	 *  <li>Select 'Policy' tab and uncheck Milestone check-box</li>
	 *  <li>Deploy and activate product</li>
	 *  <li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 *  <li>Go to 'Quote' creation of newly imported product
	 * 	<ul><li>Check: tabs panel is presented</li>
	 *  	<li>Check: next button is not presented</li></ul></li>
	 * </ol>
	 *  
	 */		
	@Test
	public void workspaceStylesShouldChangeDataGatherWorkflow() {
		// change style Wizard and activate
		navigateToProductView(PRODUCT_NAME_CD, PRODUCT_VERSION)
				.configureWorkspace()
				.selectWorkspaceStyle(WORKSPACE_STYLE_WIZARD)
				.clickSave()
				.deployProduct()
				.activateProduct();
		
		// runtime checking
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_NAME_CD);
		dataGatherPage.navigate(new MainApplication(getDriver(), getConfiguration()));
		
		// check tabs panel is not presented, next button is presented
		assertFalse("Tabs should not be dispalyed", dataGatherPage.isTabsDispalayed());
		assertTrue("'Next' should be displayed", dataGatherPage.isNextDispalayed());
// ------------------------------		
		// open imported product and select Milestone on Policy tab
		navigateToProductView(PRODUCT_NAME_CD, PRODUCT_VERSION)
				.deactivateProduct()
				.configureWorkspace()
				.clickTreeNode(TABS_POLICY)
				.selectMilestone(true)
				.clickSave()
				.deployProduct()
				.activateProduct();

		// runtime checking
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_NAME_CD);
		dataGatherPage.navigate(new MainApplication(getDriver(), getConfiguration()));
		
		// check tabs panel is not presented, next button is not presented
		assertFalse("Tabs should not be dispalyed", dataGatherPage.isTabsDispalayed());
		assertFalse("'Next' should not be  displayed", dataGatherPage.isNextDispalayed());
// ------------------------------	
		// open imported product, remove tabs, change tab style back to tab and uncheck milestone
		navigateToProductView(PRODUCT_NAME_CD, PRODUCT_VERSION)
				.deactivateProduct()
				.configureWorkspace()
				.removeTab(TABS_COVERAGE)
				.removeTab(TABS_DWELL)
				.removeTab(TABS_RISKITEM)
				.selectWorkspaceStyle(WORKSPACE_STYLE_TAB)
				.clickTreeNode(TABS_POLICY)
				.selectMilestone(false)
				.clickSave()
				.deployProduct()
				.activateProduct();
		
		// runtime checking
		// go to quote creation page		
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_NAME_CD);
		dataGatherPage.navigate(new MainApplication(getDriver(), getConfiguration()));
		
		// check tabs panel is presented, next button is not presented
		assertTrue("Tabs shoulf be displayed", dataGatherPage.isTabsDispalayed());
		assertFalse("'Next' should not be displayed", dataGatherPage.isNextDispalayed());
	}

	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
		getProductManager().deleteProduct(PRODUCT_NAME_CD, PRODUCT_VERSION);
	}
}
