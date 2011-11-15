package com.exigen.ipb.product;

import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage.CopyProductPopUpFields;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;

/**
 * test that verifies that it is possible to copy product containing business 
 * rules extenions
 * 
 * steps:
 *		import product
 *		verify existence of business rules extensions
 *		copy product
 *		verify existence of business rules in coppied product
 *		delete products.
 * @author aruskys
 */
public class ProductWithRuleExtensionCopyFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final double PRODUCT_VERSION = 1.0;
	
	private static final String EXTENSIONS_PRODUCT_NAME = "pocruntime";
	private static final String EXTENSIONS_PRODUCT_FILE = "target/test-classes/products/pocruntime.zip";
	
	private static final String STATE_SPECIFIC_PRODUCT_NAME = "pocgroups";
	private static final String STATE_SPECIFIC_PRODUCT_FILE = "target/test-classes/products/pocgroups.zip";
	
	private static final String COPPIED_PRODUCT_NAME = "poctest_auto_copy";
	
	@Test
	public void shouldCopyExtenionsWithNoExceptions() {
		// import product
		importTestProduct(EXTENSIONS_PRODUCT_NAME, PRODUCT_VERSION, EXTENSIONS_PRODUCT_FILE);
		
		// verify product
		ProductRulesConfigurationPage originalProductRulesPage = navigateToProductView(EXTENSIONS_PRODUCT_NAME, PRODUCT_VERSION)
				.configureRules().clickConfigureAllRuleGroups();
		
		List<String> originalExtensionNames = originalProductRulesPage.getGroupViewRuleNames();
		assertRulesExistence(originalExtensionNames);
		
		// copy product
		ProductConsolidatedViewPage coppiedProductView = copyProduct(
				originalProductRulesPage.clickCancel().clickCancel(), 
				EXTENSIONS_PRODUCT_NAME, 
				COPPIED_PRODUCT_NAME
		);
		
		// verify product
		ProductRulesConfigurationPage coppiedProductRulesPage = coppiedProductView.configureRules().clickConfigureAllRuleGroups();
		List<String> coppiedExtensionNames = coppiedProductRulesPage.getGroupViewRuleNames();
		
		assertRulesExistence(coppiedExtensionNames);
		assertRulesEquality(originalExtensionNames, coppiedExtensionNames);
	}
	
	@Test
	public void shouldCopyGroupSpecificRules() {
		// import product 
		importTestProduct(STATE_SPECIFIC_PRODUCT_NAME, PRODUCT_VERSION, STATE_SPECIFIC_PRODUCT_FILE);
		
		// verify product
		ProductRulesConfigurationPage originalProductPage = navigateToProductView(STATE_SPECIFIC_PRODUCT_NAME, PRODUCT_VERSION).configureRules().clickConfigureAllRuleGroups();
		List<String> originalRuleNames = originalProductPage.getGroupViewRuleNames();
		assertRulesExistence(originalRuleNames);
		
		// copy product
		ProductConsolidatedViewPage coppiedProductView = copyProduct(
				originalProductPage.clickCancel().clickCancel(),
				STATE_SPECIFIC_PRODUCT_NAME,
				COPPIED_PRODUCT_NAME
		);
		
		// verify product
		ProductRulesConfigurationPage coppiedProductRulesPage = coppiedProductView.configureRules().clickConfigureAllRuleGroups();
		List<String> coppiedRuleNames = coppiedProductRulesPage.getGroupViewRuleNames();
		
		assertRulesExistence(coppiedRuleNames);
		assertRulesEquality(originalRuleNames, coppiedRuleNames);
	}
	
	private void assertRulesExistence(List<String> ruleNames) {
		Assert.assertNotNull("Product rule collection can not be null", ruleNames);
		Assert.assertFalse("Product must contain at least one rule", ruleNames.isEmpty());
	}
	
	private void assertRulesEquality(List<String> originalRules, List<String> coppiedRules) {
		Assert.assertEquals("Rule extension count must match between original and copy", originalRules.size(), coppiedRules.size());

		for(String originalRuleName : originalRules) {
			Assert.assertTrue("Coppied product must contain rule with name " + originalRuleName, coppiedRules.contains(originalRuleName));
		}
	}
	
	private ProductConsolidatedViewPage copyProduct(ProductSearchPage searchPage, String productName, String copyName) {
		searchPage.searchForProduct(productName);
		
		ProductCopyPopUpPage copyProduct = searchPage.copyProduct(productName);
		copyProduct.enterFieldValue(CopyProductPopUpFields.productName, copyName);
		copyProduct.enterFieldValue(CopyProductPopUpFields.productCode, copyName);
		copyProduct.assignCurrentDateForTransactionAndEffective();
		
		return copyProduct.clickSave();
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(EXTENSIONS_PRODUCT_NAME, PRODUCT_VERSION);
		getProductManager().deleteProduct(STATE_SPECIFIC_PRODUCT_NAME, PRODUCT_VERSION);
		getProductManager().deleteProduct(COPPIED_PRODUCT_NAME, PRODUCT_VERSION);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}	
}
