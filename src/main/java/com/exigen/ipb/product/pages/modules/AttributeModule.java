package com.exigen.ipb.product.pages.modules;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.product.pages.ProductComponentsConfigurationPage;
import com.exigen.ipb.product.pages.ProductDataPage;
import com.exigen.ipb.product.pages.dialogs.AttributeAddNewPopUp;
import com.exigen.ipb.product.pages.dialogs.AttributeAddPopUp;
import com.exigen.ipb.product.pages.dialogs.AttributeConstraintsDialog;
import com.exigen.ipb.product.pages.dialogs.AttributeGeneralPropertiesEditPopUp;
import com.exigen.ipb.product.pages.dialogs.AttributeRemovalConfirmationPopUp;
import com.exigen.ipb.product.pages.dialogs.LookupInfoDialog;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.AttributeWebAction;
import com.exigen.ipb.selenium.utils.ComponentWebAction;
import com.exigen.ipb.selenium.utils.PageUtil;

/**
 * Alternative view page module that represents components attribute panel
 * 
 * @author gzukas
 * @since 3.9
 */
public class AttributeModule extends AbstractParentedWebPage<AlternativeProductDataPage> {

	private static final String LOOKUP_INFO_ITEM_VALUE = "alternateLookupSearchPopup";
	
	private static final String PROPERTIES_ITEM_VALUE = "selectedAttributeGeneralInfoPopup";
	
	private static final String CONSTRAINTS_ITEM_VALUE = "selectedAttributeConstraintsPopup";
	
	@FindBy(id="attributesForm:componentAttributesTable")
	private WebElement attributeTable;
	
	@FindBy(id="attributesForm:componentAttributesTable:tb")
	private WebElement attributesTable;
	
	@FindBy(id="attributeConfigurationForm:addNewAttribute")
	private WebElement addNewAttribute;
	
	@FindBy(id="attributeConfigurationForm:addAttribute")
	private WebElement addAttribute;
	
	public AttributeModule(WebDriver driver, PageConfiguration conf,
			AlternativeProductDataPage parentPage) {

		super(driver, conf, parentPage);
	}
	
	/**
	 * Drags and drops an attribute over another. Support of native events is required.
	 * 
	 * @param attributeName  The name of attribute to be dragged.
	 * @param afterAttributeName  The name of attribute to be used as drop target.
	 */
	public void dragAttribute(String attributeName, String targetAttributeName) {
		WebElement grippy = findGrippyOf(attributeName);
		WebElement target = getAttributeRow(targetAttributeName).findElement(By.tagName("td"));
		System.out.println(target.getTagName() + " " + target.getAttribute("id"));

		Actions actions = new Actions(getDriver());
		actions.dragAndDrop(grippy, target).perform();
	}
	
	/**
	 * @param attributeName  The attribute name.
	 * @return  Drag handler element that is used to initiate drag of attribute.
	 */
	private WebElement findGrippyOf(String attributeName) {
		WebElement attributeRow = getAttributeRow(attributeName);
		return attributeRow != null
				? attributeRow.findElement(By.className("grippy"))
				: null;
	}
	
	/**
	 * Opens remove attribute confirmation popup
	 * @param attributeName The attribute name to remove
	 * @return Attribute removal confirmation popup
	 */
	public AttributeRemovalConfirmationPopUp removeAttribute(String attributeName) {
		WebElement row = getAttributeRow(attributeName);
		row.findElement(By.linkText("Remove")).click();
		return create(AttributeRemovalConfirmationPopUp.class);
	}
	
	/**
	 * Checks if currently selected component has attribute
	 * @param attributeName The attribute name to check for
	 * @return
	 */
	public boolean existsAttribute(String attributeName) {
		return getAttributeRow(attributeName) != null;
	}
	
	/**
	 * @return  Names of displayed attributes.
	 */
	public List<String> getAttributeNames() {
		List<WebElement> rows = getAttributeRows();
		List<String> attributeNames = new ArrayList<String>(rows.size());
		for (WebElement row : rows) {
			attributeNames.add(getAttributeNameForRow(row));
		}
		return attributeNames;
	}

	/**
	 * Opens add new attribute popup
	 * @return
	 */
	public AttributeAddNewPopUp addNewAttribute() {
		addNewAttribute.click();
		return create(AttributeAddNewPopUp.class);
	}

	/**
	 * Opens add attribute popup
	 * @return
	 */
	public AttributeAddPopUp addAttribute() {
		addAttribute.click();
		return create(AttributeAddPopUp.class);
	}
	
	/**
	 * @return  Table rows of displayed attributes.
	 */
	private List<WebElement> getAttributeRows() {
		return attributeTable.findElements(By.className("rich-table-row"));
	}
	
	/**
	 * Returns a table row that is used to display attribute with given name.
	 * @param attributeName  The name of attribute.
	 * @return  Corresponding table row.
	 */
	private WebElement getAttributeRow(String attributeName) {
		List<WebElement> rows = getAttributeRows();
		for (WebElement row : rows) {
			if (getAttributeNameForRow(row).equals(attributeName)) {
				return row;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a name of attribute for given table row.
	 * 
	 * @param attributeRow  Attribute row.
	 * @return  Name of attribute.
	 */
	private String getAttributeNameForRow(WebElement attributeRow) {
		return attributeRow.getText().split(" ")[0];
	}
	
	/**
	 * Dirty way to get the name of lookup binded to specified attribute.
	 * 
	 * @param attributeName  The name of attribute.
	 * @return  The name of binded lookup.
	 */
	public String getLookupName(String attributeName) {
		int i = getAttributeNames().indexOf(attributeName);
		validateIndexBounds(i, attributeName);
		
		String columnId = new StringBuilder("attributesForm:componentAttributesTable:")
			.append(i).append(":lookupName")
			.toString();
		
		return attributeTable.findElement(By.id(columnId)).getText();
	}
	
	/**
	 * Opens dialog for editing component attribute constraints.
	 * 
	 * @param attributeName  The name of attribute to be modified.
	 * @return  Page Object of attribute constraints dialog.
	 */
	public AttributeConstraintsDialog editAttributeConstraints(String attributeName) {
		executeActionOnAttribute(attributeName, AttributeWebAction.EDIT_CONSTRAINTS);
		
		return create(AttributeConstraintsDialog.class, parent());
	}
	
	/**
	 * Opens dialog for editing component attribute general properties.
	 * 
	 * @param attributeName  The name of attribute to be modified.
	 * @return  Page Object of attribute properties dialog.
	 */
	public AttributeGeneralPropertiesEditPopUp editAttributeProperties(String attributeName) {
		executeActionOnAttribute(attributeName, AttributeWebAction.EDIT_GENERAL_INFO);
		
		return create(AttributeGeneralPropertiesEditPopUp.class, parent());
	}
	
	/**
	 * Opens dialog for editing component attribute lookup info.
	 * 
	 * @param attributeName  The name of attribute to be modified.
	 * @return  Page Object of attribute lookup info dialog.
	 */
	public LookupInfoDialog editAttributeLookupInfo(String attributeName) {
		executeActionOnAttribute(attributeName, AttributeWebAction.EDIT_LOOKUP_INFO);
		
		return create(LookupInfoDialog.class, parent());
	}
	
	/**
	 * Opens attribute edit dialog.
	 * @param attributeName
	 * @param action
	 */
	public void executeActionOnAttribute(String attributeName, AttributeWebAction action) {
		List<WebElement> tableRows =
				attributesTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement row : tableRows)	{			
			WebElement td = row.findElement(By.id("attributesForm:componentAttributesTable:" + i + ":attributeName"));			
			
			if (td.getText().equals(attributeName))	{				
				WebElement actionsColumn = row.findElement(By.id("attributesForm:componentAttributesTable:" + i + ":selectAttributeEditAction"));
				
				actionsColumn.findElement(By.id("attributesForm:componentAttributesTable:" + i + ":edit")).click();						
				PageUtil.waitTillElementLoaded(getDriver(), getWait(), "attributesForm:componentAttributesTable:" + i + ":"+ action + "_");
				actionsColumn.findElement(By.id("attributesForm:componentAttributesTable:" + i + ":"+ action + "_")).click();
			}
			i++;
		}	
	}
	
	private void validateIndexBounds(int i, String attributeName) {
		if (i < 0) {
			throw new IllegalArgumentException("No such attribute with name '" + attributeName + "' exists.");
		}
	}
	
	/**
	 * Generates piece of JavaScript code to be used to change value of
	 * inplaceSelect component that enlists actions for editing component
	 * attribute.
	 * 
	 * @param id  The id of inplaceSelect component.
	 * @param actionValue  Value to change to.
	 * @return  JavaScript Code.
	 */
	private String buildActionSelectJS(String id, String actionValue) {
		return new StringBuilder()
			.append("var c = document.getElementById('" + id + "').component;")
			.append("c.setValue('" + actionValue + "');")
			.toString();
	}
	
}
