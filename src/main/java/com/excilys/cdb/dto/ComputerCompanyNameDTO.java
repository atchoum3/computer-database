package com.excilys.cdb.dto;

public class ComputerCompanyNameDTO {
	private String name, introduced, discontinued, companyName;
	
	private ComputerCompanyNameDTO(String name, String introduced, String discontinued, String companyName) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}
	
	public static class Builder {
		private String name, introduced, discontinued, companyName;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder introduced(String introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public Builder discontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public ComputerCompanyNameDTO build() {
			return new ComputerCompanyNameDTO(name, introduced, discontinued, companyName);
		}
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
