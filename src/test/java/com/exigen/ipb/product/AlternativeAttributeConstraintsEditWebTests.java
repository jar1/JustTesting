package com.exigen.ipb.product;

import org.junit.After;
import org.junit.Test;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;

import com.exigen.ipb.components.domain.AttributeConstraints;
import com.exigen.ipb.components.domain.WordingCase;
import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.dialogs.AttributeConstraintsDialog;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that covers Component Attribute Constraints editing dialog
 * in alternate component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AlternativeAttributeConstraintsEditWebTests extends AbstractProductSeleniumTests {

	private static final String ATTRIBUTE_NAME = "policyStatusCd";
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static Double PRODUCT_VERSION = 1.0D;
	private AlternativeProductDataPage altProductDataPage;

	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.selectTreeNode("Policy");
	}
	
	/**
	 * Tests whether entered values are saved and attribute is updated.
	 */
	@Test
	public void shouldSaveConstraints() {
		AttributeConstraintsDialog dialog =
				openConstraintsDialog(altProductDataPage, ATTRIBUTE_NAME);
		
		assertTrue("Dialog is not displayed", dialog.isDialogDisplayed());
		
		AttributeConstraints constraints = extractConstraints(dialog);
		constraints.setMandatoryInd(true);
		constraints.setDisabledInd(true);
		constraints.setDefaultValue("defaultValue");
		constraints.setRegExp("\\w+");
		constraints.setWordingCase(WordingCase.Camel);
		constraints.setValuesRangeFrom(0);
		constraints.setValuesRangeTo(1);
		constraints.setMinLength(2);
		constraints.setMaxLength(3);
		
		injectConstraints(dialog, constraints);

		// Save both dialog values and component, and navigate back to dialog.
		dialog = openConstraintsDialog(
				dialog.save().clickSave().updateProductDataAlternative(),
				ATTRIBUTE_NAME);
		
		assertValueEquals(dialog, constraints);
	}
	
	/**
	 * Tests whether command message is generated after attribute constraints
	 * dialog is closed.
	 */
	@Test
	public void shouldCreateCommandMessage() {
		int historySize = altProductDataPage.getCommandsHistoryModule().getHistorySize();
		
		AttributeConstraintsDialog dialog =
				openConstraintsDialog(altProductDataPage, ATTRIBUTE_NAME);
		
		AttributeConstraints constraints = extractConstraints(dialog);
		constraints.setDefaultValue(constraints.getDefaultValue() + "Modified");
		
		altProductDataPage = dialog.save();
		int updateHistorySize = altProductDataPage.getCommandsHistoryModule().getHistorySize();
		assertEquals("No command message was displayed", historySize + 1, updateHistorySize);
	}
	
	/**
	 * Tests whether entered values are not saved when canceled.
	 */
	@Test
	public void shouldCancelConstraints() {
		AttributeConstraintsDialog dialog =
				openConstraintsDialog(altProductDataPage, ATTRIBUTE_NAME);
		
		AttributeConstraints constraints = extractConstraints(dialog);

		dialog.setDefaultValue("shouldBeNotSaved");
		dialog.setMandatory(true);
		dialog.setValuesRangeFrom(100);
		dialog.setMinLength(100);
		
		altProductDataPage = dialog.cancel();
		assertFalse("Dialog is should be closed", dialog.isDialogDisplayed());
		dialog = openConstraintsDialog(altProductDataPage, ATTRIBUTE_NAME);
		
		assertValueEquals(dialog, constraints);		
	}

	/**
	 * Opens Component Attribute Constraints editing dialog.
	 * 
	 * @param page  Instance of alternate product data page.
	 * @param attributeName  The name of attribute which constraints will be edited.
	 * @return  Opened constraints dialog.
	 */
	private AttributeConstraintsDialog openConstraintsDialog(
			AlternativeProductDataPage page, String attributeName) {
		
		return page.getAttributeModule().editAttributeConstraints(attributeName);
	}
	
	/**
	 * Fills dialog form fields with values of component attribute constraints.
	 * 
	 * @param dialog  Constraints dialog.
	 * @param constraints  Constraints to be filled with.
	 */
	private void injectConstraints(AttributeConstraintsDialog dialog, AttributeConstraints constraints) {
		dialog.setMandatory(constraints.getMandatoryInd());
		dialog.setDisabled(constraints.getDisabledInd());
		
		dialog.setDefaultValue(constraints.getDefaultValue());
		dialog.setRegExp(constraints.getRegExp());
		dialog.setWordingCase(constraints.getWordingCase());
		
		dialog.setValuesRangeFrom(constraints.getValuesRangeFrom());
		dialog.setValuesRangeTo(constraints.getValuesRangeTo());
		
		dialog.setMinLength(constraints.getMinLength());
		dialog.setMaxLength(constraints.getMaxLength());
	}
	
	/**
	 * Creates attribute constraints from values of dialog form fields.
	 * 
	 * @param dialog  Constraints dialog.
	 * @return  Extracted attribute constraints.
	 */
	private AttributeConstraints extractConstraints(AttributeConstraintsDialog dialog) {
		AttributeConstraints constraints = new AttributeConstraints();
		constraints.setMandatoryInd(dialog.isMandatory());
		constraints.setDisabledInd(dialog.isDisabled());
		
		constraints.setDefaultValue(dialog.getDefaultValue());
		constraints.setRegExp(dialog.getRegExp());
		constraints.setWordingCase(dialog.getWordingCase());
		
		constraints.setValuesRangeFrom(numberize(dialog.getValuesRangeFrom()));
		constraints.setValuesRangeTo(numberize(dialog.getValuesRangeTo()));
		
		constraints.setMinLength(numberize(dialog.getMinLength()));
		constraints.setMaxLength(numberize(dialog.getMaxLength()));
		
		return constraints;
	}
	
	/**
	 * Verifies whether values of form fields and attribute constraints are equal.
	 * 
	 * @param dialog  Constraints dialog.
	 * @param constraints  Constraints to be compared to.
	 */
	private void assertValueEquals(AttributeConstraintsDialog dialog, AttributeConstraints constraints) {
		// Checkboxes.
		assertTrue("Mandatory Indicators do not match", dialog.isMandatory() == constraints.getMandatoryInd());
		assertTrue("Disabled Indicators do not match", dialog.isDisabled() == constraints.getDisabledInd());
		
		// General part.
		assertEquals("Default Values do not match", dialog.getDefaultValue(), constraints.getDefaultValue());
		assertEquals("Regular Expressions do not match", dialog.getRegExp(), constraints.getRegExp());
		assertEquals("Wording Cases do not match", dialog.getWordingCase(), constraints.getWordingCase());
		
		// Value Range.
		assertEquals("Values Range From do not match", numberize(dialog.getValuesRangeFrom()), constraints.getValuesRangeFrom());
		assertEquals("Values Range To do not match", numberize(dialog.getValuesRangeTo()), constraints.getValuesRangeTo());
		
		// Lengths.
		assertEquals("Minimum Lengths do not match", numberize(dialog.getMinLength()), constraints.getMinLength());
		assertEquals("Maximum Lengths do not match", numberize(dialog.getMaxLength()), constraints.getMaxLength());
	}
	
	private Integer numberize(String str) {
		return StringUtils.hasText(str) ? Integer.valueOf(str) : null;
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