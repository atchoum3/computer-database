package com.excilys.cbd.ui;


public enum ChoicePageMenu {
	NEXT_PAGE("+"), PREVIOUS_PAGE("-"), QUIT("q");
	
	private final String value;
	
	private ChoicePageMenu(String s) {
		value = s;
	}
	
	public String getValue() {
		return value;
	}
	
	public static ChoicePageMenu fromPropertyName(String x) throws Exception {
		for (ChoicePageMenu currentType : ChoicePageMenu.values()) {
			if (x.equals(currentType.getValue())) {
				return currentType;
			}
		}
		throw new Exception("Unmatched Type: " + x);
	}
}
