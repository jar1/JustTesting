package com.exigen.ipb.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.modules.ComponentInfoModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 tc for component info properties editing in alternative view
 * @author mulevicius
 * @since 3.9
 */

public class AlternativeComponentInfoEditWebTests extends AbstractProductSeleniumTests {
	
	private AlternativeProductDataPage altProductDataPage;
	
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
				.selectTreeNode("Policy");
	}
	
	/**
	 * Tests default behavior - modifies attributes, saves and then returns to check if saved to db
	 * then removes label expression and summary and saves again
	 */
	@Test
	public void shouldEditComponentProperties() {
		ComponentInfoModule componentInfoModule = altProductDataPage.getComponentInfoModule()
			.setComponentLabel("newLabel")
			.setLabelIsRenderedTo(true)
			.setOptionalQuestion("optionalQuestion")
			.setShowInConsolidatedViewTo(false)
			.configureInstanceLabel()
			.setLabelExpression("labelExpression")
			.ok()
			.getComponentInfoModule()
			.configureInstanceSummary()
			.setSummaryExpression("summaryExpression")
			.ok()
			.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy")
			.getComponentInfoModule();
		
		assertEquals("Component Label should be \"newLabel\"",
				"newLabel", componentInfoModule.getComponentLabel());
		assertEquals("Component Optional Question should be \"optionalQuestion\"",
				"optionalQuestion", componentInfoModule.getOptionalQuestion());
		assertTrue("Label is Rendered checkbox should be selected",
				componentInfoModule.getLabelIsRendered());
		assertFalse("'Show In Cosolidated View' checkbox should not be selected",
				componentInfoModule.getShowInConsolidatedView());
		assertEquals("Label expression should be \"labelExpression\"",
				"labelExpression", componentInfoModule.configureInstanceLabel().getLabelExpression());
		assertEquals("Summary expression should be \"summaryExpression\"",
				"summaryExpression", componentInfoModule.configureInstanceSummary().getSummaryExpression());
	
		componentInfoModule = componentInfoModule.configureInstanceLabel()
			.remove()
			.getComponentInfoModule()
			.configureInstanceSummary()
			.remove()
			.clickSave()
			.updateProductDataAlternative()
			.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
			.selectTreeNode("Policy")
			.getComponentInfoModule();

		assertEquals("Component Label should be \"newLabel\"",
				"newLabel", componentInfoModule.getComponentLabel());
		assertEquals("Component Optional Question should be \"optionalQuestion\"",
				"optionalQuestion", componentInfoModule.getOptionalQuestion());
		assertTrue("Label is Rendered checkbox should be selected",
				componentInfoModule.getLabelIsRendered());
		assertFalse("'Show In Cosolidated View' checkbox should not be selected",
				componentInfoModule.getShowInConsolidatedView());
		assertEquals("Label expression should be EMPTY",
				"", componentInfoModule.configureInstanceLabel().getLabelExpression());
		assertEquals("Summary expression should be EMPTY",
				"", componentInfoModule.configureInstanceSummary().getSummaryExpression());
//		
//		assertEquals("newLabel", componentInfoModule.getComponentLabel());
//		assertEquals("optionalQuestion", componentInfoModule.getOptionalQuestion());
//		assertTrue(componentInfoModule.getLabelIsRendered());
//		assertFalse(componentInfoModule.getShowInConsolidatedView());
//		assertEquals("", componentInfoModule.configureInstanceLabel().getLabelExpression());
//		assertEquals("", componentInfoModule.configureInstanceSummary().getSummaryExpression());
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
