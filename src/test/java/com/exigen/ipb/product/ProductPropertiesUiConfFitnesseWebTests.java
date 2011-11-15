package com.exigen.ipb.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.exigen.ipb.product.pages.ProductPropertiesPage;
import com.exigen.ipb.product.pages.ProductSearchPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;
import com.exigen.ipb.selenium.utils.BroadLOB;
import com.exigen.ipb.selenium.utils.LOB;
import com.exigen.ipb.selenium.utils.PolicyTerm;
import com.exigen.ipb.selenium.utils.ProductType;

/**
 * Selenium2 PF smoke test which checks default product properties ui conformance when creating new product
 * 
 * @category FitNesse 
 * 
 * Covers: EisTestCases.TestSuite.ProductSuite.ProductPropertiesUiConformance
 *   
 * @author mulevicius
 * 
 * @since 3.9
 */

public class ProductPropertiesUiConfFitnesseWebTests extends AbstractProductSeleniumTests {
	
	private ProductPropertiesPage productPropertiesPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {				
		ProductSearchPage productSearchPage = create(ProductSearchPage.class);
		productSearchPage.navigate(getApplication());
		productPropertiesPage = productSearchPage.createProduct(ProductType.POLICY_PRODUCT);
	}
	
	/**
	 * Main test method that checks if correct UI and default values are displayed when creating product
	 */
	@Test
	public void testProductOverviewUIConformance() {
		// check if fields has mandatory constraints
		assertTrue("Product Code field did not have mandatory constraint",
				productPropertiesPage.isProductCodeMandatory());
		assertTrue("Product Name field did not have mandatory constraint", 
				productPropertiesPage.isProductNameMandatory());
		assertTrue("Effective Date field did not have mandatory constraint",
				productPropertiesPage.isEffectiveDateMandatory());
		assertTrue("Endorsement Effective Date field did not have mandatory constraint",
				productPropertiesPage.isEndorsementEffectiveDateMandatory());
		assertTrue("Broad Line of Business field did not have mandatory constraint",
				productPropertiesPage.isBroadLOBMandatory());
		assertTrue("Line of Business field did not have mandatory constraint", 
				productPropertiesPage.isLOBMandatory());
		assertTrue("Renewal Types Allowed field did not have mandatory constraint", 
				productPropertiesPage.isRenewalTypesAllowedMandatory());
		assertTrue("Policy Changes Allowed At field did not have mandatory constraint", 
				productPropertiesPage.isPolicyChangesAllowedAtMandatory());
		
		// check statuses
		assertEquals("Wrong default product status", "Deactivated", productPropertiesPage.getProductStatus());
		assertEquals("Wrong default policy term type", "Monthly", productPropertiesPage.getPolicyTermType());
		assertEquals("Wrong default product activation date", "Not Active", productPropertiesPage.getProductActivationDate());

		// check default available actions
		// ... in Broad LOB drop down
		List<String> availableActions = productPropertiesPage.getOptionsOfBroadLOBDropDownAsText();
		List<BroadLOB> expectedBLOBActions = Arrays.asList(BroadLOB.values());
		assertSelect(availableActions, expectedBLOBActions, "Broad LOB drop down");

		// ... in LOB drop down
		availableActions = productPropertiesPage.getOptionsOfLOBDropDownAsText();
		List<LOB> expectedLOBActions = Arrays.asList(LOB.values());
		assertSelect(availableActions, expectedLOBActions, "LOB drop down");
		
		//assertTrue("Some expected actions in Broad LOB drop down are missing", availableActions.containsAll(expectedActions));
		// ... in Policy Term drop down
		availableActions = productPropertiesPage.getOptionsOfPolicyTermDropDownAsText();
		List<PolicyTerm> expectedPolicyTermActions = Arrays.asList(PolicyTerm.values());
		assertSelect(availableActions, expectedPolicyTermActions, "Policy Term drop down");
		
		// check if buttons are displayed
		assertTrue("Expected radio button Policy Changes Allowed At Renewal & Mid-term is not displayed",
				productPropertiesPage.isOptionPolicyChangesAllowedAtRenewalMidTermVisible());
		assertTrue("Expected radio button Policy Changes Allowed At Renewal Only is not displayed",
				productPropertiesPage.isOptionPolicyChangesAllowedAtRenewalVisible());
		assertTrue("Expected radio button Renewal Types Allowed Automatic is not displayed", 
				productPropertiesPage.isOptionRenewalTypesAllowedAutoVisible());
		assertTrue("Expected radio button Renewal Types Allowed Both was not displayed",
				productPropertiesPage.isOptionRenewalTypesAllowedBothVisible());
		assertTrue("Expected radio button Renewal Types Allowed Manual was not displayed",
				productPropertiesPage.isOptionRenewalTypesAllowedManualVisible());
		
		// check if correct error messages are displayed
		productPropertiesPage.clickNext();
		assertEquals("Wrong error message is dislayed for Policy Effective Date ", 
				"Used for Policy Effective Date is required", productPropertiesPage.getEffectiveDateErrorMsg());
		assertEquals("Wrong error message is dislayed for Broad Line of Business",
				"Broad Line of Business is required", productPropertiesPage.getBroadLOBErrorMsg());
		assertEquals("Wrong error message is dislayed for Transaction Date",
				"Used for Transaction Date is required", productPropertiesPage.getEndorsementEffectiveDateErrorMsg());
		assertEquals("Wrong error message is dislayed for Line of Business", 
				"Line of Business is required", productPropertiesPage.getLOBErrorMsg());
		assertEquals("Wrong error message is dislayed for Policy Changes Allowed At", 
				"Policy Changes Allowed At is required", productPropertiesPage.getPolicyChangesAllowedAtErrorMsg());
		assertEquals("Wrong error message is dislayed for Renewal Types Allowed", 
				"Renewal Types Allowed is required", productPropertiesPage.getRenewalTypesAllowedErrorMsg());
		assertEquals("Wrong error message is dislayed for Product Code", 
				"Product Code is required", productPropertiesPage.getProductCodeErrorMsg());
		assertEquals("Wrong error message is dislayed for Product Name", 
				"Product Name is required", productPropertiesPage.getProductNameErrorMsg());
		
	}
	
	private void assertSelect(List<String> availableActions, List<?> expectedActions, String dropDown) {
		for(Object expected : expectedActions) {
			assertTrue("Action " + expected.toString() + " is missing in " + dropDown, availableActions.contains(expected.toString()));
		}
		//assertEquals("Additional actions were present in " + dropDown, expectedActions.size(), availableActions.size());
	}
	
	@Override
	public Application setUpApplication() {
		return new AdminApplication(getDriver(), getConfiguration());
	}
}
