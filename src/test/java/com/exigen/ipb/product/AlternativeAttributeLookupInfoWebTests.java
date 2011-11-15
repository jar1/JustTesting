package com.exigen.ipb.product;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import com.exigen.ipb.admin.pages.LookupPage;
import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.dialogs.LookupEditDialog;
import com.exigen.ipb.product.pages.dialogs.LookupInfoDialog;
import com.exigen.ipb.product.pages.dialogs.LookupInfoElement;
import com.exigen.ipb.product.pages.modules.AttributeModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium2 test that is used to verify behavior of attribute lookup info dialog
 * that is accessed through selection of 'Lookup Info' option next to an attribute. 
 * 
 * @author gzukas
 * @since 3.9
 */
public class AlternativeAttributeLookupInfoWebTests extends AbstractProductSeleniumTests {

	private static final String LOOKUP_TEMPLATE = "Base Product Lookup";
	
	private static final String LOOKUP_NAME = "AccidentViolationCd";
	
	private static final String PORT = "policyDetail.riskItems [*]";
	
	private static final String ATTRIBUTE = "territoryCd";
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static Double PRODUCT_VERSION = 1.0D;
	
	private AlternativeProductDataPage altProductDataPage;
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.selectTreeNode("Policy")
				.selectTreeNode(PORT)
				.connectComponent(PORT, "RiskItem", 1.0);
		
		altProductDataPage.expandTreeNode(PORT);
		altProductDataPage.selectTreeNode("RiskItem");
	}

	@Test
	public void shouldSearchLookup() {
		LookupInfoDialog lookupDialog = altProductDataPage.getAttributeModule().editAttributeLookupInfo(ATTRIBUTE);
		
		lookupDialog.search(LOOKUP_TEMPLATE);
		
		for (LookupInfoElement element : lookupDialog.getSearchResults()) {
			assertEquals("Wrong Lookup with name had been found",
					LOOKUP_TEMPLATE, element.getTemplateName());
		}
		
		lookupDialog.search("Any", LOOKUP_NAME);
		
		for (LookupInfoElement element : lookupDialog.getSearchResults()) {
			assertTrue("Wrong Lookup with template name had been found",
					element.getLookupName().contains(LOOKUP_NAME));
		}
	}
	
	@Test
	public void severalLookupsShouldNotBeSelected() {
		LookupInfoDialog lookupDialog = altProductDataPage.getAttributeModule().editAttributeLookupInfo(ATTRIBUTE);
		
		List<LookupInfoElement> lookups =
				lookupDialog.search(LOOKUP_TEMPLATE).getSearchResults();
		
		for (LookupInfoElement lookup : lookups) {
			lookupDialog.selectLookup(lookup);
		}
		
		int countSelected = 0;
		for (LookupInfoElement lookup : lookupDialog.getSearchResults()) {
			if (lookup.isSelected()) {
				countSelected++;
			}
		}
		
		assertEquals("Wrong number of lookups were selected", 1, countSelected);
	}
	
	@Test
	public void shouldCheckLookupParameters() {
		LookupInfoDialog lookupDialog = altProductDataPage.getAttributeModule().editAttributeLookupInfo(ATTRIBUTE);
		
		List<LookupInfoElement> lookups =
				lookupDialog.search(LOOKUP_TEMPLATE).getSearchResults();

		lookupDialog.selectLookup(lookups.get(0));
		LookupEditDialog editDialog = lookupDialog.next();
		assertTrue("LookupEditDialog was not displayed", editDialog.isDisplayed());
		assertFalse("LookupTemplate field should be disabled", editDialog.isLookupTemplateEnabled());
		assertFalse("LookupName field should be disabled", editDialog.isLookupNameEnabled());
	}

	@Test
	public void shouldCheckAlternativeFlow() {
		LookupInfoDialog lookupDialog = altProductDataPage.getAttributeModule().editAttributeLookupInfo(ATTRIBUTE);
		
		assertTrue("LookupInfo dialog was not displayed", lookupDialog.isDisplayed());
		
		lookupDialog.search(LOOKUP_TEMPLATE);

		LookupEditDialog editDialog = lookupDialog.createNewLookup();

		String uniqueName = "lup" + System.currentTimeMillis(); 
				
		editDialog.selectLookupTemplate(LOOKUP_TEMPLATE);
		editDialog.fillRandomLookupBindings();
		editDialog.setLookupName(uniqueName);
		editDialog.clickOk();
		
		altProductDataPage.clickSave();

		LookupPage lookupPage = create(LookupPage.class);
		lookupPage.navigate(getApplication());
		lookupPage.search(uniqueName);
		
		assertTrue("DB Lookup not found", lookupPage.getSearchResults().contains(uniqueName));
	}
	
	@Test
	public void shouldAssignAndUnassign() {
		// Test Assign.
		AttributeModule attributeModule = altProductDataPage.getAttributeModule();
		LookupInfoDialog lookupDialog = attributeModule.editAttributeLookupInfo(ATTRIBUTE);
		
		lookupDialog.selectLookup(
				lookupDialog.search(LOOKUP_TEMPLATE).getSearchResults().get(0));
		
		lookupDialog.next().fillRandomLookupBindings().clickOk();
		assertFalse("LookupInfo dialog was not closed", lookupDialog.isDisplayed());
		assertEquals("Wrong lookup was binded",
				LOOKUP_NAME, attributeModule.getLookupName(ATTRIBUTE));
		
		altProductDataPage.clickSave()
			.updateProductDataAlternative()
			.expandTreeNode(PORT);
		
		altProductDataPage.selectTreeNode("RiskItem");
		
		// Test Unassign.
		attributeModule.editAttributeLookupInfo(ATTRIBUTE)
			.selectLookup(lookupDialog.search(LOOKUP_TEMPLATE).getSearchResults().get(0));
		
		lookupDialog.unassign();
		
		assertTrue("Lookup is still displayed next to attribute", attributeModule.getLookupName(ATTRIBUTE).isEmpty());
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
