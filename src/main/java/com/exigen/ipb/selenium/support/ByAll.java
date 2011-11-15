package com.exigen.ipb.selenium.support;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

/**
 * Mechanism used to locate elements within a document using a list of lookups.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ByAll extends By {

	private By[] bys;
	
	public ByAll(By... bys) {
		this.bys = bys;
	}

	@Override
	public List<WebElement> findElements(SearchContext context) {
		List<WebElement> elements = new ArrayList<WebElement>();
		for (By by : bys) {
			elements.addAll(context.findElements(by));
		}
		return elements;
	}
}
