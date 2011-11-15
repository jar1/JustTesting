package com.exigen.ipb.product.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;

/**
 * Page Object that represents 'View Rule Sets' screen.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ViewRuleSetsPage extends AbstractWebPage {

	private static final String EXPIRATION_DATE_COLUMN_FORMAT =
			"productForm:body_productDetailsMainTable:%d:column_expirationDate";

	@FindBy(id="productForm:body_productDetailsMainTable")
	private WebElement ruleSetsTable;
	
	public ViewRuleSetsPage(WebDriver driver, PageConfiguration conf) {
		super(driver, conf);
	}
	
	/**
	 * Returns expiration date that is retrievable from specified row.
	 * 
	 * @param index  The index of row.
	 * @return  Value of expiration date.
	 */
	public String getExpirationDate(int index) {
		String columnId = String.format(EXPIRATION_DATE_COLUMN_FORMAT, index);
		return ruleSetsTable.findElement(By.id(columnId)).getText();
	}

}
