package com.excilys.model;

import java.time.LocalDateTime;

public class Computer {
	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private long idCompany;
	
	public Computer(long id, String name, LocalDateTime introduced, 
			LocalDateTime discontinued, long idCompany) {
		this.id = id;
		this.setName(name);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
		this.setIdCompany(idCompany);
	}
	
	@Override
	public String toString() {
		return "" + id + "," + name + "," + introduced + "," + discontinued + "," + idCompany;
	}
	
	/// setters and getters
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDateTime introduced) {
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			throw new IllegalArgumentException(
					"The discontinued date have to be greater than introduced date");
		}
		this.introduced = introduced;
	}
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDateTime discontinued) {
		if (discontinued != null && introduced != null && discontinued.isBefore(introduced)) {
			throw new IllegalArgumentException(
					"The discontinued date have to be greater than introduced date");
		}
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
