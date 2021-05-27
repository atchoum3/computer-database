package com.excilys.cdb.ui;

/**
 * Commune method to display table on a command line.
 *
 */
public class DisplayTable {
	protected static final int SIZE_WORD_NULL = 4;
	
	/**
	 * To display a line well aligned of a table.
	 * @param sizeColumn size of each column
	 * @param values values in each column
	 */
	protected static void displayLine(int[] sizeColumn, String... values) {
		for (int i = 0; i < sizeColumn.length; i++) {
			System.out.format("| %-" + sizeColumn[i] + "s ", values[i]);
		}
		System.out.println("|");
	}
	
	/**
	 * To display the header of a table.
	 * @param sizeColumn size of each column
	 * @param values in each column
	 */
	protected static void displayHeader(int[] sizeColumn, String[] values) {
		displayLine(sizeColumn, values);
		System.out.print("+");
		for (int i = 0; i < sizeColumn.length; i++) {
			for (int j = 0; j < sizeColumn[i] + 2; j++) {
				System.out.print("-");
			}
			System.out.print("+");
		}
		System.out.println();
	}
}
