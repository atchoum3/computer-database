package com.excilys.cdb.model;

public enum OrderBy {
	COMPUTER_NAME(2),
	INTRODUCED(3),
	DISCONTINUED(4),
	COMPANY_NAME(5);

	private int index;

	private OrderBy(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
