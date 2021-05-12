package com.excilys.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.excilys.exception.ComputerDateGreaterLessThan1970;

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
	
	public Computer(String name, LocalDateTime introduced, 
			LocalDateTime discontinued, long idCompany) {
		this(-1, name, introduced, discontinued, idCompany);
	}
	
	@Override
	public String toString() {
		return "" + id + "," + name + "," + introduced + "," + discontinued + "," + idCompany;
	}
	
	/**
	 * This method check if a LocalDateTime is before the 1st January 1970. 
	 * This method is useful to know if the value can be or not converted in Timestamp.
	 * @param date the date to check
	 * @throws ComputerDateGreaterLessThan1970 if the date is before the 1st January 1970
	 */
	public static void checkDateBefore1970(LocalDateTime date) 
			throws ComputerDateGreaterLessThan1970 {
		if (date != null && date.isBefore(LocalDateTime.ofEpochSecond(0L, 0, ZoneOffset.UTC))) {
			throw new ComputerDateGreaterLessThan1970(
					"The date need to be greater than the 1970-01-01");
		}
	}
	
	/**
	 * Check if the introduced is before the discontinued date. If this is the case 
	 * throw IllegalArgumentException
	 * @param introduced the date to when the computer was introduced
	 * @param discontinued the date to when the computer was discontinued
	 */
	private void checkIntroducedlessThanDisontinued(LocalDateTime introduced, 
			LocalDateTime discontinued) {
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			throw new IllegalArgumentException(
					"The discontinued date have to be greater than introduced date");	
		}
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
	/**
	 * setter
	 * @param introduced
	 * @throws ComputerDateGreaterLessThan1970 if the time is before the 1st January 1970
	 * @throws IllegalArgumentException if introduced date is after discontinued date
	 */
	public void setIntroduced(LocalDateTime introduced) 
			throws ComputerDateGreaterLessThan1970, IllegalArgumentException {
		checkDateBefore1970(introduced);
		checkIntroducedlessThanDisontinued(introduced, discontinued);
		this.introduced = introduced;
	}
	
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	/**
	 * 
	 * @param discontinued
	 * @throws ComputerDateGreaterLessThan1970 if the time is before the 1st January 1970
	 * @throws IllegalArgumentException if introduced date is after discontinued date
	 */
	public void setDiscontinued(LocalDateTime discontinued) 
			throws ComputerDateGreaterLessThan1970, IllegalArgumentException {
		checkDateBefore1970(discontinued);
		checkIntroducedlessThanDisontinued(introduced, discontinued);
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
