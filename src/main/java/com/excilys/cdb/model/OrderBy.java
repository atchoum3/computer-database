package com.excilys.cdb.model;

public enum OrderBy {
	ASC,
	DESC;

	public OrderBy reverse() {
		if (this.equals(ASC)) {
			return OrderBy.DESC;
		} else {
			return OrderBy.ASC;
		}
	}
}
