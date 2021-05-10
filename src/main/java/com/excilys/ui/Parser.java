package com.excilys.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Parser {
	
	
	public static Command parseLine(String line) {
		String[] lineSplit = line.split(line);
		
		if (lineSplit.length == 0) {
			//TODO
		}
		
		// command
		String commandName = lineSplit[0];
		
		
		Map<String, String> options = new HashMap<>();
		List<String> arguments = new ArrayList<>();
		boolean searchOption = true;
		
		for (int i = 1; i < lineSplit.length; i++) {
			
			// options
			if (searchOption ) {
				if (lineSplit[i].startsWith("--")) {
					String[] oneOption = lineSplit[i].substring(2).split("=",2);
					options.put(oneOption[0], oneOption[1]);
				} else {
					searchOption = false;
				}
			} else {
				// arguments
				//TODO
			}
		}
		return new Command(commandName, options, arguments);
	}
}
