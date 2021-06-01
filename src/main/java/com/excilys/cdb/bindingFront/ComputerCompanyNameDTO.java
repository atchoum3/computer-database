package com.excilys.cdb.bindingFront;

public class ComputerCompanyNameDTO {
	private String name, introduced, discontinued, companyName;
	private long id;
	
	private ComputerCompanyNameDTO(Builder builder) {
		name = builder.name;
		introduced = builder.introduced;
		discontinued = builder.discontinued;
		companyName = builder.companyName;
		id = builder.id;
	}
	
	public static class Builder {
		private String name = "", introduced = "", discontinued = "", companyName = "";
		private long id;
		
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
		
		public Builder withId(long id) {
			this.id = id;
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
	
	public long getId() {
		return id;
	}
}