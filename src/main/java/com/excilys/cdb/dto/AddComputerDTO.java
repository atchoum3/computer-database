package com.excilys.cdb.dto;

public class AddComputerDTO {

	private String name, introduced, discontinued; 
	private int companyId;

	private AddComputerDTO(Builder builder) {
		this.companyId = builder.companyId;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.name = builder.name;
	}
	
	public static class  Builder {
		private String name = "", introduced = "", discontinued = ""; 
		private int companyId;
		
		public Builder(String name) {
			if (name == null)  {
				name = "";
			}
			this.name = name;
		}
		
		public Builder withIntroduced(String introduced) {
			if (introduced == null)  {
				introduced = "";
			}
			this.introduced = introduced;
			return this;
		}
		
		public Builder withDiscontinued(String discontinued) {
			if (discontinued == null)  {
				discontinued = "";
			}
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder withCompanyId(int companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public AddComputerDTO build() {
			return new AddComputerDTO(this);
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
