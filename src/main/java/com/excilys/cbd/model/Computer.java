package com.excilys.cbd.model;

import java.time.LocalDate;

public class Computer {
	private static final long DEFAULT_ID = -1;
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	private Computer(long id, String name, LocalDate introduced, 
			LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	private Computer(String name, LocalDate introduced, 
			LocalDate discontinued, Company company) {
		this(DEFAULT_ID, name, introduced, discontinued, company);
	}
	
	public static class Builder {
		private long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public Builder(String name) {
			this.id = DEFAULT_ID;
			this.name = name;
		}
		
		public Builder(long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public Builder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public Builder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public Builder company(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(id, name, introduced, discontinued, company);
		}
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
