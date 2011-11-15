package com.exigen.ipb.product.pages.modules;

import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;

import com.exigen.ipb.selenium.pages.AbstractWebPage;
import com.exigen.ipb.selenium.pages.PageConfiguration;
import com.exigen.ipb.selenium.utils.ReflectionPageFactory;

/**
 * The PageModules class is used to instantiate page modules of parent page.
 * Instantiation considers properties that are annotated with PageModule
 * annotation.
 * 
 * Current implementation does not consider scope of module. Module elements are
 * allocated relatively to page context.
 * 
 * @author gzukas
 * @since 3.9
 */
public class PageModules {

	/**
	 * Instantiates page modules of specified page.
	 * 
	 * @param driver  The WebDriver object.
	 * @param conf  The configuration of page.
	 * @param page  Parent page that holds modules.
	 */
	public static void initModules(WebDriver driver, PageConfiguration conf, AbstractWebPage page) {
		for (Field field : page.getClass().getDeclaredFields()) {
			PageModule moduleAnnotation = field.getAnnotation(PageModule.class);
			if (moduleAnnotation == null) {
				continue;
			}
			
			try {
				Object module = ReflectionPageFactory.createPage(
						driver, conf, page, moduleAnnotation.value());
				
				field.setAccessible(true);
				field.set(page, module);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}
