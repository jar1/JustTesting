package com.exigen.ipb.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that is used to verify correct behavior of user component creation
 * in alternate component view.
 * 
 * @author mulevicius
 * @since 3.9
 */
public class AlternativeUserComponentCreationWebTests extends AbstractProductSeleniumTests {
	
	private static final String COMPONENT_LABEL = "riskTestLabel";

	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	
	private static final String COMPONENT_NAME = "RiskItem";
	private static final String COMPONENT_VERSION = "1.0";
	private static final String COMPONENT_FULL_NAME;
	
	private static final String PORT_POLICY_DETAIL_RISK_ITEMS = "policyDetail.riskItems [*]";
	
	private static final String NEW_USER_CMP_DESCRIPTION = "riskTestDesc";
	private static final String NEW_USER_CMP_VERSION = "1.0";
	private static final String NEW_USER_CMP_1_0;
	private static final String NEW_USER_CMP_NAME;
	private static final String NEW_USER_CMP_FULL_NAME;
	
	static {
		NEW_USER_CMP_NAME = "riskTest" + System.currentTimeMillis();
		NEW_USER_CMP_FULL_NAME = NEW_USER_CMP_NAME + " " + NEW_USER_CMP_VERSION;
		COMPONENT_FULL_NAME = COMPONENT_NAME + " " + COMPONENT_VERSION;
		NEW_USER_CMP_1_0 = NEW_USER_CMP_NAME + " Component " + NEW_USER_CMP_VERSION;
	}
	
    private AlternativeProductDataPage altProductDataPage;
    
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
        // creates default empty test product
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
	        	.setViewMode(AlternativeProductDataPage.ALL_COMPONENTS_VIEW);

		// navigate to Dwell 2.0 component tree node
        altProductDataPage.selectTreeNode(COMPONENT_FULL_NAME);
        
        assertTrue("Create new button should be displayed",
        		altProductDataPage.isDisplayedCreateNewUserComponentButton());
	}
	
	/**
	 * Tests the expanded default behavior. Verifies component creation, saving and info fields modification.
	 * Also checks if newly created user component is displayed correctly in all components view and ports view (can be assigned).
	 */
	@Test
	public void testSimpleDefaultUserComponentCreation() {
		// creates user component with default name and description
		altProductDataPage.openNewUserComponentPopUp()
			.createUserComponent(NEW_USER_CMP_NAME, NEW_USER_CMP_DESCRIPTION)
			.setViewMode(AlternativeProductDataPage.ALL_COMPONENTS_VIEW);
		
		assertTrue(NEW_USER_CMP_FULL_NAME + " is not displayed",
				altProductDataPage.isTreeNodeDisplayed(NEW_USER_CMP_FULL_NAME));
		
		// navigates to newly created user component
		altProductDataPage.selectTreeNode(NEW_USER_CMP_FULL_NAME);
		
		// asserts that correct values are shown and correct actions are accessible
		assertTrue("Cannot find remove user component button",
				altProductDataPage.isDisplayedRemoveUserComponentButton());
		assertEquals("Created user component name is incorrect", 
				NEW_USER_CMP_NAME, altProductDataPage.getComponentInfoModule().getComponentName());
		assertEquals("Created user component version is incorrect", 
				NEW_USER_CMP_VERSION, altProductDataPage.getComponentInfoModule().getComponentVersion());
		assertNotSame("Created user component label is incorrect",
				COMPONENT_LABEL, altProductDataPage.getComponentInfoModule().getComponentLabel());
		
		// edit some values
		altProductDataPage.getComponentInfoModule().setComponentLabel(COMPONENT_LABEL);
		
		// go to ports view
		altProductDataPage.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS);
		
		// assert that newly created component is in list when 
		// there are NO components of the same type connected
		assertTrue("User created component is not in components list to connect before save",
				altProductDataPage.isComponentDisplayed(NEW_USER_CMP_1_0));
		
		// save and return to alternative view with newly created user component selected
		altProductDataPage.clickSave()
    		.updateProductDataAlternative()
    		.setViewMode(AlternativeProductDataPage.ALL_COMPONENTS_VIEW)
    		.selectTreeNode(NEW_USER_CMP_FULL_NAME);
		
		// assert that data was saved (also with edited values!)
		assertTrue("Cannot find remove user component button",
				altProductDataPage.isDisplayedRemoveUserComponentButton());
		assertEquals("Created user component name is incorrect", 
				NEW_USER_CMP_NAME, altProductDataPage.getComponentInfoModule().getComponentName());
		assertEquals("Created user component version is incorrect", 
				NEW_USER_CMP_VERSION, altProductDataPage.getComponentInfoModule().getComponentVersion());
		assertEquals("Created user component label is incorrect",
				COMPONENT_LABEL, altProductDataPage.getComponentInfoModule().getComponentLabel());
		
		// go to ports view
		altProductDataPage.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode(PORT_POLICY_DETAIL_RISK_ITEMS);
		
		// assert that newly created component is in list when 
		// there are NO components of the same type connected
		assertTrue("User created component is not in components list to connect after save",
				altProductDataPage.isComponentDisplayed(NEW_USER_CMP_1_0));
	}
	
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}
	
	@After
	public void cleanUserCreatedCmp() {
		getComponentService().removeConcreteUserComponent(NEW_USER_CMP_NAME, 1.0);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
