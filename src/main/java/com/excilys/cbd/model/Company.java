package com.excilys.cbd.model;

public class Company {
	private long id;
	private String name;
	
	public Company(long id) {
		this(id, "");
	}
	
	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	/// getters & setters
	
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
}
