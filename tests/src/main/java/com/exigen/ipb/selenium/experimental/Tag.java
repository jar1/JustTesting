package com.exigen.ipb.selenium.experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Generally used to tag fields.
 * 
 * @author gzukas
 * @since 3.9
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Tag {

	/**
	 * Names of tags that field should belong to.
	 * @return
	 */
	String[] value();

}