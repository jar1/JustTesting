package com.exigen.ipb.product.pages.dialogs;

public class LookupInfoElement {
	
	private int index;
	
	private boolean selected;
	
	private String lookupName;
	
	private String templateName;
	
	private String description;
	
	public LookupInfoElement(int index, String lookupName, String templateName, String description) {
		this.index = index;
		this.lookupName = lookupName;
		this.templateName = templateName;
		this.description = description;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getLookupName() {
		return lookupName;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
