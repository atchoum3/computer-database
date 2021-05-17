package com.excilys.cbd.target;

import com.excilys.cbd.controler.ConsoleControler;
import com.excilys.cbd.persistence.Database;

public class LaunchConsole {
	public static void main(String[] args) {
		Database.getInstance().connection();
		
		ConsoleControler.getInstance().start();
	}
}
