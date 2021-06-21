package com.excilys.cdb.model;

public enum OrderBy {
	COMPUTER_NAME("computer.name"),
	INTRODUCED("computer.introduced"),
	DISCONTINUED("computer.discontinued"),
	COMPANY_NAME("company.name");

	private String value;

	private OrderBy(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
