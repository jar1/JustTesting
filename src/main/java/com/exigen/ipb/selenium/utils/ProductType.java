package com.exigen.ipb.selenium.utils;

public enum ProductType {
	POLICY_PRODUCT("policyParent"),
	CLAIM_PRODUCT("claimsParent");
	
	private ProductType(String name) {
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
