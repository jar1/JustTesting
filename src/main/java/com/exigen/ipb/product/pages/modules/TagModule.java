package com.exigen.ipb.product.pages.modules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;
import com.exigen.ipb.selenium.utils.PageUtil;

/**
 * A module that represents a part of alternative product data page that is
 * responsible for component tagging.
 * 
 * @author gzukas
 * @since 3.9
 */
public class TagModule extends AbstractParentedWebPage<AlternativeProductDataPage> {
	
	@FindBy(id="componentTagsForm:editTags")
	private WebElement editTagsLink;

	@FindBy(id="tagEditForm:saveButton")
	private WebElement saveTagsButton;

	@FindBy(id="tagEditForm:cancelButton")
	private WebElement cancelTagsButton;

	@FindBy(id="tagEditForm:tagInput")
	private WebElement tagInput;

	@FindBy(id="tagEditForm:addTagButton")
	private WebElement addTagButton;

	@FindBy(id="tagEditPanelContentTable")
	private WebElement tagEditDialog;

	@FindBy(id="tagEditForm:tags")
	private WebElement tags;
	
	@FindBy(id="componentTagsForm:componentTags")
	private WebElement componentTags;
	
	public TagModule(WebDriver driver, PageConfiguration conf, AlternativeProductDataPage parent) {
		super(driver, conf, parent);
	}
	
	/**
	 * Opens tag editor.
	 */
	public TagModule editTags() {
		if (!editTagsLink.isDisplayed()) {
			WebElement componentTagsPanel =
					find(By.id("componentTagPanel_header"));
			
			componentTagsPanel.click();
		}
		
		editTagsLink.click();
		return this;
	}
	
	/**
	 * Adds a tag.
	 * 
	 * @param tagName  The name of new tag.
	 * @return True if new tag is visible in the list, otherwise - false.
	 */
	public TagModule addTag(String tagName) {
		tagInput.sendKeys(tagName);
		addTagButton.click();

		return this;
	}

	/**
	 * Selects a tag with given name.
	 * @param tagName  Name of tag to be selected.
	 */
	public TagModule selectTag(String tagName) {
		WebElement tag = findTagElement(tagName);
		if (tag != null) {
			tag.click();
		}
		
		return this;
	}
	
	/**
	 * Determines whether tag with given name is selected. Tag is selected
	 * when it is marked with 'selected' style class.
	 * 
	 * @param tagName  Name of tag to be checked.
	 * @return  True if tag is selected, otherwise - false.
	 */
	public boolean isTagSelected(String tagName) {
		return ElementUtils.containsStyleClass(findTagElement(tagName), "selected");
	}
	
	/**
	 * Removes tag from list in tag editor dialog.
	 * 
	 * @param tagName  The name of tag to be removed.
	 * @return  True if after removal tag is not visible in the list, otherwise - false.
	 */
	public TagModule removeTag(String tagName) {
		WebElement tag = findTagElement(tagName);
		WebElement deleteLink = tag.findElement(By.tagName("a"));
		deleteLink.click();

		return this;
	}

	/**
	 * Renames a tag.
	 * 
	 * @param oldTagName  The name of tag to be renamed.
	 * @param newTagName  The new name of tag.
	 * @param webDriver 
	 * @return
	 */
	public TagModule renameTag(String oldTagName, String newTagName, WebDriver webDriver) {		
		WebElement tag = findTagElement(oldTagName);		
		WebElement tagNameField = tag.findElement(
				By.cssSelector("span.rich-inplace input.rich-inplace-field"));
		
		tagNameField.click();
		
		tagNameField.clear();
		PageUtil.waitForAjax(getDriver(), new WebDriverWait(getDriver(), 10), "wait for ajax");
		tagNameField.sendKeys(newTagName, Keys.RETURN);
		return this;
	}
	
	/**
	 * Clicks on save button in tag editor dialog.
	 */	
	public TagModule saveTags() {
		saveTagsButton.click();
		return this;
	}
	
	/**
	 * Clicks on cancel button in tag editor dialog.
	 */
	public TagModule cancelTags() {
		cancelTagsButton.click();
		return this;
	}
	
	/**
	 * Determines whether tag editor dialog is visible
	 * @return  True if dialog is visible, otherwise - false.
	 */
	public boolean isTagDialogDisplayed() {
		return tagEditDialog.isDisplayed();
	}
	
	/**
	 * Returns a list of names, of all tags.
	 * @return  Names of tags.
	 */
	public List<String> getTags() {
		return ElementUtils.getAttributeValues(
				tags, By.cssSelector("li.tag span input"), "value");
	}
	
	/**
	 * Returns tag names of selected component.
	 * @return
	 */
	public List<String> getComponentTags() {
		return ElementUtils.getTextValues(componentTags, By.tagName("li"));
	}
	
	/**
	 * Indicates whether tag with given name is visible in tag editor.
	 * 
	 * @param tagName  The name of tag.
	 * @return  True if tag is present in all tag list, otherwise - false.
	 */
	public boolean isTagDisplayed(String tagName) {
		return findTagElement(tagName) != null;
	}
	
	/**
	 * Indicates whether tag with given name is visible in list of component tags.
	 * 
	 * @param tagName  The name of tag.
	 * @return  True if tag is present in componet tags, otherwise - false.
	 */
	public boolean isComponentTagDisplayed(String tagName) {
		return getComponentTags().contains(tagName);
	}
	
	/**
	 * Returns list element (&lt;li/&gt;) of tag with specified name.
	 * 
	 * @param tagName  The name of tag.
	 * @return  If found - tag list element, otherwise - null.
	 */
	private WebElement findTagElement(String tagName) {
		try {
			WebElement tagInput = tags.findElement(By.cssSelector("li.tag span input[value='" +tagName+ "']"));
			String tagInputId = tagInput.getAttribute("id");
			String tagItemId = tagInputId.substring(0, tagInputId.lastIndexOf(":"));
			
			return tags.findElement(By.id(tagItemId));
		}
		catch (NoSuchElementException e) {
			return null;
		}
	}

}

