package com.excilys.ui;

import java.util.List;
import java.util.Map;

public class Command {
	private String name;
	private Map<String, String> options;
	private List<String> arguments;
	
	public Command(String name, Map<String, String> options, List<String> arguments) {
		this.name = name;
		this.options = options;
		this.arguments = arguments;
	}
	
	
	/// getters
	public String getName() {
		return name;
	}
	public Map<String, String> getOptions() {
		return options;
	}
	public List<String> getArguments() {
		return arguments;
	}
}
