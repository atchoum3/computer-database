package com.excilys.cdb.dto;

public class ComputerCompanyNameDTO {
	private String name, introduced, discontinued, companyName;
	
	private ComputerCompanyNameDTO(Builder builder) {
		super();
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyName = builder.companyName;
	}
	
	public static class Builder {
		private String name = "", introduced = "", discontinued = "", companyName = "";
		
		public Builder(String name) {
			if (name == null) {
				name = "";
			}
			this.name = name;
		}
		
		public Builder withIntroduced(String introduced) {
			if (introduced == null) {
				introduced = "";
			}
			this.introduced = introduced;
			return this;
		}
		
		public Builder withDiscontinued(String discontinued) {
			if (discontinued == null) {
				discontinued = "";
			}
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder withCompanyName(String companyName) {
			if (companyName == null) {
				companyName = "";
			}
			this.companyName = companyName;
			return this;
		}
		
		public ComputerCompanyNameDTO build() {
			return new ComputerCompanyNameDTO(this);
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
