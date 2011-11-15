package com.exigen.ipb.product;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductMergePage;
import com.exigen.ipb.product.pages.dialogs.ProductConfirmMergePopUp;
import com.exigen.ipb.product.pages.modules.ComponentInfoModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that is used to verify correct behavior of component replace
 * in alternate component view.
 * 
 * @author mulevicius
 * @since 3.9
 */
public class AlternativeComponentReplaceWebTests extends AbstractProductSeleniumTests {
	
	private static final String COMPONENT_VEHICLE = "Vehicle";
	private static final String COMPONENT_VEHICLE_1_0 = "Vehicle [1.0]";
	private static final String COMPONENT_RISK_ITEM = "RiskItem";
	private static final String PORT_POLICY_DETAIL_RISK_ITEMS = "policyDetail.riskItems [*]";
	
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRootAndWorkspace.zip";
	
    private AlternativeProductDataPage altProductDataPage;
    private ProductMergePage mergePage;
    
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
        // navigates to imported test product, assigns Policy root and connects RiskItem 1.0, configures workspace
        // saves and returns to alternative view page
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
	        	.updateProductDataAlternative()
	        	.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
        
		// navigate to component risk item tree node
        navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		// open component replace pop up for RiskItem
		ProductConfirmMergePopUp productConfirmMergePopUpPage = altProductDataPage.replace();
		assertNotNull("No replace button found", productConfirmMergePopUpPage);
		
		// choose to diff with Vehicle 1.0
		boolean isDiffComponentSet = productConfirmMergePopUpPage.setComponentNameInOptionBox(COMPONENT_VEHICLE_1_0);
		assertTrue("Cannot start replace web tests as no such component to replace with exists in option box", isDiffComponentSet);
		
		// press replace button so to open merge components page
		mergePage = productConfirmMergePopUpPage.replace();
	}
	
	/**
	 * Tests default behavior. If component replace works correctly when merge is full (checked all).  
	 */
	@Test
	public void shouldReplaceDefault() {
		// do merging with default configuration and return to alternative view page
		mergePage.checkAll();
		altProductDataPage = mergePage.merge().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		
		// navigate to component info
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		// assert that merge changes is displayed before saving (here we check only component's name, better to check everything)
		assertEquals("Replace changes are not displayed before saving", COMPONENT_VEHICLE, altProductDataPage.getComponentInfoModule().getComponentName());
		
		// save and return
		altProductDataPage.clickSave().updateProductDataAlternative().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		// assert that data merged (here we check only component's name, better to check everything)
		assertEquals("Component name was not replaced correctly", COMPONENT_VEHICLE, altProductDataPage.getComponentInfoModule().getComponentName());
	}
	
	/**
	 * Tests if component replace works correctly when no data is merged (unchecked all).  
	 */
	@Test
	public void shouldReplaceUncheckedAll() {
		// cancel merging and return to alternate view
		mergePage.uncheckAll();
		altProductDataPage = mergePage.merge().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		
		// navigate to component info
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		assertThatNothingsChanged();
		
		// save and return
		altProductDataPage.clickSave().updateProductDataAlternative().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		assertThatNothingsChanged();
	}
	
	/**
	 * Tests if component replace can be safely canceled.  
	 */
	@Test
	public void shouldCancelReplace() {
		// cancel merging and return to alternate view
		altProductDataPage = mergePage.cancel().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		
		// navigate to component info
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		assertThatNothingsChanged();

		// save and return
		altProductDataPage.clickSave().updateProductDataAlternative().setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
		navigateToTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS, COMPONENT_RISK_ITEM);
		
		assertThatNothingsChanged();
	}	
	
	private void navigateToTreeNode(String expand, String click) {
		altProductDataPage.expandTreeNode(expand);
		altProductDataPage.selectTreeNode(click);
	}	
	
	private void assertThatNothingsChanged() {
		ComponentInfoModule infoModule = altProductDataPage.getComponentInfoModule();
		assertEquals("Component name was replaced when it shouldn't have",
				COMPONENT_RISK_ITEM, infoModule.getComponentName());
		assertEquals("Component version was replaced when it shouldn't have",
				"1.0", infoModule.getComponentVersion());
		assertEquals("Component min instances was replaced when it shouldn't have",
				"1", infoModule.getComponentMinInstances());
		assertEquals("Component max instances was replaced when it shouldn't have",
				"1", infoModule.getComponentMaxInstances());
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
