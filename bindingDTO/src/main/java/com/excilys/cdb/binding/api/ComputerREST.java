package com.excilys.cdb.binding.api;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.annotation.Generated;

public class ComputerREST {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate disconduced;
	private CompanyREST company;

	private ComputerREST(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.disconduced = builder.disconduced;
		this.company = builder.company;
	}

	public ComputerREST() { }



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDisconduced() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return disconduced;
	}

	public void setDisconduced(LocalDate disconduced) {
		this.disconduced = disconduced;
	}

	public CompanyREST getCompany() {
		return company;
	}

	public void setCompany(CompanyREST company) {
		this.company = company;
	}



	/**
	 * Creates builder to build {@link ComputerREST}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ComputerREST}.
	 */
	public static final class Builder {
		private long id;
		private String name;
		private LocalDate introduced;
		private LocalDate disconduced;
		private CompanyREST company;

		private Builder() {
		}

		public Builder withId(long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder withDisconduced(LocalDate disconduced) {
			this.disconduced = disconduced;
			return this;
		}

		public Builder withCompany(CompanyREST company) {
			this.company = company;
			return this;
		}

		public ComputerREST build() {
			return new ComputerREST(this);
		}
	}

}
