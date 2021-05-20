package com.excilys.cbd.model;

import java.util.Objects;

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Company) {
			Company c = (Company) obj;
			if (id == c.getId()  && Objects.equals(name, c.getName())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
	
	@Override
	public String toString() {
		return "{id=" + id + " name=" + name + "}";
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
