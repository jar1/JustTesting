package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.product.pages.modules.ComponentInfoModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test used to test add/remove buttons visibility data save in 
 * both old and new product data configuration pages
 * 
 * @author mulevicius
 * @since 3.9
 */
public class ProductAddRemoveButtonsVisibilityWebTests extends AbstractProductSeleniumTests {
	
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static Double PRODUCT_VERSION = 1.0D;
	
	private AlternativeProductDataPage alternativeProductDataPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		alternativeProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy");
	}
	
	/**
	 * Tests if product's add/remove buttons are configured and saved correctly to db when saving from alt view
	 */
	@Test
	public void testSaveInAltView() {
		
		ComponentInfoModule componentInfoModule = 
			alternativeProductDataPage.getComponentInfoModule()
			.setAddButtonIsHiddenTo(true)
			.setRemoveButtonIsHiddenTo(false)
			.parent()
			.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy")
			.getComponentInfoModule();
		
		assertTrue("Add button value is incorrect in alt data view", componentInfoModule.getAddButtonHidden());
		assertFalse("Remove button value is incorrect in alt data view", componentInfoModule.getRemoveButtonHidden());
		
		ProductComponentsConfigurationPage configPage = componentInfoModule.parent()
			.cancel()
			.updateProductData()
			.configureComponent(0);
			
		configPage.expand();
		
		assertNotNull("No component found", configPage);
		assertTrue("Add button value is incorrect in old data view", configPage.getAddButtonVisible());
		assertFalse("Add button value is incorrect in old data view", configPage.getRemoveButtonVisible());
	}
	
	/**
	 * Tests if product's add/remove buttons are configured and saved correctly to db when saving from old view
	 */
	@Test
	public void testSaveInOldView() {

		ComponentInfoModule componentInfoModule = 
			alternativeProductDataPage.clickSave()
			.updateProductData()
			.configureComponent(0)
			.expand()
			.setAddButtonIsVisibleTo(true)
			.setRemoveButtonIsVisibleTo(false)
			.clickSave()
			.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy")
			.getComponentInfoModule();

		assertTrue("Add button value is incorrect in alt data view", componentInfoModule.getAddButtonHidden());
		assertFalse("Remove button value is incorrect in alt data view", componentInfoModule.getRemoveButtonHidden());
		
		ProductComponentsConfigurationPage configPage = 
			componentInfoModule.parent()
			.cancel()
			.updateProductData()
			.configureComponent(0)
			.expand();
		
		assertNotNull("No component found", configPage);
		assertTrue("Add button value is incorrect in old data view", configPage.getAddButtonVisible());
		assertFalse("Add button value is incorrect in old data view", configPage.getRemoveButtonVisible());
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
