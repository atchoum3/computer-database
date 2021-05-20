package com.excilys.cbd.ui;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.excilys.cbd.model.Computer;

public class DisplayComputer extends DisplayTable {
	private static final String[] HEADER_COLUMN = {"id", "name", "introduced", "discontinued", "company_id"};
	private static final String PRINT_NO_VALUE = "NULL";
	
	private DisplayComputer() { } // static class, all method are static
	
	/**
	 * To display a collection in a table.
	 * @param collection the collection to display
	 */
	public static void displayCollection(Collection<Computer> collection) {
		//if there is no data
		if (collection.isEmpty()) {
			System.out.println("");
			System.out.println("No data ¯\\_(ツ)_/¯");
			System.out.println("");
		} else {
			int[] sizeColumn = maxCharEachColumn(collection);
			String introduced, discontinued, companyId;
			
			displayHeader(sizeColumn, HEADER_COLUMN);
			for (Computer c : collection) {
				
				//to print introduced value if it's null
				if (c.getIntroduced() == null) {
					introduced = PRINT_NO_VALUE;
				} else {
					introduced = c.getIntroduced().toString();
				}
				//to print discontinued value if it's null
				if (c.getDiscontinued() == null) {
					discontinued = PRINT_NO_VALUE;
				} else {
					discontinued = c.getDiscontinued().toString();
				}
				//to print companyId value if it's null
				if (c.getCompany() != null) {
					companyId = Long.toString(c.getCompany().getId());
				} else {
					companyId = PRINT_NO_VALUE;
				}
				
				displayLine(sizeColumn, Long.toString(c.getId()), c.getName(), 
						introduced, discontinued, companyId);
			}
			System.out.println("");
		}
	}
	
	/**
	 * To display one line on a table.
	 * @param c the computer to display
	 */
	public static void displayComputer(Computer c) {
		List<Computer> l = new ArrayList<>();
		l.add(c);
		displayCollection(l);
	}
	
	/**
	 * Method to get the max size of each column.
	 * @param collection the collection where computation will be made
	 * @return Array of int with the max char on each column
	 */
	private static int[] maxCharEachColumn(Collection<Computer> collection) {
		int[] array = new int[5];
		// get max length to print value of each column
		array[0] = collection.stream()
				.mapToLong(Computer::getId)
				.mapToObj(Long::toString)
				.mapToInt(String::length)
				.max().getAsInt();
		array[1] = collection.stream()
				.map(Computer::getName)
				.filter(Objects::nonNull)
				.mapToInt(String::length)
				.max().getAsInt();
		array[2] = collection.stream()
				.map(Computer::getIntroduced)
				.filter(Objects::nonNull)
				.map(LocalDate::toString)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		array[3] = collection.stream()
				.map(Computer::getDiscontinued)
				.filter(Objects::nonNull)
				.map(LocalDate::toString)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		array[4] = collection.stream()
				.filter(Objects::nonNull)
				.mapToLong(Computer::getId)
				.mapToObj(Long::toString)
				.mapToInt(String::length)
				.max().getAsInt();
		// get max between value length and header length
		for (int i = 0; i < array.length; i++) {
			array[i] = Math.max(array[i], HEADER_COLUMN[i].length());
		}
		return array;
	}
}
