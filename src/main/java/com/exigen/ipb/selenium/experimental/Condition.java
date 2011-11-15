package com.exigen.ipb.selenium.experimental;

/**
 * 
 * @author gzukas
 * @since 3.9
 * 
 * @param <T>  The type of object condition should be checked on.
 */
public interface Condition<T> {

	boolean apply(T to);
}
