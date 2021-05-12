package com.excilys.ui;

import java.util.Collection;
import java.util.Objects;

import com.excilys.model.Company;

public class DisplayCompany extends DisplayTable {
	private static final String[] HEADER_COLLECTION = {"id","name"};
	
	private DisplayCompany() {} // static class, all method are static
	
	public static void displayCollection(Collection<Company> collection) {
		if (collection.isEmpty()) {
			System.out.println("");
			System.out.println("No data ¯\\_(ツ)_/¯");
			System.out.println("");
		} else {
			int[] sizeColumn = maxCharEachColumn(collection);
			
			displayHeader(sizeColumn, HEADER_COLLECTION);
			for (Company c : collection) {
				displayLine(sizeColumn, Long.toString(c.getId()), c.getName());
			}
			System.out.println("");
		}
	}
	
	private static int[] maxCharEachColumn(Collection<Company> collection) {
		int[] array = new int[2];
		array[0] = collection.stream()
				.mapToLong(Company::getId)
				.mapToObj(Long::toString)
				.mapToInt(String::length)
				.max().getAsInt();
		array[1] = collection.stream()
				.map(Company::getName)
				.filter(Objects::nonNull)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		
		for (int i=0; i<array.length; i++) {
			array[i] = Math.max(array[i], HEADER_COLLECTION[i].length());
		}
		return array;
	}
}
