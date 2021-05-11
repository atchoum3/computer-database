package com.excilys.ui;

public class DisplayTable {
	
	protected static void displayLine(int[] sizeColumn, String... values) {
		for (int i=0; i<sizeColumn.length; i++) {
			System.out.format("| %-"+sizeColumn[i]+"s ", values[i]);
		}
		System.out.println("|");
	}
	
	protected static void displayHeader(int[] sizeColumn, String[] values) {
		displayLine(sizeColumn, values);
		System.out.print("+");
		for (int i=0; i<sizeColumn.length; i++) {
			for (int j=0; j<sizeColumn[i] +2; j++) {
				System.out.print("-");
			}
			System.out.print("+");
		}
		System.out.println();
	}
}
