package com.excilys.cdb.ui;

public enum ChoiceMainMenu {
	DISPLAY_ALL_COMPANIES(1), DISPLAY_ALL_COMPUTERS(2), DISPLAY_COMPUTER(3), 
	CREATE_COMPUTER(4), UPDATE_COMPUTER(5), DELETE_COMPUTER(6), QUIT(7);
	
	private final int value;
	
	ChoiceMainMenu(int i) {
		value = i;
	}
	
	public int getValue() {
		return value;
	}
	
	public static boolean isChoiceMenu(int i) {
		return (i > 0 && i < ChoiceMainMenu.values().length);
	}
}
