package com.exigen.ipb.product;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Selenium2 test used to verify component tree filtering in alternate
 * component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AlternativeComponentTreeFilterWebTests extends AbstractProductSeleniumTests {
	
	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRootAndPayment.zip";
	
    private AlternativeProductDataPage altProductDataPage;
    
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative();
	}
	
	/**
	 * Tests whether tree nodes are filtered for all types of component tree.
	 */
	@Test
	public void shouldFilter() {
		// Verify filtering in 'Component ports'
		altProductDataPage.search("Loss");
		assertFilter("Loss");
		
		// Verify filtering in 'Component relations'
		altProductDataPage.setViewMode(AlternativeProductDataPage.COMPONENT_RELATIONS_VIEW).search("poli");
		assertFilteredNodes("Policy", "PaymentOption");

		// Verify filtering in 'All components'. Nodes should be filtered by default.
		altProductDataPage.setViewMode(AlternativeProductDataPage.ALL_COMPONENTS_VIEW).search("cancel");
		assertFilter("cancel");
		
		altProductDataPage.setViewMode(AlternativeProductDataPage.COMPONENT_PORTS_VIEW).search(null).selectTreeNode("Policy");
		
		// Prepare tags		
		String tagA = "tagA" + System.currentTimeMillis();
		altProductDataPage.getTagModule()
				.editTags()
				.addTag(tagA)
				.selectTag(tagA)
				.saveTags();
		
		// Verify filtering in 'Tags'
		altProductDataPage.setViewMode(AlternativeProductDataPage.COMPONENT_TAGS_VIEW).search(tagA);
		assertFilteredNodes(tagA);
	}
	
	/**
	 * Verifies filtered nodes of component tree. Order of nodes is important. 
	 * @param expectedNodes  List of expected filtered nodes.
	 */
	private void assertFilteredNodes(String... expectedNodes) {
		List<String> actualNodes = altProductDataPage.getTreeNodes();
		
		assertEquals("Wrong number of filtered nodes", expectedNodes.length, actualNodes.size());
		
		assertEquals("Wrong nodes filtered", Arrays.asList(expectedNodes), actualNodes);
	}
	
	/**
	 * Verifies component tree filter result. It checks whether all filtered
	 * nodes conform to used criteria.
	 * 
	 * @param criteria  Search criteria.
	 */
	private void assertFilter(String criteria) {
		List<String> actualNodes = altProductDataPage.getTreeNodes();
		for (String node : actualNodes) {
			String message = String.format(
					"Node does not conform to search criteria. Criteria: <%s>, node: <%s>",
					criteria, node);
			
			boolean isMatched = node.toLowerCase().contains(criteria.toLowerCase()); 
			assertTrue(message, isMatched);
		}
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
