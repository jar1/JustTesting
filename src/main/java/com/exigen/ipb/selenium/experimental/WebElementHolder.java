package com.exigen.ipb.selenium.experimental;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Holds web element and reference to {@link org.openqa.selenium.support.FindBy}
 * annotation that was used to find this element.
 * 
 * @author gzukas
 * @since 3.9
 */
public class WebElementHolder {

	private FindBy by;
	
	private WebElement element;
	
	public WebElementHolder(FindBy by, WebElement element) {
		this.by = by;
		this.element = element;
	}
	
	public FindBy getBy() {
		return by;
	}
	
	public WebElement getElement() {
		return element;
	}
}
