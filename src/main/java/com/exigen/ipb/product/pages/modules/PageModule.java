package com.exigen.ipb.product.pages.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.exigen.ipb.selenium.pages.AbstractParentedWebPage;

/**
 * Used to mark a field on a Page Object to indicate encapsulated part of page.
 * 
 * @author gzukas
 * @since 3.9
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PageModule {

	/**
	 * Class of Page module.
	 * @return
	 */
	Class<? extends AbstractParentedWebPage<?>> value();

}