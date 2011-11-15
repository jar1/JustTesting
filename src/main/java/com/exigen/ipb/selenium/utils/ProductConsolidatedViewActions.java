package com.exigen.ipb.selenium.utils;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EnumType;

public enum ProductConsolidatedViewActions {
	
	UpdateProductProperties("Update Product Properties"),
	UpdateProductActions("Update Product Actions"),
	UpdateProductData("Update Product Data"),
	ConfigureWorkspace("Configure Workspace"),
	ConfigureRules("Configure Rules"),
	ConfigureAllRuleGroups("Configure All Rule Groups"),
	ConfigureRulesForGroup("Configure Rules For Group"),
	ExportProduct("Export Product"),
	ImportBusinessRules("Import Business Rules"),
	ExportBusinessRules("Export Business Rules"),
	ImportGlobalRules("Import Global Rules"),
	ViewRuleSets("View Rule Sets"),
	Report("Reports"),
	ExportResources("Export Resources"),
	AlternativeUpdateData("Alternative Update Data"),
	DeactivateProduct("Deactivate Product");

	private ProductConsolidatedViewActions(String name) {
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
