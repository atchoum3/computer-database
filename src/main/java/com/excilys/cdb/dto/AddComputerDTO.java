package com.excilys.cdb.dto;

public class AddComputerDTO {

	private String name, introduced, discontinued; 
	private int companyId;

	public AddComputerDTO(String name, String introduced, String discontinued, int companyId) {
		this.companyId = companyId;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.name = name;
	}

	public int getCompanyId() {
		return companyId;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getName() {
		return name;
	}
}
