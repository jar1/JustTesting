package com.exigen.ipb.product.pages.dialogs;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class AttributeAddPopUp extends AbstractWebPage {
	
	@FindBy(id="addAttributeForm:okButton")
	private WebElement okButton;
	
	@FindBy(id="addAttributeClose")
	private WebElement closeButton;
	
	@FindBy(id="addAttributeForm:attributeList:tb")
	private WebElement attributeList;
	
	public AttributeAddPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public AlternativeProductDataPage close() {
		closeButton.click();
		return create(AlternativeProductDataPage.class);
	}
	
	public AlternativeProductDataPage save() {
		okButton.click();
		return create(AlternativeProductDataPage.class);
	}

	public AttributeAddPopUp selectAttribute(String attributeName) {
		WebElement row = getRow(attributeName);
		if(row != null) {
			WebElement checkBox = row.findElement(By.tagName("input"));
			if(!checkBox.isSelected()) {
				checkBox.click();
			}
		}
		return this;
	}
	
	public AttributeAddPopUp deselectAttribute(String attributeName) {
		WebElement row = getRow(attributeName);
		if(row != null) {
			WebElement checkBox = row.findElement(By.tagName("input"));
			if(checkBox.isSelected()) {
				checkBox.click();
			}
		}
		return this;
	}
	
	public boolean existsAttribute(String attributeName) {
		return getRow(attributeName) != null;
	}
	
	private List<WebElement> getRows() {
		return attributeList.findElements(By.cssSelector("tr.extdt-firstrow"));
	}
	
	private WebElement getRow(String attributeName) {
		List<WebElement> rows = getRows();
		for(WebElement row : rows) {
			String name = row.getText().split("\n")[0];
			if(name.equals(attributeName)) {
				return row;
			}
		}
		return null;
	}
}
