package com.exigen.ipb.product;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductPropertiesPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * web tests for product ruleset actions
 * @author aruskys
 */
public class ProductRuleSetFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final double PRODUCT_VERSION = 1.0;	
	private static final String ORIGINAL_PRODUCT_CODE = "pocruntime";
	private static final String ORIGINAL_PRODUCT_FILE = "target/test-classes/products/pocruntime.zip";	
	private static final String NEW_PRODUCT_CODE = "pocruntime_new";	
	
	/**
	 * actives current rule set
	 * creates new rule set
	 * actives new rule set
	 * deactives new rule set
	 */
	@Test 
	public void shouldCreateActivateDeactivatePOCProductRulesetWithRuleExtensions() {
		// given
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
		String dateString = dateFormat.format(tomorrow);
		
		// import product
		importTestProduct(ORIGINAL_PRODUCT_CODE, PRODUCT_VERSION, ORIGINAL_PRODUCT_FILE);
		ProductConsolidatedViewPage consolidatedView = navigateToProductView(ORIGINAL_PRODUCT_CODE, PRODUCT_VERSION);
		
		// change product name and code
		ProductPropertiesPage propertiesPage = consolidatedView.updateProductProperties();
		propertiesPage.enterProductCode(NEW_PRODUCT_CODE);
		propertiesPage.enterProductName(NEW_PRODUCT_CODE);
		consolidatedView = propertiesPage.clickNext();
		
		// active first ruleset
		ProductRulesConfigurationPage rulesConfigurationPage = consolidatedView.configureRules();
		
		rulesConfigurationPage.clickActivateVersion();
		rulesConfigurationPage.createNewVersion(dateString);		
		// verify rulesets
		verifyRuleSetVersions(rulesConfigurationPage, 1, 2);
		
		// active second rule set
		rulesConfigurationPage.clickActivateVersion();		
		// verify rulesets
		verifyRuleSetVersions(rulesConfigurationPage, 2, 2);
		
		// return to consolidated view
		consolidatedView = rulesConfigurationPage.clickCancel();
		
		// return to business rules view
		rulesConfigurationPage = consolidatedView.configureRules();		
		// verify rulesets
		verifyRuleSetVersions(rulesConfigurationPage, 2, 2);
		
		// deactive current active ruleset
		rulesConfigurationPage.clickDeactivateVersion();		
		// verify rulesets
		verifyRuleSetVersions(rulesConfigurationPage, 1, 2);
		
		// remove inactive ruleset
		rulesConfigurationPage.clickRemoveInactiveVersion();		
		// verify ruleSets
		verifyRuleSetVersions(rulesConfigurationPage, 1, 1);
		
	}
	
	private void verifyRuleSetVersions(ProductRulesConfigurationPage page, int activeVersion, int lastVersion) {
		int activeRuleSetVersion = page.getActiveRuleSetVersion();
		int lastRuleSetVersion = page.getLastRuleSetVersion();
		
		Assert.assertEquals("Active rule set version must 1", activeRuleSetVersion, activeVersion);
		Assert.assertEquals("Last rule set version must be 2", lastRuleSetVersion, lastVersion);
		
		boolean versionsMatches = activeVersion == lastVersion;
		List<String> ruleNames = page.getBasicRuleViewRuleName();
		
		if(versionsMatches) {
			Assert.assertFalse("All rule selection should be disabled", page.isAllRulesSelectable());
		}
		else {
			Assert.assertTrue("All rule selection should be enabled", page.isAllRulesSelectable());
		}
		
		for(String ruleName : ruleNames) {
			Assert.assertEquals("Rule selection status ["+ruleName+"]", !versionsMatches, page.isRuleSelectable(ruleName));
			Assert.assertEquals("Rule edition status ["+ruleName+"]", !versionsMatches, page.isRuleEditable(ruleName));
			Assert.assertEquals("Rule copying status ["+ruleName+"]", !versionsMatches, page.isRuleCopyable(ruleName));
			Assert.assertEquals("Rule moving status ["+ruleName+"]", activeRuleSetVersion == 0, page.isRuleMovable(ruleName));
		}
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(NEW_PRODUCT_CODE, PRODUCT_VERSION);
	}

	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}	
}
