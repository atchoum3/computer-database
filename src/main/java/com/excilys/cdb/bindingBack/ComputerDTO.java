package com.excilys.cdb.bindingBack;

public class ComputerDTO {
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;

	private ComputerDTO(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public static class Builder {
		private long id;
		private String name = "";
		private String introduced;
		private String discontinued;
		private CompanyDTO company;

		public Builder(String name) {
			if (name == null) {
				name = "";
			}
			this.name = name;
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public Builder withIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder withCompany(CompanyDTO company) {
			this.company = company;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComputerDTO other = (ComputerDTO) obj;
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", company=" + company + "]";
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public Long getCompanyId() {
		if (company != null) {
			return company.getId();
		} else {
			return null;
		}
	}
}