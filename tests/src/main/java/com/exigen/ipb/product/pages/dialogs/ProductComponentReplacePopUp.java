package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductDataPage;
import com.exigen.ipb.product.pages.ProductWorkspacePage;
import com.exigen.ipb.product.pages.ProductsComponentReplaceMergePage;
import com.exigen.ipb.selenium.experimental.Tag;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * @author jdaskevicius
 * 
 * @since 3.9
 * 
 */
public class ProductComponentReplacePopUp extends AbstractWebPage {
	
	@Tag("default")
	@FindBy(id="replaceConfigPanelHeader")
	private WebElement headerNewComponent;
	
	@Tag("default")
	@FindBy(id="replaceConfigForm:selectNewCmp")
	private WebElement dropDownNewComponent;
	
	@Tag("default")
	@FindBy(id="replaceConfigForm:diffBtn")
	private WebElement buttonReplace;
	
	@Tag("default")
	@FindBy(id="replaceConfigForm:cancelBtn")
	private WebElement buttonCancel;		
	
	public ProductComponentReplacePopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}

	public List<String> getComponentsListAllOptions() {
		List<WebElement> componentListElements = dropDownNewComponent.findElements(By.tagName("option"));
		List<String> componentListOptions = new ArrayList<String>();	
		
		for (WebElement arr : componentListElements) {
			componentListOptions.add(arr.getText());
		}
		
		return componentListOptions;
	}
	
	public ProductComponentReplacePopUp selectComponent(String componentName) {
		dropDownNewComponent.findElement(By.cssSelector("option[value='"+ componentName +"']")).click();
		
		return this;
	}
	
	public ProductsComponentReplaceMergePage selectReplaceComponent(String componentName) {
		dropDownNewComponent.findElement(By.cssSelector("option[value='"+ componentName +"']")).click();
		buttonReplace.click();
		
		return create(ProductsComponentReplaceMergePage.class);
	}

	public ProductDataPage clickReplace() {
		buttonReplace.click();
		return create(ProductDataPage.class);
	}	
	
	public ProductDataPage clickCancel() {
		buttonCancel.click();
		return create(ProductDataPage.class);
	}	
}