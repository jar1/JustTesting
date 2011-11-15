package com.exigen.ipb.product;

import static org.junit.Assert.*;

import com.exigen.ipb.components.domain.BusinessRuleType;
import com.exigen.ipb.policy.domain.PolicyChangesAllowedAt;
import com.exigen.ipb.policy.domain.RenewalTypesAllowed;
import com.exigen.ipb.product.pages.ProductActionsPage;
import com.exigen.ipb.product.pages.ProductConsolidatedViewPage;
import com.exigen.ipb.product.pages.ProductPropertiesPage;
import com.exigen.ipb.product.pages.ProductRulesConfigurationPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.product.pages.ProductWorkspacePage;
import com.exigen.ipb.product.pages.dialogs.AssignActionDialog;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage;
import com.exigen.ipb.product.pages.dialogs.ProductCopyPopUpPage.CopyProductPopUpFields;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

import com.exigen.ipb.selenium.utils.BroadLOB;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.PolicyTerm;
import com.exigen.ipb.selenium.utils.ProductAction;
import com.exigen.ipb.selenium.utils.ProductType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * testing creation of new product versions
 * @author aruskys
 */
public class ProductVersioningFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private static final String POC_PRODUCT_CODE = "pocruntime";
	private static final String POC_PRODUCT_PATH = "target/test-classes/products/pocruntime.zip";
	
	private static final String PRODUCT_CODE = "Version1026105514";
	
	private static final String POLICY_EFFECTIVE_DATE = "01/16/2010";
	private static final String POLICY_ENDORSEMENT_EFFECTIVE_DATE = "01/15/2011";
	
	private static final String COMPONENT_POLICY_CODE = "Policy";
	private static final int COMPONENT_POLICY_VERSION = 2;
	private static final String COMPONENT_RISKITEM_CODE = "RiskItem";
	private static final int COMPONENT_RISKITEM_VERSION = 1;
	
	private static final double PRODUCT_FIRST_VERSION = 1.0;
	private static final double PRODUCT_SECOND_VERSION = 2.0;
	
	/**
	 * testing product versioning with rules extensions
	 */
	@Test
	public void shouldCreateNewProductVersionWithRuleExtensions() {
		// importing product with POC rules
		importTestProduct(POC_PRODUCT_CODE, PRODUCT_FIRST_VERSION, POC_PRODUCT_PATH);
		
		// navigate to original product
		ProductConsolidatedViewPage firstConsolidatedView = navigateToProductView(POC_PRODUCT_CODE, PRODUCT_FIRST_VERSION);
		
		// extract business rules of first version
		Map<BusinessRuleType, List<String>> v1RuleNames = new HashMap<BusinessRuleType, List<String>>();
		firstConsolidatedView = extractBusinessRuleNames(v1RuleNames, firstConsolidatedView);
		
		// deploy and active
		firstConsolidatedView.deployProduct();
		firstConsolidatedView.activateProduct();
		
		// create new product version
		ProductConsolidatedViewPage secondConsolidatedView = createNewProductVersion(firstConsolidatedView, POC_PRODUCT_CODE, PRODUCT_FIRST_VERSION);
		
		// verify 
		verifyProductVersions(secondConsolidatedView);
		
		// extract business rules of second version
		Map<BusinessRuleType, List<String>> v2RuleNames = new HashMap<BusinessRuleType, List<String>>();
		secondConsolidatedView = extractBusinessRuleNames(v2RuleNames, secondConsolidatedView);
		
		// verify business rules
		assertEquals("Number of base business rules must match",
				v1RuleNames.get(BusinessRuleType.BaseRule).size(), v2RuleNames.get(BusinessRuleType.BaseRule).size());
		assertEquals("Number of extended business rules must match",
				v1RuleNames.get(BusinessRuleType.RuleExtension).size(), v2RuleNames.get(BusinessRuleType.RuleExtension).size());
		
		verifyRuleNames(v1RuleNames.get(BusinessRuleType.BaseRule), v2RuleNames.get(BusinessRuleType.BaseRule));
		verifyRuleNames(v1RuleNames.get(BusinessRuleType.RuleExtension), v2RuleNames.get(BusinessRuleType.RuleExtension));
	}
	
	/**
	 * testing versioning of simple product
	 * steps:
	 *		1. create a product
	 *		2. deploy product
	 *		3. active product
	 *		4. create product version
	 *		5. verify new product version
	 *		6. cleanup
	 */
	@Test
	public void shouldCreateNewProductVersion() {
		// given
		
		// enter product creation screen
		ProductSearchPage searchPage = create(ProductSearchPage.class);
		searchPage.navigate(getApplication());
		ProductPropertiesPage propertiesPage = searchPage.createProduct(ProductType.POLICY_PRODUCT);
		
		// create product
		propertiesPage.enterProductCode(PRODUCT_CODE)
			.enterProductName(PRODUCT_CODE)
			.enterAppliesToPolicyEffectiveDate(POLICY_EFFECTIVE_DATE)
			.enterAppliesToEndorsementEffectiveDate(POLICY_ENDORSEMENT_EFFECTIVE_DATE)
			.setBroadLineOfBusiness(BroadLOB.PERSONAL_LINES)
			.setLineOfBusiness(LOB.Automobile)
			.setPolicyTermType(PolicyTerm.MONTHLY)
			.setPolicyChangesAllowedAt(PolicyChangesAllowedAt.RENEWAL_AND_MIDTERM)
			.setRenewalTypesAllowed(RenewalTypesAllowed.AUTOMATIC);
		
		ProductConsolidatedViewPage consolidatedView = propertiesPage.clickNext();
		
		// update product actions
		ProductActionsPage actionsPage = consolidatedView.updateProductActions();
		AssignActionDialog dataGatherPopup = actionsPage.editActions(
				ProductActionsPage.ProcessState.quote,
				ProductActionsPage.TxType.newBusiness,
				ProductActionsPage.QuoteState.dataGather
		);
		dataGatherPopup.assign(ProductAction.dataGather);
		dataGatherPopup.clickSave();
		consolidatedView = actionsPage.clickSave();
		
		// update product data
		consolidatedView = consolidatedView.updateProductData()
				.expandComponentSelection()
				.selectComponent(COMPONENT_POLICY_CODE, COMPONENT_POLICY_VERSION)
				.selectComponent(COMPONENT_RISKITEM_CODE, COMPONENT_RISKITEM_VERSION)
				.clickAddComponents()
				.clickSave();
		
		// create default workspace
		ProductWorkspacePage workspacePage = consolidatedView.configureWorkspace();
		consolidatedView = workspacePage.clickSave();
		
		// deploy and activate
		consolidatedView.deployProduct().activateProduct();
		
		// create new product version
		consolidatedView = createNewProductVersion(consolidatedView, PRODUCT_CODE, PRODUCT_FIRST_VERSION);
		
		// verify
		verifyProductVersions(consolidatedView);
		
	}

	private void verifyProductVersions(ProductConsolidatedViewPage consolidatedView) {
		consolidatedView.clickProductVersionsToggleControl();
		// verify that:
		//		1.0 and 2.0 are existing
		//		1.0 is deployed and actived
		//		2.0 is undeployed and deactived
		//		secondConsolidatedView
		assertTrue("First version must exist", consolidatedView.productVersionExists(PRODUCT_FIRST_VERSION));
		assertTrue("Second version must exist", consolidatedView.productVersionExists(PRODUCT_SECOND_VERSION));
		assertTrue("First product version must be deployed", consolidatedView.productVersionIsDeployed(PRODUCT_FIRST_VERSION));
		assertTrue("First product version must be activated", consolidatedView.productVersionIsActive(PRODUCT_FIRST_VERSION));
		assertFalse("Second product version must be undeployed", consolidatedView.productVersionIsDeployed(PRODUCT_SECOND_VERSION));
		assertFalse("Second product version must be deactivated", consolidatedView.productVersionIsActive(PRODUCT_SECOND_VERSION));
	}
	
	private ProductConsolidatedViewPage extractBusinessRuleNames(Map<BusinessRuleType, List<String>> rules, ProductConsolidatedViewPage page) {
		ProductRulesConfigurationPage rulesPage = page.configureRules();
		rules.put(BusinessRuleType.BaseRule, rulesPage.getBasicRuleViewRuleName());
		rulesPage.clickConfigureAllRuleGroups();
		rules.put(BusinessRuleType.RuleExtension, rulesPage.getGroupViewRuleNames());
		return rulesPage.clickCancel();
	}

	private void verifyRuleNames(List<String> v1Rules, List<String> v2Rules) {
		for(String v1RuleName : v1Rules) {
			Assert.assertTrue("Version 2 must contain rule with name " + v1RuleName, v2Rules.contains(v1RuleName));
		}
	}
	
	private ProductConsolidatedViewPage createNewProductVersion(ProductConsolidatedViewPage consolidatedViewPage, String productCode, double coppiedVersion) {
		
		// create new product version
		consolidatedViewPage.clickProductVersionsToggleControl();
		ProductCopyPopUpPage versionDialog = consolidatedViewPage.clickCopyProductVersion(coppiedVersion);
		
		versionDialog.enterFieldValue(CopyProductPopUpFields.productCode, productCode);
		versionDialog.enterFieldValue(CopyProductPopUpFields.productName, productCode + " v2");
		versionDialog.assignCurrentDateForTransactionAndEffective();
		versionDialog.clickSave();
		
		// confirm version creation;
		ProductPropertiesPage newVersionPropertiesPage = consolidatedViewPage.confirmVersionCreation();
		
		// save new product version
		return newVersionPropertiesPage.clickNext();
	}
	
	@After
	public void tearDown() {
		getProductManager().deleteProduct(POC_PRODUCT_CODE, PRODUCT_FIRST_VERSION);
		getProductManager().deleteProduct(POC_PRODUCT_CODE, PRODUCT_SECOND_VERSION);
		getProductManager().deleteProduct(PRODUCT_CODE, PRODUCT_FIRST_VERSION);
		getProductManager().deleteProduct(PRODUCT_CODE, PRODUCT_SECOND_VERSION);
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
	
}
