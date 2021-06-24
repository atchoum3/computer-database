package com.excilys.cdb.binding.web;

public class CompanyDTO {
	private String name;
	private long id;

	public CompanyDTO() { }

	public CompanyDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
