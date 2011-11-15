package com.exigen.ipb.product.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.dialogs.ProductComponentReplacePopUp;
import com.exigen.ipb.product.pages.modules.LegacyComponentInfoModule;
import com.exigen.ipb.product.pages.modules.PageModule;
import com.exigen.ipb.product.pages.modules.PageModules;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ComponentWebAction;

import org.openqa.selenium.NoSuchElementException;
/**
 * Class that represents product data configuration in old view. Used in 
 * Selenium2 tests.
 * 
 * @author ggrazevicius
 * @author mulevicius
 * @author jdaskevicius
 * 
 * @since 3.9
 */
public class ProductDataPage extends AbstractWebPage {
	
	@FindBy(id="topSaveLink")
	private WebElement topSaveButton;
	
	@PageModule(LegacyComponentInfoModule.class)
	private LegacyComponentInfoModule legacyComponentInfoModule;		
	
	@FindBy(id="iconcomponentsConfigurationForm:componentsSelection")
	private WebElement componentSelection;	
	
	@FindBy(id="componentsConfigurationForm:body_componentTable:tb")
	private WebElement componentsTable;

	@FindBy(id="componentsConfigurationForm:body_configurationTable:tb")
	private WebElement connectedComponentsTable;	
	
	@FindBy(id="componentsConfigurationForm:addComponentBtn")
	private WebElement buttonAddComponents;

	@FindBy(id="topCancelLink")
	private WebElement cancelButton;
	
	public ProductDataPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
		
		PageModules.initModules(driver, conf, this);
	}
	
	public ProductDataPage selectComponent(String component, int version) {
		List<WebElement> tableRows =
				componentsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":componentPreview"));			
			if (td.getText().equals(component))	{
				WebElement ver = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":column_componentVersion"));
				if (ver.getText().equals(Integer.toString(version))) {
				table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":select")).click();
				return this;
				}
			}
			i++;
		}
		return null;
	}	
	
	public ProductComponentsConfigurationPage configureComponent(int row) {
		String id = "componentsConfigurationForm:body_configurationTable:" + row + ":configureLink";
		try {
			WebElement href = connectedComponentsTable.findElement(By.id(id));
			href.click();
			return create(ProductComponentsConfigurationPage.class);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public ProductComponentsConfigurationPage configureComponent(String componentName) {
		List<WebElement> tableRows =
				connectedComponentsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":componentName"));			
			if (td.getText().contains(componentName))	{
				table.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":configureLink")).click();
				return create(ProductComponentsConfigurationPage.class);
			}
			i++;
		}
		return null;
	}
	
	public ProductComponentReplacePopUp replaceComponent(String componentName) {
		List<WebElement> tableRows =
				connectedComponentsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":componentName"));			
			if (td.getText().contains(componentName))	{
				table.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":replaceCmplLink")).click();
				return create(ProductComponentReplacePopUp.class);
			}
			i++;
		}
		return null;
	}
	
	public boolean checkReplaceLink() {
		List<WebElement> tableRows =
				connectedComponentsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			try {
				table.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":replaceCmplLink"));
			} catch(NoSuchElementException e) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	public ProductDataPage executeActionOnComponent(String componentName, ComponentWebAction action)
	{
		List<WebElement> tableRows = 
				connectedComponentsTable.findElements(By.cssSelector("tr.rich-table-row"));

		int i = 0;
		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.id("componentsConfigurationForm:body_configurationTable:" + i + ":componentName"));			
			
			if (td.getText().equals(componentName)) {
				table.findElement(By.id("componentsConfigurationForm:body_configurationTable:" + i + ":"+ action +"Link")).click();
				return this;
			}
			i++;
		}
		return null;
	}
	
	public Set<String> getConnectedComponentsNamesSet() {
		return new HashSet<String>(getConnectedComponentsNamesList());
	}
	
	public List<String> getConnectedComponentsNamesList() {
		List<WebElement> tableRows =
				connectedComponentsTable.findElements(By.cssSelector("tr.rich-table-row"));
		List<String> componentsList = new ArrayList<String>();	
		
		int i = 0;
		for (WebElement arr : tableRows) {
			WebElement td = arr.findElement(By.id("componentsConfigurationForm:body_configurationTable:"+ i +":componentName"));
			componentsList.add(td.getText());
			i++;
		}
		
		return componentsList;
	}
	
	public List<String> getSystemComponentsOfType(String componentType) {
		List<WebElement> tableRows  = componentsTable.findElements(By.cssSelector("tr.rich-table-row"));
		List<String> componentsList = new ArrayList<String>();	
		String name, version = "";
		
		int i = 0;
		for (WebElement table : tableRows)
		{
			WebElement componentTypeCol = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":column_componentType"));
			if (componentTypeCol.getText().equals(componentType))				
			{
				WebElement componentUserCreated = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":createNewLink"));
				if(!componentUserCreated.getAttribute("onclick").equals("return false;"))
				{
					name = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":componentPreview")).getText();
					version = table.findElement(By.id("componentsConfigurationForm:body_componentTable:"+ i +":column_componentVersion")).getText();
					
					if(version.contains(".")) {
						componentsList.add(name +" ["+ version +"]");
					} else {
						componentsList.add(name +" ["+ version +".0]");	
					}
				}
			}		
			i++;
		}
		return componentsList;
	}	
		
	public LegacyComponentInfoModule getLegacyComponentInfoModule() {
		return legacyComponentInfoModule;
	}
	
	public ProductDataPage expandComponentSelection() {
		componentSelection.click();
		return this;
	}
	
	public ProductDataPage clickAddComponents() {
		buttonAddComponents.click();
		return this;
	}
	
	public ProductConsolidatedViewPage clickSave() {
		topSaveButton.click();
		return create(ProductConsolidatedViewPage.class);
	}	
	
	public ProductConsolidatedViewPage clickCancel() {
		cancelButton.click();
		return create(ProductConsolidatedViewPage.class);
	}
	
}
