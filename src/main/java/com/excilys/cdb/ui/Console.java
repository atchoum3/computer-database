package com.excilys.cdb.ui;

import com.excilys.cdb.model.Page;

public class Console {
	private static Console instance;

	private Console() { }

	public static Console getInstance() {
		if (instance == null) {
			instance = new Console();
		}
		return instance;
	}

	/**
	 * display all possible choices.
	 */
	public void displayMenuChoice() {
		System.out.println("Possibilities :");
		System.out.println("1 : see all companies");
		System.out.println("2 : <id> delete a company");
		System.out.println("----------");
		System.out.println("3 : see all computers");
		System.out.println("4 <id> : see detail of a computer");
		System.out.println("5 : create a computer");
		System.out.println("6 <id> : update a computer");
		System.out.println("7 <id> : delete a computer");
		System.out.println("----------");
		System.out.println("8 : quit");
		System.out.println("");
	}

	/**
	 * display the footer page of an table.
	 * @param page object Page to display the index of the current page
	 */
	public void displayFooterPage(Page page) {
		System.out.println("- : previous page");
		System.out.println("+ : next page");
		System.out.println("q : to quit");
		System.out.println("current page: " + page.getCurrentPage());

	}
}
