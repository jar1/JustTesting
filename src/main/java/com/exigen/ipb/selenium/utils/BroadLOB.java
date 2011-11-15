package com.exigen.ipb.selenium.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Available options to choose for Line Of Business
 * @author mulevicius
 * @since 3.9
 */
public enum BroadLOB {
	
	PLEASURE_AND_BUSINESS("Pleasure and Business"),
	COMMERSIAL_LINES("Commercial Lines"),
	FARM("Farm"),
	PERSONAL_LINES("Personal Lines"),
	SURETY("Surety");
	
	private BroadLOB(String name) {
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
