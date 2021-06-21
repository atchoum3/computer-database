package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Objects;

public class Computer {
	private static final long DEFAULT_ID = -1;

	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	private Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public static class Builder {
		private long id;
		private String name = "";
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;

		public Builder(String name) {
			id = DEFAULT_ID;
			if (name == null) {
				name = "";
			}
			this.name = name;
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public Builder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder withCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Computer) {
			Computer c = (Computer) obj;
			if (Objects.equals(name, c.getName()) && Objects.equals(introduced, c.getIntroduced())
					&& Objects.equals(discontinued, c.getDiscontinued())
					&& Objects.equals(company, c.getCompany())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, introduced, discontinued, company);
	}

	@Override
	public String toString() {
		return "{id=" + id + " name=" + name + " introduced=" + introduced + " discontinued=" + discontinued + " company=" + company + "}";
	}

	/// setters & getters

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}
}
