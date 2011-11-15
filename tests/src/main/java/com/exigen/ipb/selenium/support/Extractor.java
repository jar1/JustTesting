package com.exigen.ipb.selenium.support;

/**
 * An interface used to mark a class that is capable of retrieving specific
 * value of container. This interface is used by
 * {@link com.exigen.ipb.selenium.utils.ExtractTools} class.
 * 
 * @author gzukas
 * @since 3.9
 */
public interface Extractor<T, K> {
	
	/**
	 * Extracts value of given component.
	 * 
	 * @param container  The component object.
	 * @return  Extracted value.
	 */
	K extract(T container);
}