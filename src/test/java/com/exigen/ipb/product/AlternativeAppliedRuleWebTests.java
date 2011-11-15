package com.exigen.ipb.product;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.dialogs.AppliedRuleDialog;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test used to verify component applicability rule dialog behavior
 * in alternate component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AlternativeAppliedRuleWebTests extends AbstractProductSeleniumTests {

	private static final String CONDITION_EXPRESSION = "true";
	
	private static final String DESCRIPTION = "testDescription";
	
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
	 * Tests whether component applicability rule is saved.
	 */
	@Test
	public void shouldSave() {
		AppliedRuleDialog dialog = editAppliedRule();
		assertTrue("Applicability Rule dialog is not displayed", dialog.isDisplayed());
		assertEquals("Current component should be Policy",
				"Policy", dialog.getCurrentComponentName());
		
		//int historySize = getHistorySize();
		applyRule(dialog, CONDITION_EXPRESSION, DESCRIPTION);
		dialog.clickSave();
		
		altProductDataPage.clickSave()
			.updateProductDataAlternative()
			.selectTreeNode("Policy");
		
		editAppliedRule();
		
		assertEquals("Condition Expression was not saved",
				CONDITION_EXPRESSION, dialog.getConditionExpression());
		assertEquals("Description was not saved",
				DESCRIPTION, dialog.getDescription());
	}
	
	/**
	 * Tests whether component applicability rule is removed.
	 */
	@Test
	public void shouldRemove() {
		AppliedRuleDialog dialog = editAppliedRule();
		applyRule(dialog, CONDITION_EXPRESSION, DESCRIPTION);
		dialog.clickSave();
		
		altProductDataPage.clickSave().updateProductDataAlternative();
		editAppliedRule();
		
		dialog.remove();
		
		altProductDataPage.clickSave().updateProductDataAlternative();
		editAppliedRule();
		
		assertTrue("Condition Expression was not cleared", dialog.getConditionExpression().isEmpty());
		assertTrue("Description was not cleared", dialog.getDescription().isEmpty());
	}

	/**
	 * Tests whether changes to component applicability rule are not saved when
	 * canceled.
	 */
	@Test
	public void shouldCancel() {
		AppliedRuleDialog dialog = editAppliedRule();
		applyRule(dialog, CONDITION_EXPRESSION, DESCRIPTION);
		dialog.clickSave();
		
		altProductDataPage.clickSave().updateProductDataAlternative();
		editAppliedRule();
		
		applyRule(dialog, CONDITION_EXPRESSION + " == true", DESCRIPTION + "Cancellation");
		
		dialog.clickCancel();
		
		altProductDataPage.clickSave().updateProductDataAlternative();
		editAppliedRule();
		
		assertEquals("Condition Expression was not supposed to be saved",
				CONDITION_EXPRESSION, dialog.getConditionExpression());
		assertEquals("Condition Expression was not supposed to be saved",
				DESCRIPTION, dialog.getDescription());
	}
	
	private AppliedRuleDialog editAppliedRule() {
		return altProductDataPage.getComponentInfoModule().configureAppliedRule();
	}
	
	private void applyRule(AppliedRuleDialog dialog, String conditionExpression, String description) {
		dialog.setConditionExpression(conditionExpression);
		dialog.setDescription(description);
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
