package com.excilys.cdb.dto;

public class AddComputerDTO {

	private String companyId, introduced, discontinued, computerName;
	
	public AddComputerDTO(String computerName, String introduced, String discontinued, String companyId) {
		this.companyId = companyId;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.computerName = computerName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getComputerName() {
		return computerName;
	}
}
