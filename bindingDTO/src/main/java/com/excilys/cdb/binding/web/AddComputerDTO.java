package com.excilys.cdb.binding.web;

public class AddComputerDTO {

	protected String name;
	protected String introduced;
	protected String discontinued;
	protected long companyId;

	public AddComputerDTO() {

	}

	protected AddComputerDTO(Builder<?> builder) {
		this.companyId = builder.companyId;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.name = builder.name;
	}

	public static class Builder<T extends Builder<T>> {
		private String name = "", introduced = "", discontinued = "";
		private long companyId;

		public Builder(String name) {
			if (name == null)  {
				name = "";
			}
			this.name = name;
		}

		public T withIntroduced(String introduced) {
			if (introduced == null)  {
				introduced = "";
			}
			this.introduced = introduced;
			return (T) this;
		}

		public T withDiscontinued(String discontinued) {
			if (discontinued == null)  {
				discontinued = "";
			}
			this.discontinued = discontinued;
			return (T) this;
		}

		public T withCompanyId(long l) {
			this.companyId = l;
			return (T) this;
		}

		public AddComputerDTO build() {
			return new AddComputerDTO(this);
		}
	}

	public long getCompanyId() {
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

	@Override
	public String toString() {
		return "AddComputerDTO [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


}
