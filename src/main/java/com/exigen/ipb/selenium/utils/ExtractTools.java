package com.exigen.ipb.selenium.utils;

import java.util.ArrayList;
import java.util.List;

import com.exigen.ipb.selenium.support.Extractor;

/**
 * Utility class that is useful for extracting values of component using
 * specific extracting strategy.
 * 
 * @author gzukas
 * @since 3.9
 */
public class ExtractTools {

	/**
	 * Extracts value of component.
	 * 
	 * @param component  The component.
	 * @param extractor  Extraction strategy.
	 * @return  Extracted value.
	 */
	public static <T, K> K extract(T component, Extractor<T, K> extractor) {
		return extractor.extract(component);
	}
	
	/**
	 * Extracts values of components.
	 * 
	 * @param components  List of components.
	 * @param extractor  Extraction strategy.
	 * @return  Extracted values.
	 */
	public static <T, K> List<K> extract(List<T> components, Extractor<T, K> extractor) {
		List<K> values = new ArrayList<K>();
		for (T component : components) {
			values.add(extractor.extract(component));
		}
		return values;
	}

}
