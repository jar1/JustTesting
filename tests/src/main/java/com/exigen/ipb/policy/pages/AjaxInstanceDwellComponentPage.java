package com.exigen.ipb.policy.pages;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page Object of Dwell component available on ajaxinstance product.
 * 
 * @author gzukas
 * @since 3.9
 */
public class AjaxInstanceDwellComponentPage extends AbstractWebPage {

	public static final String ATTRIBUTE_TEST1 = "test1";
	
	public static final String ATTRIBUTE_TEST2 = "test2";
	
	public static final String ATTRIBUTE_TEST3 = "test3";
	
	public static final String ATTRIBUTE_TEST4 = "test4";
	
	public static final String ATTRIBUTE_PRIMARY_RESIDENCE = "primaryResidence";
	
	private static final String DWELL_NAME_FORMAT =
			"policyDataGatherForm:dataGatherView_ListDwell:%d:dataGatherView_List_Text";
	
	@FindBy(id="policyDataGatherForm:sedit_Dwell_additionalFields_test1_stringValue")
	private WebElement testOne;
	
	@FindBy(id="policyDataGatherForm:sedit_Dwell_additionalFields_test2_stringValue")
	private WebElement testTwo;
	
	@FindBy(id="policyDataGatherForm:sedit_Dwell_additionalFields_test3_stringValue")
	private WebElement testThree;

	@FindBy(id="policyDataGatherForm:sedit_Dwell_additionalFields_test4_stringValue")
	private WebElement testFour;
	
	@FindBy(id="policyDataGatherForm:sedit_Dwell_primaryResidence")
	private WebElement primaryResidence;
	
	@FindBy(id="policyDataGatherForm:addDwell")
	private WebElement addDwellButton;
	
	@FindBy(id="policyDataGatherForm:componentList_Dwell")
	private WebElement dwellList;
	
	private Map<String, WebElement> attributeMap = new HashMap<String, WebElement>();
	
	public AjaxInstanceDwellComponentPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
		populateAttributes(attributeMap);
	}

	/**
	 * Changes value of input field.
	 * 
	 * @param key  The key of field.
	 * @param value  The value.
	 */
	public void setAttributeValue(String key, String value) {
		WebElement element = attributeMap.get(key);
		if (element != null) {
			element.clear();
			element.sendKeys(value, Keys.RETURN);
		}
	}
	
	/**
	 * Returns a value of input field.
	 * 
	 * @param key  The key of field.
	 * @return  The value.
	 */
	public String getAttributeValue(String key) {
		WebElement element = attributeMap.get(key);
		return element != null ? ElementUtils.getInputValue(element) : null;
	}

	/**
	 * Returns a name of Dwell instance at given position.
	 * 
	 * @param index  The index of dwell list item.
	 * @return  The name of dwell.
	 */
	public String getDwellName(int index) {
		String nameId = String.format(DWELL_NAME_FORMAT, index);
		return dwellList.findElement(By.id(nameId)).getText();
	}
	
	/**
	 * Adds new Dwell instance.
	 */
	public void clickAdd() {
		addDwellButton.click();
	}

	private void populateAttributes(Map<String, WebElement> output) {
		output.put(ATTRIBUTE_TEST1, testOne);
		output.put(ATTRIBUTE_TEST2, testTwo);
		output.put(ATTRIBUTE_TEST3, testThree);
		output.put(ATTRIBUTE_TEST4, testFour);
		output.put(ATTRIBUTE_PRIMARY_RESIDENCE, primaryResidence);
	}

}
