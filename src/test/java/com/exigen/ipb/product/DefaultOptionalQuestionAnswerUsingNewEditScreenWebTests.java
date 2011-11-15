package com.exigen.ipb.product;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.modules.ComponentInfoModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Web test to cover optional question answer defaulting functionality using new product data update screen
 * 
 * @author sstundzia
 * @since 3.9
 */

public class DefaultOptionalQuestionAnswerUsingNewEditScreenWebTests extends AbstractProductSeleniumTests {
	
	private final static String PRODUCT_CD = "optionalQ";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/optionalQ.zip";
	
	private AlternativeProductDataPage alternativeProductDataPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		alternativeProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW)
				.expandTreeNode("policyDetail.riskItems [*]")
				.selectTreeNode("RiskItem")
				.getComponentInfoModule()
				.setOptionalQuestion("Show Risk Item?")
				.setDefaultOptionalQuestionAnswer("Yes")
				.parent()
				.expandTreeNode("RiskItem")
				.expandTreeNode("coverages [*]")
				.selectTreeNode("Coverage")
				.getComponentInfoModule()
				.setOptionalQuestion("Show Coverage?")
				.setDefaultOptionalQuestionAnswer("No")
				.parent()
				.clickSave()
				.deployProduct()
				.activateProduct()
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW);
	}

	@Test
	public void shoulcCheckSetupDefaultOptionalQuestionUsingNewScreen() {
		ComponentInfoModule riskItemComponentInfo = alternativeProductDataPage.expandTreeNode("policyDetail.riskItems [*]")
				.selectTreeNode("RiskItem")
				.getComponentInfoModule();
		Assert.assertEquals("Optional questions should be presented",
				"Show Risk Item?", riskItemComponentInfo.getOptionalQuestionLabel());
		Assert.assertEquals("Default answer should be YES",
				"Yes", riskItemComponentInfo.getDefaultOptionalQuestionAnswer());
		
		ComponentInfoModule coverageComponentInfo = alternativeProductDataPage.expandTreeNode("RiskItem")
				.expandTreeNode("coverages [*]")
				.selectTreeNode("Coverage")
				.getComponentInfoModule();
		Assert.assertEquals("Optional questions should be presented",
				"Show Coverage?", coverageComponentInfo.getOptionalQuestionLabel());
		Assert.assertEquals("Default answer should be NO",
				"No", coverageComponentInfo.getDefaultOptionalQuestionAnswer());
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
