package com.excilys.cdb.model;

public enum Order {
	ASC,
	DESC;

	public Order reverse() {
		if (this.equals(ASC)) {
			return Order.DESC;
		} else {
			return Order.ASC;
		}
	}
}
