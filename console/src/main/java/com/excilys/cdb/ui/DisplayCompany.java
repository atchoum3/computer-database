package com.excilys.cdb.ui;

import java.util.Collection;
import java.util.Objects;

import com.excilys.cdb.bindingFront.CompanyDTO;

public class DisplayCompany extends DisplayTable {
	private static final String[] HEADER_COLUMN = {"id", "name"};
	
	private DisplayCompany() { } // static class, all method are static
	
	/**
	 * To display a collection in a table.
	 * @param collection the collection to display
	 */
	public static void display(Collection<CompanyDTO> collection) {
		if (collection.isEmpty()) {
			System.out.println("");
			System.out.println("No data ¯\\_(ツ)_/¯");
			System.out.println("");
		} else {
			int[] sizeColumn = maxCharEachColumn(collection);
			
			displayHeader(sizeColumn, HEADER_COLUMN);
			for (CompanyDTO c : collection) {
				displayLine(sizeColumn, Long.toString(c.getId()), c.getName());
			}
			System.out.println("");
		}
	}
	
	/**
	 * Method to get the max size of each column.
	 * @param collection the collection where computation will be made
	 * @return Array of int with the max char on each column
	 */
	private static int[] maxCharEachColumn(Collection<CompanyDTO> collection) {
		int[] array = new int[2];
		// get max length to print value of each column
		array[0] = collection.stream()
				.mapToLong(CompanyDTO::getId)
				.mapToObj(Long::toString)
				.mapToInt(String::length)
				.max().getAsInt();
		array[1] = collection.stream()
				.map(CompanyDTO::getName)
				.filter(Objects::nonNull)
				.mapToInt(String::length)
				.max().orElse(SIZE_WORD_NULL);
		// get max between value length and header length
		for (int i = 0; i < array.length; i++) {
			array[i] = Math.max(array[i], HEADER_COLUMN[i].length());
		}
		return array;
	}
}