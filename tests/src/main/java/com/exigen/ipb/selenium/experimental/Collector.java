package com.exigen.ipb.selenium.experimental;

import java.util.Collection;

import com.exigen.ipb.selenium.support.Extractor;

/**
 * Defines functionality that could be used to collect members of parent
 * container.
 * 
 * @author gzukas
 * @since 3.9
 * 
 * @param <T>  The type of container.
 * @param <K>  The type of member.
 */
public interface Collector<T, K> extends Extractor<T, Collection<K>> {

}