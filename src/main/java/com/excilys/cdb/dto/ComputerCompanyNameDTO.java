package com.excilys.cdb.dto;

public class ComputerCompanyNameDTO {
	private String name, introduced, discontinued, companyName;
	
	public ComputerCompanyNameDTO(String name, String introduced, String discontinued, String companyName) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyName() {
		return companyName;
	}
}
