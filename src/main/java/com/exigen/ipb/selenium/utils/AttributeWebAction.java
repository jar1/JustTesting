package com.exigen.ipb.selenium.utils;

/**
 * Action for attribute to take in selenium web tests
 * @author sstundzia
 * @since 3.9
 */

public enum AttributeWebAction {

	EDIT_GENERAL_INFO("selectedAttributeGeneralInfoPopup"),
	EDIT_LOOKUP_INFO("alternateLookupSearchPopup"),
	EDIT_CONSTRAINTS("selectedAttributeConstraintsPopup");
	
	private AttributeWebAction(String actionName) {
		this.actionName = actionName;
	}
	
	private final String actionName;
	
	@Override
	public String toString() {
		return actionName;
	}
	
}
