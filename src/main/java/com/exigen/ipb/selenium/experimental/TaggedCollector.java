package com.exigen.ipb.selenium.experimental;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Collects fields that are tagged with certain tag. To retrieve fields
 * that are not tagged, pass <code>null</code> as <code>tagName</code>. For
 * tagging you must use {@link com.exigen.ipb.selenium.experimental.Tag}
 * annotation.
 * 
 * @author gzukas
 * @since 3.9
 */
public class TaggedCollector<T, K> implements Collector<T, K> {

	protected String tagName;
	
	protected Class<K> memberClass;

	public TaggedCollector(Class<K> memberClass) {
		this.memberClass = memberClass;
	}

	public TaggedCollector(Class<K> memberClass, String tagName) {
		this(memberClass);
		this.tagName = tagName;
	}

	@SuppressWarnings(value = "unchecked")
	public Collection<K> extract(T container) {
		Collection<K> members = new ArrayList<K>();
		for (Field field : container.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Tag tag = field.getAnnotation(Tag.class);
			try {
				boolean isMember = memberClass.isAssignableFrom(field.getType());
				if (isMember && hasTag(tag, tagName)) {
					members.add((K) field.get(container));
				}
			} catch (Exception e) {
			}
		}
		return members;
	}

	/**
	 * Determines whether a set of tags contains specified tag.
	 * 
	 * @param tag  The set of tags.
	 * @param name  The name of tag.
	 * @return  True if contains, otherwise - false.
	 */
	private boolean hasTag(Tag tag, String name) {
		if (tag == null && tagName == null) {
			return true;
		}

		for (String value : tag.value()) {
			if (value.equals(name)) {
				return true;
			}
		}

		return false;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
