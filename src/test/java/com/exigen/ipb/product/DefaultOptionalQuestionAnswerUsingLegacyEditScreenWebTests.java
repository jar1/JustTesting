package com.exigen.ipb.product;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.ProductDataPage;
import com.exigen.ipb.product.pages.modules.LegacyComponentInfoModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ComponentWebAction;

/**
 * Web test to cover optional question answer defaulting functionality using legacy product data update screen
 * 
 * @author sstundzia
 * @since 3.9
 */

public class DefaultOptionalQuestionAnswerUsingLegacyEditScreenWebTests extends AbstractProductSeleniumTests {
	
	private final static String PRODUCT_CD = "optionalQ";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/optionalQ.zip";
	
	private ProductDataPage productDataPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		productDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductData()
				.executeActionOnComponent("RiskItem (1.0)", ComponentWebAction.CONFIGURE)
				.getLegacyComponentInfoModule()
				.expandComponentPropertiesPanel()
				.setOptionalQuestion("Show Risk Item?")
				.setDefaultOptionalQuestionAnwser("Yes")
				.save()
				.executeActionOnComponent("Coverage (1.0)", ComponentWebAction.CONFIGURE)
				.getLegacyComponentInfoModule()
				.expandComponentPropertiesPanel()
				.setOptionalQuestion("Show Coverage?")
				.setDefaultOptionalQuestionAnwser("No")
				.save()
				.clickSave()
				.updateProductData();
	}
	
	@Test
	public void shouldCheckSetupDefaultOptionalQuestionUsingLegacyScreen() {
		LegacyComponentInfoModule riskItemLegacyComponentInfoModule = productDataPage.executeActionOnComponent("RiskItem (1.0)", ComponentWebAction.CONFIGURE)
			.getLegacyComponentInfoModule()
			.expandComponentPropertiesPanel();
		
		Assert.assertEquals("Optional questions should be presented",
				"Show Risk Item?", riskItemLegacyComponentInfoModule.getOptionalQuestion());
		Assert.assertEquals("Default answer should be YES",
				"Yes", riskItemLegacyComponentInfoModule.getDefaultOptionalQuestionAnwser());
		
		riskItemLegacyComponentInfoModule.save();
		
		LegacyComponentInfoModule coverageLegacyComponentInfoModule = productDataPage.executeActionOnComponent("Coverage (1.0)", ComponentWebAction.CONFIGURE)
			.getLegacyComponentInfoModule()
			.expandComponentPropertiesPanel();
				
		Assert.assertEquals("Optional questions should be presented",
				"Show Coverage?", coverageLegacyComponentInfoModule.getOptionalQuestion());
		Assert.assertEquals("Default answer should be NO",
				"No", coverageLegacyComponentInfoModule.getDefaultOptionalQuestionAnwser());
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
