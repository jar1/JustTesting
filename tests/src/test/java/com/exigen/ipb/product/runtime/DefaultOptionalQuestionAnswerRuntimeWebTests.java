package com.exigen.ipb.product.runtime;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.components.domain.OptionalQuestionAnswer;
import com.exigen.ipb.policy.pages.DataGatheringPage;
import com.exigen.ipb.policy.pages.GeneralCoverageComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Web test to cover optional question answer defaulting functionality in runtime application
 * 
 * @author sstundzia
 * @since 3.9
 * 
 */
public class DefaultOptionalQuestionAnswerRuntimeWebTests extends AbstractProductSeleniumTests {
	
	private static final String PRODUCT_CD = "optionalQ";
	private static final double PRODUCT_VERSION = 1.0;
	private static final String PRODUCT_DIR = "target/test-classes/products/optionalQ-Runtime.zip";
	private static final int CUSTOMER_NR = 500000;
	
	private DataGatheringPage dataGatherPage;
	private GeneralRiskItemComponentPage riskItemPage;
	private GeneralCoverageComponentPage coveragePage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProductAndActivate(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
	}

	@Test
	public void defaultOptionalQuestionShouldBeDefault() {
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		dataGatherPage.openTab(1, "Other");		
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		assertEquals("Optional Question answer should be " + OptionalQuestionAnswer.Yes,
				OptionalQuestionAnswer.Yes, riskItemPage.getOptionalQuestionAnswer());
		assertTrue("itemName label should be visible", riskItemPage.isItemNameLabelVisible());
		assertEquals("itemName value should not be set", "", riskItemPage.getItemNameValue());
		
		dataGatherPage.openTab(2, "Coverage");		
		coveragePage = create(GeneralCoverageComponentPage.class);
		
		assertEquals("Optional Question answer should be " + OptionalQuestionAnswer.No,
				OptionalQuestionAnswer.No, coveragePage.getOptionalQuestionAnswer());
		assertFalse("LimitAmount label should not be visible", coveragePage.isLimitAmountLabelVisible());
		
		assertNotNull(dataGatherPage.clickSaveAndExit().getQuoteNumber());
	}
	
	@Test
	public void defaultOptionalQuestionNoShouldDeleteComponentInstance() {
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		dataGatherPage.openTab(1, "Other");
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		riskItemPage.setItemName("Test Item");	
		dataGatherPage.clickSave();	
		
		assertEquals("Optional Question should be " + OptionalQuestionAnswer.Yes,
				OptionalQuestionAnswer.Yes, riskItemPage.getOptionalQuestionAnswer());
		assertTrue("itemName label shoudl be visible", riskItemPage.isItemNameLabelVisible());
		assertEquals("itemName value should be Test Item", "Test Item", riskItemPage.getItemNameValue());
		
		riskItemPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.No);
		
		assertEquals("Optional Question answer should be " + OptionalQuestionAnswer.No,
				OptionalQuestionAnswer.No, riskItemPage.getOptionalQuestionAnswer());
		assertFalse("itemName label should not be visible", riskItemPage.isItemNameLabelVisible());
		
		assertNotNull(dataGatherPage.clickSaveAndExit().getQuoteNumber());
	}
	
	@Test
	public void defaultOptionalQuestionYesShouldAddComponentInstance() {
		dataGatherPage = create(DataGatheringPage.class, PRODUCT_CD, CUSTOMER_NR);
		dataGatherPage.navigate(getApplication());
		
		dataGatherPage.openTab(1, "Other");	
		riskItemPage = create(GeneralRiskItemComponentPage.class);
		
		riskItemPage.setItemName("Test Item");	
		riskItemPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.No);
		dataGatherPage.clickSave();	
		
		assertEquals("Optional Question answer should be " + OptionalQuestionAnswer.No,
				OptionalQuestionAnswer.No, riskItemPage.getOptionalQuestionAnswer());
		assertFalse("itemName label should not be visible", riskItemPage.isItemNameLabelVisible());
		
		riskItemPage.setOptionalQuestionAnswer(OptionalQuestionAnswer.Yes);
		
		assertEquals("Optional Question answer should be " + OptionalQuestionAnswer.Yes,
				OptionalQuestionAnswer.Yes, riskItemPage.getOptionalQuestionAnswer());
		assertTrue("itemName label should be  visible", riskItemPage.isItemNameLabelVisible());
		assertEquals("itemName value should not be set", "", riskItemPage.getItemNameValue());
		
		riskItemPage.openTab(2, "Coverage");
		coveragePage = create(GeneralCoverageComponentPage.class);
		
		assertEquals("Optional Question answer is not " + OptionalQuestionAnswer.No,
				OptionalQuestionAnswer.No, coveragePage.getOptionalQuestionAnswer());
		assertFalse("LimitAmount label is visible", coveragePage.isLimitAmountLabelVisible());
		
		coveragePage.setOptionalQuestionAnswer(OptionalQuestionAnswer.Yes);
		
		assertEquals("Optional Question answer shoudl be " + OptionalQuestionAnswer.Yes,
				OptionalQuestionAnswer.Yes, coveragePage.getOptionalQuestionAnswer());
		assertTrue("LimitAmount label should be visible", coveragePage.isLimitAmountLabelVisible());
		assertEquals("LimitAmount value should not be set", "", coveragePage.getLimitAmountValue());
		
		coveragePage.setMandatoryCoverageCd("test");
		
		assertNotNull(dataGatherPage.clickSaveAndExit().getQuoteNumber());
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(PRODUCT_CD, PRODUCT_VERSION);
	}	
	
	@Override
	public Application setUpApplication() {
		return new MainApplication(getDriver(), getConfiguration());
	}	
}
