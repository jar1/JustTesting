package com.exigen.ipb.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.dialogs.AttributeGeneralPropertiesEditPopUp;
import com.exigen.ipb.product.pages.modules.AttributeModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 tests that verifies general attribute properties edit popup behavior in alternative view
 *
 * @author mulevicius
 * @since 3.9
 */
public class AlternativeAttributePropertiesEditWebTests extends AbstractProductSeleniumTests {
	
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	private final static String PRODUCT_NAME = "seleniumProduct";
	private final static Double PRODUCT_VERSION = 1.0D;
	
	private AttributeGeneralPropertiesEditPopUp popup;
	private AlternativeProductDataPage altPage;
	private int historySize = 0;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_NAME, PRODUCT_VERSION, PRODUCT_DIR);
		
		popup = navigateToProductView(PRODUCT_NAME, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
				.selectTreeNode("Policy")
				.getAttributeModule()
				.editAttributeProperties("customerNumber");
		
		assertEquals("General attribute properties edit popup was not opened", "Attribute customerNumber", popup.getAttributeName());
		
		// set everything to default values
		setDefault(popup);
		
		// save and return to popup for test cases
		altPage = popup.save();
	}
	
	/**
	 * Test case that verifies saving to database.
	 */
	@Test
	public void testDefaultBehaviorSaveToDbAttributes() {
		popup = altPage.getAttributeModule().editAttributeProperties("customerNumber");
		
		// assert that values are correct
		assertDefault(popup);
		
		// set different values
		setModified(popup);
		
		// save in popup and save to db (in alt view) and then return to popup
		ProductConsolidatedViewPage cview = popup.save().clickSave();
		AttributeModule a = cview.updateProductDataAlternative().getAttributeModule();
		popup = a.editAttributeProperties("customerNumber");

		// assert that values saved to db and no command messages displayed
		assertModified(popup);
		assertCommandsHistory(0);
	}

	/**
	 * Test case that verifies editing attributes
	 */
	@Test
	public void testDefaultBehaviorSaveAttributes() {
		historySize = altPage.getCommandsHistoryModule().getHistorySize();
		
		popup = altPage.getAttributeModule().editAttributeProperties("customerNumber");
		
		// assert that values are correct
		assertDefault(popup);
		
		// set different values
		setModified(popup);
		
		// save in popup and save to db (in alt view) and then return to popup
		
		AttributeModule a = popup.save().getAttributeModule();
		popup = a.editAttributeProperties("customerNumber");

		// assert that values were edited
		assertModified(popup);
		assertCommandsHistory(historySize+1);
	}
	
	/**
	 * Test case that verifies canceling behavior.
	 */
	@Test
	public void testDefaultBehaviorCancelSaveAttributes() {
		historySize = altPage.getCommandsHistoryModule().getHistorySize();
		popup = altPage.getAttributeModule().editAttributeProperties("customerNumber");
		
		// assert that values are correct
		assertDefault(popup);
		
		// set different values
		setModified(popup);
		
		// cancel and return to popup
		AttributeModule a = popup.cancel().getAttributeModule();
		popup = a.editAttributeProperties("customerNumber");
		
		// assert that nothings changed
		assertDefault(popup);
		assertCommandsHistory(historySize);
	}

	private void assertCommandsHistory(int expectedHistorySizeIncrease) {
		assertEquals("Command message was not displayed",
				expectedHistorySizeIncrease, altPage.getCommandsHistoryModule().getHistorySize());
	}
	
	private void setDefault(AttributeGeneralPropertiesEditPopUp popup) {
		popup.setComparableTo(true);
		popup.setDisplayableTo(true);
		popup.setDisplayInConsolidatedTo(false);
		popup.setSerializableTo(false);
		popup.setRatingTo(false);
		popup.setLabel("myLabel");
		popup.setXPath("thisIsXPATH");
		popup.setHelpText("This is a help text");
	}
	
	private void setModified(AttributeGeneralPropertiesEditPopUp popup) {
		popup.setComparableTo(false);
		popup.setDisplayableTo(false);
		popup.setDisplayInConsolidatedTo(true);
		popup.setSerializableTo(true);
		popup.setRatingTo(true);
		popup.setLabel("myAnotherLabel");
		popup.setXPath("thisIsAnotherXPATH");
		popup.setHelpText("This is another help text");
	}
	
	private void assertModified(AttributeGeneralPropertiesEditPopUp popup) {
		assertFalse("Comparable should not be selected", popup.getIsComparable());
		assertFalse("Displayable should not be selected", popup.getIsDisplayable());
		assertTrue("Display in Consolidated View should be selected", popup.getIsDisplayInConsolidated());
		assertTrue("Is Serializabl Comparable should be selected", popup.getIsSerializable());
		assertTrue("Is Rating should be selected", popup.getIsRating());		
		assertEquals("Help text should be changed", "This is another help text", popup.getHelpText());
		assertEquals("Label should be changed", "myAnotherLabel", popup.getLabelText());
		assertEquals("XPath should be changed", "thisIsAnotherXPATH", popup.getXPathText());
	}
	
	private void assertDefault(AttributeGeneralPropertiesEditPopUp popup) {
		assertTrue("Comparable should be selected", popup.getIsComparable());
		assertTrue("Displayable should be selected", popup.getIsDisplayable());
		assertFalse("Display in Consolidated View should not be selected", popup.getIsDisplayInConsolidated());
		assertFalse("Is Serializabl Comparable should not be selected", popup.getIsSerializable());
		assertFalse("Is Rating should not be selected", popup.getIsRating());
		assertEquals("Help text should be " + "This is a help text", "This is a help text", popup.getHelpText());
		assertEquals("Label should be " + "myLabel", "myLabel", popup.getLabelText());
		assertEquals("XPath should be " + "thisIsXPATH", "thisIsXPATH", popup.getXPathText());
	}	
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_NAME, PRODUCT_VERSION);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}

}
