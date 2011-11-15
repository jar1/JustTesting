package com.exigen.ipb.product.pages.dialogs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.ProductWorkspacePage;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

public class WorkspaceConfigureEntityPopUp extends AbstractWebPage {
	
	@FindBy(id="entityViewConfigurationForm:saveButton")
	private WebElement saveButton;
	
	@FindBy(id="entityViewConfigurationForm:deleteButton")
	private WebElement deleteButton;
	
	@FindBy(id="entityViewConfigurationForm:undoButton")
	private WebElement undoButton;
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlecopyAlllink")
	private WebElement addAllButton;
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlecopyAll")
	private WebElement addAllShuttle;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttletbody")
	private WebElement componentsAvailabaleList;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttletltbody")
	private WebElement componentsSelectedList;		
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlecopyAll")
	private WebElement buttonAddAll;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlecopy")
	private WebElement buttonAdd;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttleremove")
	private WebElement buttonRemove;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttleremoveAll")
	private WebElement buttonRemoveAll;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlefirst")
	private WebElement buttonFirst;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttleup")
	private WebElement buttonUp;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttledown")
	private WebElement buttonDown;	
	
	@FindBy(id="entityViewConfigurationForm:entityViewConfigurationShuttlelast")
	private WebElement buttonLast;	
	
	public WorkspaceConfigureEntityPopUp(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	public WorkspaceConfigureEntityPopUp addComponent(String compName) {
		selectAvailableComponent(compName);
		buttonAdd.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp removeComponent(String compName) {
		selectSelectedComponent(compName);
		buttonRemove.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp addComponentsAll() {
		buttonAddAll.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp removeComponentsAll() {
		buttonRemoveAll.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp setFirst(String compName) {
		selectSelectedComponent(compName);
		buttonFirst.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp setLast(String compName) {
		selectSelectedComponent(compName);
		buttonLast.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp setUp(String compName) {
		selectSelectedComponent(compName);
		buttonUp.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp setDown(String compName) {
		selectSelectedComponent(compName);
		buttonDown.click();
		
		return this;
	}
	
	public WorkspaceConfigureEntityPopUp selectAvailableComponent(String compName) {		
		List<WebElement> tableRows =
				componentsAvailabaleList.findElements(By.cssSelector("tr"));

		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.cssSelector("td"));			
			if (td.getText().equals(compName))	{				
				td.click();

				return this;
			}
		}
		return null;
	}
	
	public List<String> getAvailableComponentsList() {		
		List<WebElement> tableRows =
				componentsAvailabaleList.findElements(By.cssSelector("tr td"));		
		List<String> componentList = new ArrayList<String>();

		for (WebElement arr : tableRows) {
			componentList.add(arr.getText());
		}
	
		return componentList;
	}
	
	public Set<String> getAvailableComponentsSet() {
		return new HashSet<String>(getAvailableComponentsList());
	}
	
	public WorkspaceConfigureEntityPopUp selectSelectedComponent(String compName) {		
		List<WebElement> tableRows =
				componentsSelectedList.findElements(By.cssSelector("tr"));

		for (WebElement table : tableRows) {
			WebElement td = table.findElement(By.cssSelector("td"));			
			if (td.getText().equals(compName))	{				
				td.click();

				return this;
			}
		}
		return null;
	}
	
	public List<String> getSelectedComponentsList() {		
		List<WebElement> tableRows =
				componentsSelectedList.findElements(By.cssSelector("tr td"));		
		List<String> componentList = new ArrayList<String>();

		for (WebElement arr : tableRows) {
			componentList.add(arr.getText());
		}
	
		return componentList;
	}	
	
	public WorkspaceConfigureEntityPopUp addAll() {
		try {
			addAllButton.click();
		} catch (NoSuchElementException e) {
			addAllShuttle.findElement(By.className("rich-list-shuttle-button")).click();
		}
		
		return create(WorkspaceConfigureEntityPopUp.class);
	}	
	
	public ProductWorkspacePage clickSave() {
		saveButton.click();
		return create(ProductWorkspacePage.class);
	}
}
