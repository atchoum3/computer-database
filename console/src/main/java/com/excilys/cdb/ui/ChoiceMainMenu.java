package com.excilys.cdb.ui;

public enum ChoiceMainMenu {
	DISPLAY_ALL_COMPANIES(1), DELETE_COMPANY(2), DISPLAY_ALL_COMPUTERS(3), DISPLAY_COMPUTER(4),
	CREATE_COMPUTER(5), UPDATE_COMPUTER(6), DELETE_COMPUTER(7),  QUIT(8);

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
