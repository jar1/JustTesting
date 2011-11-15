package com.exigen.ipb.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.dialogs.AttributeAddPopUp;
import com.exigen.ipb.product.pages.modules.AttributeModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that covers component attribute add/remove functionality
 * in alternate component view.
 * 
 * @author mulevicius
 * @since 3.9
 * 
 */
public class AlternativeAddRemoveAttributesWebTests extends AbstractProductSeleniumTests {
	
	private static final String ATTRIBUTE_PRODUCT_CD = "productCd";
	
	private static final String CUSTOMER_NUMBER = "customerNumber";
	
	private static final String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	private static final String PRODUCT_CD = "seleniumProduct";
	private static final Double PRODUCT_VERSION = 1.0D;
	
	private AlternativeProductDataPage altProductDataPage;
	private AttributeModule attributeModule;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
				.selectTreeNode("Policy");
		
		assertEquals("Cannot open Policy component's info view",
				"Policy", altProductDataPage.getComponentInfoModule().getComponentName());
		
		attributeModule = altProductDataPage.getAttributeModule();
		
		// check first if attribute exists before testing
		assertTrue("Policy component do not have expected atribute, testing will not continue",
				attributeModule.existsAttribute(CUSTOMER_NUMBER));
	}
	
	/**
	 * Test case to check if attribute removal works.
	 * Test checks both canceling and saving in expanded test case; 
	 * also verifies if attribute is removed after confirmation 
	 * and if removal was persisted to database after saving in alt view
	 */
	@Test
	public void shouldRemoveAttribute() {
		// save attribute count
		int attributeCount = attributeModule.getAttributeNames().size();
		
		// open remove attribute confirmation popup and cancel
		attributeModule.removeAttribute(CUSTOMER_NUMBER).cancel();
		
		// check that attribute remains
		assertTrue("Attribute was removed after canceling", attributeModule.existsAttribute(CUSTOMER_NUMBER));
		
		// open remove attribute confirmation popup and confirm removal
		altProductDataPage = attributeModule.removeAttribute(CUSTOMER_NUMBER).remove();
		
		// check that attribute is removed
		assertFalse("Attribute was not removed", attributeModule.existsAttribute(CUSTOMER_NUMBER));
		
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count before saving to database",
				attributeCount - 1, attributeModule.getAttributeNames().size());
		
		altProductDataPage.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy");
		
		// check that attribute is removed
		assertFalse("Attribute removal was not saved to database", attributeModule.existsAttribute(CUSTOMER_NUMBER));
		
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count after saving to database",
				attributeCount - 1, attributeModule.getAttributeNames().size());
	}
	
	/**
	 * Test case to check if add new attribute works.
	 * Test checks both canceling and saving in expanded test case; 
	 * also verifies if attribute is inserted to list after confirmation 
	 * and if new attribute was persisted to database after saving in alt view
	 */
	@Test
	public void shouldAddNewAttribute() {
		// save attribute count
		int attributeCount = attributeModule.getAttributeNames().size();
		
		// open remove attribute confirmation popup and cancel
		attributeModule.addNewAttribute().cancel();
		
		// check that attribute remains
		assertTrue("Attribute was removed after canceling", attributeModule.existsAttribute(CUSTOMER_NUMBER));
		
		// confirm before continuing that attribute we want to add does not exist
		assertFalse("Attribute we want to add already exist", attributeModule.existsAttribute("newAttribute"));
		
		boolean response = attributeModule.addNewAttribute()
			.setAttributeLabel("newAttribute")
			.setAttributeName("newAttribute")
			.setDataType("String")
			.clickAdd();
		
		assertTrue("No error messages expected", response);
		
		// check that attribute is added
		assertTrue("Attribute was not added", attributeModule.existsAttribute("newAttribute"));
		
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count before saving new attribute to database",
				attributeCount + 1, attributeModule.getAttributeNames().size());
		
		altProductDataPage.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy");
	
		// check that attribute is removed
		assertTrue("New attribute was not saved to database", attributeModule.existsAttribute("newAttribute"));
	
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count after saving new attribute to database",
				attributeCount + 1, attributeModule.getAttributeNames().size());
		
		// try to add this attribute again - check for unique attribute name
		response = attributeModule.addNewAttribute()
			.setAttributeLabel("newAttribute")
			.setAttributeName("newAttribute")
			.setDataType("String")
			.clickAdd();
		
		assertFalse("No error messages expected", response);
	}
	
	/**
	 * Test case to check if add attribute works.
	 * Test removes two attributes and then adds one of them 
	 * and verifies if changes are displayed before saving
	 * and verifies if changes were saved to db correctly
	 */
	@Test
	public void shouldAddAttribute() {
		// save attribute count
		int attributeCount = attributeModule.getAttributeNames().size();
		
		altProductDataPage = attributeModule.removeAttribute(CUSTOMER_NUMBER).remove().getAttributeModule()
				.removeAttribute(ATTRIBUTE_PRODUCT_CD).remove();
		
		// check that attributes are removed
		assertFalse("Attribute " + CUSTOMER_NUMBER + " was not removed",
				altProductDataPage.getAttributeModule().existsAttribute(CUSTOMER_NUMBER));
		assertFalse("Attribute " + ATTRIBUTE_PRODUCT_CD + " was not removed",
				altProductDataPage.getAttributeModule().existsAttribute(ATTRIBUTE_PRODUCT_CD));
		
		// check that current attribute count in list is exactly two attributes less
		assertEquals("Wrong attribute count before saving to database",
				attributeCount - 2, altProductDataPage.getAttributeModule().getAttributeNames().size());
		
		AttributeAddPopUp popup = attributeModule.addAttribute();
		assertTrue("Removed attribute " + CUSTOMER_NUMBER + " is not in add attribute popup",
				popup.existsAttribute(CUSTOMER_NUMBER));
		assertTrue("Removed attribute " + ATTRIBUTE_PRODUCT_CD + " is not in add attribute popup",
				popup.existsAttribute(ATTRIBUTE_PRODUCT_CD));
		
		altProductDataPage = popup.selectAttribute(ATTRIBUTE_PRODUCT_CD).deselectAttribute(CUSTOMER_NUMBER).save();
		
		// check that attribute productCd was added, while customerNumber was not
		assertFalse("Attribute " + CUSTOMER_NUMBER + " was added before save",
				altProductDataPage.getAttributeModule().existsAttribute(CUSTOMER_NUMBER));
		assertTrue("Attribute " + ATTRIBUTE_PRODUCT_CD + " was not added before save",
				altProductDataPage.getAttributeModule().existsAttribute(ATTRIBUTE_PRODUCT_CD));
		
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count before saving to database",
				attributeCount - 1, altProductDataPage.getAttributeModule().getAttributeNames().size());
		
		altProductDataPage.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy");
		
		// check that attribute productCd was added, while customerNumber was not
		assertFalse("Attribute " + CUSTOMER_NUMBER + " was added after save", 
				altProductDataPage.getAttributeModule().existsAttribute(CUSTOMER_NUMBER));
		assertTrue("Attribute " + ATTRIBUTE_PRODUCT_CD + " was not added after save", 
				altProductDataPage.getAttributeModule().existsAttribute(ATTRIBUTE_PRODUCT_CD));
		
		// check that current attribute count in list is exactly one attribute less
		assertEquals("Wrong attribute count after saving to database", 
				attributeCount - 1, altProductDataPage.getAttributeModule().getAttributeNames().size());
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
