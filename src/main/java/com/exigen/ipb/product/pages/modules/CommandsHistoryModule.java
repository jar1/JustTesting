package com.exigen.ipb.product.pages.modules;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.product.pages.AlternativeProductDataPage;
import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ElementUtils;

/**
 * Page module for commands history panel in alternative view.
 * @author mulevicius
 * @author gzukas
 * @since 3.9
 */
public class CommandsHistoryModule extends AbstractParentedWebPage<AlternativeProductDataPage> {

	@FindBy(id="commandsTogglePanel_header")
	private WebElement commandTogglePanel;
	
	@FindBy(id="commandsTogglePanel_switch_on")
	private WebElement expandedIndicator;
	
	@FindBy(id="commandsTable")
	private WebElement commandTable;

	public CommandsHistoryModule(WebDriver driver, PageConfiguration conf,
			AlternativeProductDataPage parentPage) {
		super(driver, conf, parentPage);
	}
	
	public void showHistory() {
		if (!isHistoryDisplayed()) {
			commandTogglePanel.click();
		}
	}
	
	public boolean isHistoryDisplayed() {
		return expandedIndicator.isDisplayed();
	}
	
	/**
	 * @return  Item values of command history table.
	 */
	public List<String> getRowsText() {
		return ElementUtils.getTextValues(getRows());
	}
	
	public int getHistorySize() {
		showHistory();
		return getRows().size();
	}
	
	private List<WebElement> getRows() {
		return commandTable.findElements(By.className("rich-table-row"));
	}
}
