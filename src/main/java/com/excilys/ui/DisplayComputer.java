package com.excilys.ui;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.excilys.model.Computer;

public class DisplayComputer extends DisplayTable {
	private static final String[] HEADER_COLLECTION = {"id","name","introduced","discontinued","company_id"};
	
	private DisplayComputer() {} // static class, all method are static
	
	public static void displayCollection(Collection<Computer> collection) {
		int[] sizeColumn = maxCharEachColumn(collection);
		String introduced, discontinued;
		
		displayHeader(sizeColumn, HEADER_COLLECTION);
		for (Computer c : collection) {
			 
			if (c.getIntroduced() == null) {
				introduced = "NULL";
			} else {
				introduced = c.getIntroduced().toString();
			}
			
			if (c.getDiscontinued() == null) {
				discontinued = "NULL";
			} else {
				discontinued = c.getDiscontinued().toString();
			}
			
			displayLine(sizeColumn, Long.toString(c.getId()), c.getName(), 
					introduced, discontinued,
					Long.toString(c.getIdCompany())
			);
		}
		System.out.println("");
	}
	
	public static void displayComputer(Computer c) {
		List<Computer> l = new ArrayList<>();
		l.add(c);
		displayCollection(l);
	}
	
	private static int[] maxCharEachColumn(Collection<Computer> collection) {
		int[] array = new int[5];
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
				.map(LocalDateTime::toString)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		array[3] = collection.stream()
				.map(Computer::getDiscontinued)
				.filter(Objects::nonNull)
				.map(LocalDateTime::toString)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		array[4] = collection.stream()
				.filter(Objects::nonNull)
				.mapToLong(Computer::getId)
				.mapToObj(Long::toString)
				.mapToInt(String::length)
				.max().getAsInt();
		return array;
	}
}
