package com.excilys.cbd.model;

import java.time.LocalDate;

public class Computer {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private long idCompany;
	
	public Computer(long id, String name, LocalDate introduced, 
			LocalDate discontinued, long idCompany) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.idCompany = idCompany;
	}
	
	public Computer(String name, LocalDate introduced, 
			LocalDate discontinued, long idCompany) {
		this(-1, name, introduced, discontinued, idCompany);
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

	public long getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(long idCompany) {
		this.idCompany = idCompany;
	}
}
