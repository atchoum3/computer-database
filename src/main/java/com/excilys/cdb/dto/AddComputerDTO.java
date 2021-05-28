package com.excilys.cdb.dto;

public class AddComputerDTO {

	private String name, introduced, discontinued; 
	private int companyId;

	private AddComputerDTO(String name, String introduced, String discontinued, int companyId) {
		this.companyId = companyId;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.name = name;
	}
	
	public static class  Builder {
		private String name, introduced, discontinued; 
		private int companyId;
		
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
		
		public Builder companyId(int companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public AddComputerDTO build() {
			return new AddComputerDTO(name, introduced, discontinued, companyId);
		}
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
