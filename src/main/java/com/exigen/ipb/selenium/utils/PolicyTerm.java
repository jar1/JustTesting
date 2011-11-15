package com.exigen.ipb.selenium.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Available options to choose for Policy Term
 * @author mulevicius
 * @since 3.9
 */
public enum PolicyTerm {
	
	MONTHLY("Monthly"),
	ANNUAL("Annual"),
	SHORT_TERM("Short Term"),
	NOT_RESTRICTED("Not Restricted");

	private PolicyTerm(String name) {
		this.name = name;
	}
	
	private final String name;

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

}
