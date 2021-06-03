package com.excilys.cdb.bindingFront.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.excilys.cdb.bindingFront.AddComputerDTO;
import com.excilys.cdb.bindingFront.mapper.AddComputerMapper;


public class AddComputerValidator {
	public static final String ERROR_COMPUTER_NAME = "computerName";
	public static final String ERROR_COMPANY_ID = "companyId";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private Map<String, String> errors;

	public AddComputerValidator(Map<String, String> errors) {
		this.errors = errors;
	}

	public Map<String, String> validate(AddComputerDTO computerDTO) {
		validateName(computerDTO.getName());
		validateIntroducedBeforeDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		return errors;
	}

	private void validateName(String name) {
		if (name.isEmpty()) {
			errors.put(ERROR_COMPUTER_NAME, "Computer name cannot be empty.");
		}
	}

	private void validateIntroducedBeforeDiscontinued(String introduced, String discontinued) {
		if (!introduced.isEmpty() && !discontinued.isEmpty()) {
			LocalDate firstDate = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(DATE_FORMAT));
			LocalDate secondDate = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(DATE_FORMAT));
			if (firstDate.isAfter(secondDate)) {
				errors.put(AddComputerMapper.ERROR_DISCONTINUED, "The discontinued date have to be after the introduced date.");
			}
		}
	}


	public Map<String, String> getErrors() {
		return errors;
	}
}
