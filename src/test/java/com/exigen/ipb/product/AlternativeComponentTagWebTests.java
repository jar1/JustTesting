package com.exigen.ipb.product;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.modules.TagModule;
import com.exigen.ipb.selenium.AdminApplication;
import com.exigen.ipb.selenium.Application;
import com.exigen.ipb.selenium.tests.AbstractProductSeleniumTests;

/**
 * Selenium test that is used to verify correct behavior of component tagging
 * in alternate component view.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AlternativeComponentTagWebTests extends AbstractProductSeleniumTests {

	private final static String PRODUCT_CD = "seleniumProduct";
	private final static double PRODUCT_VERSION = 1.0D;
	private final static String PRODUCT_DIR = "target/test-classes/products/withPolicyRoot.zip";
	
    private AlternativeProductDataPage altProductDataPage;
    
    private TagModule tagModule;
    
	@Override
	protected void afterCustomSettingsSetUp() {
		importTestProduct(PRODUCT_CD, PRODUCT_VERSION, PRODUCT_DIR);
		altProductDataPage = navigateToProductView(PRODUCT_CD, PRODUCT_VERSION)
				.updateProductDataAlternative()
				.selectTreeNode("Policy");
  
    	tagModule = altProductDataPage.getTagModule();
	}
	
	/**
	 * Tests whether new tag had been added and saved.
	 */
	@Test
	public void shouldAddTag() {
		String tagName = "add";

		assertTrue("Tag Edit dialog has not been opened",
				tagModule.editTags().isTagDialogDisplayed());

		assertTrue("Tag was not added to all tag list",
				tagModule.addTag(tagName).isTagDisplayed(tagName));

		assertFalse("Tag Edit dialog has not been closed",
				tagModule.saveTags().isTagDialogDisplayed());
		
		tagModule.editTags();
				
		assertTrue("Tag was not saved",
				tagModule.isTagDisplayed(tagName));
	}
	
	/**
	 * Tests whether tag is removed.
	 */
	@Test
	public void shouldRemoveTag() {
		String tagName = "remove";
		
		boolean isTagVisible = tagModule.editTags()
				.addTag(tagName)
				.removeTag(tagName)
				.isTagDisplayed(tagName);
		
		assertFalse("Tag was not removed from tag list", isTagVisible);
		
		isTagVisible = tagModule.saveTags()
				.editTags()
				.isTagDisplayed(tagName);
		
		assertFalse("Tag should have been deleted", isTagVisible);
	}
	
	/**
	 * Tests whether component is tagged.
	 */
	@Test
	public void shouldTagSelectedComponent() {
		String tagName = "tag";
		
		boolean isSelected = tagModule.editTags()
				.addTag(tagName)
				.selectTag(tagName)
				.isTagSelected(tagName);
		
		assertTrue("Tag supposed to be selected", isSelected);
		
		boolean isTagged = tagModule.saveTags()
				.isComponentTagDisplayed(tagName);

		assertTrue("Component have not been tagged", isTagged);
	}
	
	/**
	 * Tests whether component is untaged.
	 */
	@Test
	public void shouldUntagSelectedComponent() {
		String tagName = "untag";

		boolean isTagVisible = tagModule.editTags()
			.addTag(tagName)
			.selectTag(tagName) // Select
			.saveTags()
			.editTags()
			.selectTag(tagName) // Deselect
			.saveTags()
			.isComponentTagDisplayed(tagName);
		
		assertFalse("Component have not been untagged", isTagVisible);
	}
	
	/**
	 * Tests whether tag is renamed.
	 */
	@Test
	public void shouldRenameTag() {
		String tagName = "rename";
		String newTagName = "renameNew";
		
		tagModule.editTags()
				.addTag(tagName)
				.saveTags()
				.editTags()
				.renameTag(tagName, newTagName, getDriver())
				.saveTags();
		boolean isRenamed =	tagModule.editTags()
				.isTagDisplayed(newTagName);
		
		assertTrue("Tag was not renamed", isRenamed);
	}
	
	/**
	 * Tests whether newly added tag is saved when clicking on top save button.
	 */
	@Test
	public void shouldSaveTags() {
		String tagName = "save";
		
		tagModule.editTags()
			.addTag(tagName)
			.saveTags();
		
		altProductDataPage = altProductDataPage.clickSave()
			.updateProductDataAlternative();
		
		tagModule = altProductDataPage.getTagModule().editTags();

		assertTrue("Tag was not saved",	tagModule.isTagDisplayed(tagName));
		
		// Garbage collect.
		tagModule.removeTag(tagName).saveTags();
		altProductDataPage.clickSave();
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
