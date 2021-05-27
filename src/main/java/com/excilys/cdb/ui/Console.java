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
		System.out.println("----------");
		System.out.println("2 : see all computers");
		System.out.println("3 <id> : see detail of one computer");
		System.out.println("4 : create one computer");
		System.out.println("5 <id> : update one computer");
		System.out.println("6 <id> : delete one computer");
		System.out.println("----------");
		System.out.println("7 : quit");
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
		