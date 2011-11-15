package com.exigen.ipb.selenium.utils;

/**
 * Action for component to take in selenium web tests
 * @author sstundzia
 * @since 3.9
 */

public enum ComponentWebAction {

	CONFIGURE("configure"),
	CONNECT_TO("connectTo"),
	REPLACE("replaceCmpl");
	
	private ComponentWebAction(String actionName) {
		this.actionName = actionName;
	}
	
	private final String actionName;
	
	@Override
	public String toString() {
		return actionName;
	}
	
}
