package com.excilys.cdb.controler.web.validator;

import java.util.HashMap;
import java.util.Map;

import com.excilys.cdb.controler.web.ComputerServlet;
import com.excilys.cdb.dto.AddComputerDTO;

public class AddComputerValidator {
	private Map<String, String> errors;
	
	
	public AddComputerValidator() {
		errors = new HashMap<>();
	}
	
	
	public Map<String, String> validate(AddComputerDTO computerDTO) {
		errors.clear();
		validateName(computerDTO.getName());
		return errors;
	}
	
	private void validateName(String name) {
		if (name.isEmpty()) {
			errors.put(ComputerServlet.INPUT_COMPUTER_NAME, "Computer name cannot be empty.");
		}
	}
	
}
