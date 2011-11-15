package com.exigen.ipb.product.runtime;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.policy.pages.GeneralPolicyComponentPage;
import com.exigen.ipb.policy.pages.GeneralRiskItemComponentPage;
import com.exigen.ipb.policy.pages.QuoteConsolidatedViewPage;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.MainApplication;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.ProductImportInfo;

/**
 * @category FitNesse
 * 
 * Selenium2 PF smoke test which checks rules override functionality
 * 
 * @author jdaskevicius
 * 
 * Covers: EisTestCases.SmokeSuite.ProductSmoke.RuleOverride
 * 
 * @since 3.9
 */
public class RulesOverrideRuntimeFitnesseSmokeWebTests extends AbstractProductSeleniumTests {

	private static final ProductImportInfo productImportInfo = new ProductImportInfo("ruleOverrideSmoke");
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		importTestProductAndActivate(productImportInfo);
	}
	
	/**
	 * Test Case:
	 * <ol>
	 * 	<li>Go to runtime app and choose customer with 'Customer Number' = 500000</li>
	 * 	<li>Go to 'Quote' creation of newly created product</li>
	 * 	<li>Open 'Risk Item' tab</li>
	 * 	<li>In 'Test1' field input 'B' value and click 'Save'</li>
	 * 	<li>Click 'Override' button</li>
	 * 	<li>Select Override check-box, 'Term' option and reason and click 'Next' button</li>
	 * 	<li>Click 'Next' button 
	 * 		<ul><li>Check: Quote created successfully</li></ul></li>
	 * </ol>
	 */
	@Test
	public void shouldOverrideRuleInRuntime() {
		// go to quote creation page
		GeneralPolicyComponentPage generalPolicyComponentPage = 
				create(GeneralPolicyComponentPage.class, productImportInfo.getProductCd());
		
		generalPolicyComponentPage.navigate(new MainApplication(getDriver(), getConfiguration()));
		
		// open Risk Item tab
		generalPolicyComponentPage.openTab(1, "Risk Item");
		GeneralRiskItemComponentPage generalRiskItemComponentPage = create(GeneralRiskItemComponentPage.class);
		
		// fill in values
		generalRiskItemComponentPage.setAdditionalInterestExists(true)
			.setTest1("B")
			.clickSave()
			.clickOverride()
			.selectOverrideRule()
			.save();
		
		// save quote after override
		generalRiskItemComponentPage.clickSave();
		QuoteConsolidatedViewPage quoteConsolidatedViewPage = create(QuoteConsolidatedViewPage.class);
		
		// check if quote created
		assertEquals("Quote should be saved with Data Gathering status",
				"Data Gathering", quoteConsolidatedViewPage.getStatus());
	}	
	
	@After
	public void tearDown() {
		deleteTestProduct(productImportInfo);
	}
	
	@Override
	public Application setUpApplication() {
		return new MainApplication(getDriver(), getConfiguration());
	}
}
