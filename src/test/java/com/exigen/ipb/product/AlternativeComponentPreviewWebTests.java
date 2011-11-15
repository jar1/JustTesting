package com.exigen.ipb.product;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentPreviewPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium 2 test to cover component preview functionality on alternative product data update screen
 * 
 * @author mulevicius
 * @since 3.9
 */
public class AlternativeComponentPreviewWebTests extends AbstractProductSeleniumTests {

	private AlternativeProductDataPage altProductDataPage;
	private ProductComponentPreviewPage componentPreviewPage;
	
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	
	// test data for DrivingLicence 1.0 preview page (commented because of WebDribver issues - it hangs on executing)
	private List<String> tags = Arrays.asList("select", "input", "input");
	private List<String> types = Arrays.asList(null, "text", "text");
	private List<String> labels = Arrays.asList("Licensing Province", "Licensing Number", "License Date");
	
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.setViewMode(AlternativeProductDataPage.ALL_COMPONENTS_VIEW)
				.selectTreeNode("Policy");
	}

	/*
	 *  This test should be used to investigate why selenium hangs on some specific cases according to
	 *  "EISDEV-19839 Investigate why selenium (WebDriver) hangs when executing specific PF tests"
	 *  
	 *  Problem should be investigated and solved 
	 */
	@Ignore("selenium hangs here, but test should be used for investigation")
	@Test
	public void shouldPreviewDrivingLicence10butSeleniumHangs() {
		componentPreviewPage = altProductDataPage.selectTreeNode("DrivingLicence 1.0").previewUserComponent();		

		assertTrue("Preview of DrivingLicence 1.0 is incorrect", componentPreviewPage.containsStructures(labels, tags, types));
	}
	
	/**
	 * Tests if correct behavior happens when RateAction 1.0 is previewed (no contents, so a label with info is expected)
	 */
	@Ignore("selenium hangs here on FF3.6, it is OK on FF7")
	@Test
	public void shouldPreviewRateAction10() {
		componentPreviewPage = altProductDataPage.selectTreeNode("RateAction 1.0").previewUserComponent();

		// selenium sometimes hangs if isComponentPreviewAvailable() used
		assertTrue("Component preview is not supposed to be available", componentPreviewPage.isComponentPreviewUnAvailable());
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
