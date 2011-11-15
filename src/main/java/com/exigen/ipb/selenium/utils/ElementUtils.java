package com.exigen.ipb.selenium.utils;

import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.exigen.ipb.selenium.experimental.Condition;
import com.exigen.ipb.selenium.experimental.ConditionalTaggedCollector;
import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.support.Extractor;

/**
 * An utility class that provides helper functionality to cope with web
 * elements.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ElementUtils {
	
	/**
	 * Determines whether given web element exists and is displayed.
	 * 
	 * @param element  The web element.
	 * @return  True if element exists and is displayed, otherwise - false.
	 */
	public static boolean isDisplayed(WebElement element) {
		try {
			return element != null && element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	/**
	 * Determines whether locator locates any element in given context.
	 * 
	 * @param context  Search context.
	 * @param by  Locator.
	 * @return  True if any element is located, otherwise - false.
	 */
	public static boolean exists(SearchContext context, By by) {
		try {
			context.findElement(by);
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Selects or deselects web element. Useful for such controls as checkbox.
	 * 
	 * @param element  Web element to be involved.
	 * @param selected  Selection indicator.
	 */
	public static void setSelected(WebElement element, boolean selected) {
		if (selected != element.isSelected()) {
			element.click();
		}
	}
	
	public static List<String> getOptions(Select select) {
		return ExtractTools.extract(select, new SelectOptionsTextExtractor());
	}

	/**
	 * Returns text values of elements found.
	 * 
	 * @param parent  Parent element.
	 * @param by  Search criteria.
	 * @return  Text values of child elements that conform to given criteria.
	 */
	public static List<String> getTextValues(WebElement parent, By by) {
		return getTextValues(parent.findElements(by));
	}
	
	/**
	 * Returns text values of elements provided.
	 * 
	 * @param elements  List of elements.
	 * @return  Text values.
	 */
	public static List<String> getTextValues(List<WebElement> elements) {
		return ExtractTools.extract(elements, new TextExtractor());
	}
	
	/**
	 * Returns values of attributes, of elements found.
	 * 
	 * @param parent  Parent element.
	 * @param by  Search criteria.
	 * @param attribute  The name of attribute.
	 * @return  Attribute values of child elements that conform to given criteria.
	 */
	public static List<String> getAttributeValues(WebElement parent, By by, String attribute) {
		return getAttributeValues(parent.findElements(by), attribute);
	}
	
	/**
	 * Returns attribute values of elements provided.
	 * 
	 * @param elements  List of elements.
	 * @param attribute  The name of attribute.
	 * @return  Attribute values.
	 */
	public static List<String> getAttributeValues(List<WebElement> elements, String attribute) {
		return ExtractTools.extract(elements, new AttributeExtractor(attribute));
	}
	
	/**
	 * Returns value of HTML input element.
	 * 
	 * @param inputElement  HTML input element.
	 * @return  Input value.
	 */
	public static String getInputValue(WebElement inputElement) {
		return ExtractTools.extract(inputElement, new AttributeExtractor("value"));
	}
	
	/**
	 * Changes value of input web element. It is same as writing:
	 * <pre>
	 * inputElement.clear();
	 * inputElement.sendKeys(keys);
	 * </pre>
	 * 
	 * @param inputElement
	 * @param keys
	 */
	public static void setInputValue(WebElement inputElement, CharSequence... keys) {
		inputElement.clear();
		inputElement.sendKeys(keys);
	}
		
	/**
	 * Determines whether web element is marked with specific style class.
	 * 
	 * @param element  Web element.
	 * @param styleClassName  The name of style class.
	 * @return  True if web element is using given style class, otherwise - false.
	 */
	public static boolean containsStyleClass(WebElement element, String styleClassName) {
		String[] classNames = element.getAttribute("class").split(" ");
		for (String className : classNames) {
			if (className.equals(styleClassName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a text of element regardless its visibility.
	 * 
	 * @param element  The element of which text will be retrieved.
	 * @param driver  The instance of web driver.
	 * @return  Text of element.
	 */
	public static String getText(WebElement element, WebDriver driver) {
		return (String) ((JavascriptExecutor)driver).executeScript("return arguments[0].innerHTML", element);
	}
	
	/**
	 * <b>This method uses experimental functionality. Method may change in the future.</b>
	 * 
	 * Returns a list of invisible web elements of parent page. Only tagged elements
	 * are considered. Visibility of element is determined by checking whether it is displayed by calling
	 * <code>element.isDisplayed()</code>
	 * 
	 * @param page  The parent page of elements.
	 * @param tag  The name of tag.
	 * @return  A collection of invisible web elements.
	 */
	public static Collection<WebElement> getInvisibleElements(AbstractWebPage page, String tag) {
		return new ConditionalTaggedCollector<AbstractWebPage, WebElement>(
				WebElement.class, new InvisibleCondition(), tag).extract(page);
	}

	/**
	 * <b>This method uses experimental functionality. Method may change in the future.</b>
	 * 
	 * Performs same operation as {@link #getInvisibleElements(String, AbstractWebPage)}.
	 * The difference is that this method considers web elements that have no tags.
	 * Same as calling <pre>ElementUtils.getInvisibleElements(null, page)</pre>
	 * 
	 * @param page  The parent page of elements.
	 * @return
	 */
	public static Collection<WebElement> getInvisibleElements(AbstractWebPage page) {
		return getInvisibleElements(page, null);
	}
	
}

/**
 * Extracts text value of current web element.
 * 
 * @author gzukas
 * @since 3.9
 */
class TextExtractor implements Extractor<WebElement, String> {

	public String extract(WebElement element) {
		return element.getText();
	}
}

/**
 * Extracts attribute value of given web element.
 * 
 * @author gzukas
 * @since 3.9
 */
class AttributeExtractor implements Extractor<WebElement, String> {

	private String attributeName;
	
	public AttributeExtractor(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public String extract(WebElement element) {
		return element.getAttribute(attributeName);
	}
}

/**
 * Extracts all available options in Select and returns as string list.
 * 
 * @author mulevicius
 * @since 3.9
 */
class SelectOptionsTextExtractor implements Extractor<Select, List<String>> {
	public List<String> extract(Select select) {
		return ElementUtils.getTextValues(select.getOptions());
	}
}

/**
 * Condition of web elements' visibility.
 * 
 * @author gzukas
 * @since 3.9
 */
class InvisibleCondition implements Condition<WebElement> {

	public boolean apply(WebElement to) {
		return !ElementUtils.isDisplayed(to);
//		try {
//			return !to.isDisplayed();
//		}
//		catch (Exception e) {
//			return true;
//		}
	}
	
}