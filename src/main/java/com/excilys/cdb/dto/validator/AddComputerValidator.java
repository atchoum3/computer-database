package com.excilys.cdb.dto.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.dto.mapper.ComputerMapper;
import com.excilys.cdb.exception.DateFormaNotValidException;
import com.excilys.cdb.model.Computer;


public class AddComputerValidator {
	public static final String ERROR_COMPUTER_NAME = "computerName";
	public static final String ERROR_INTRODUCED = "introduced";
	public static final String ERROR_DISCONTINUED = "discontinued";
	public static final String ERROR_COMPANY_ID = "companyId";
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private Map<String, String> errors;
	
	public AddComputerValidator(Map<String, String> errors) {
		this.errors = errors;
	}
	
	public Optional<Computer> validate(AddComputerDTO computerDTO) {
		Optional<Computer> computer = Optional.empty();
		
		validateName(computerDTO.getName());
		validateIntroduced(computerDTO.getIntroduced());
		validateDiscontinued(computerDTO.getDiscontinued());
		validateIntroducedBeforeDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		
		if (errors.isEmpty()) {
			try {
				computer = Optional.of(ComputerMapper.getInstance().toComputer(computerDTO));
			} catch (DateFormaNotValidException e) {
				errors.put(AddComputerValidator.ERROR_COMPUTER_NAME, "Computer name cannot be empty.");
			}
		}
		return computer;
	}
	
	private void validateName(String name) {
		if (name.isEmpty()) {
			errors.put(AddComputerValidator.ERROR_COMPUTER_NAME, "Computer name cannot be empty.");
		}
	}
	
	private void validateIntroduced(String introduced) {
		if (!introduced.isEmpty()) {
			try {
				LocalDate.parse(introduced, DateTimeFormatter.ofPattern(DATE_FORMAT));
			} catch (DateTimeParseException e) {
				errors.put(AddComputerValidator.ERROR_INTRODUCED, "The introduced date '" + introduced + "' have not the good format.");
			}
		}
	}
	
	private void validateDiscontinued(String discontinued) {
		if (!discontinued.isEmpty()) {
			try {
				LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(DATE_FORMAT));
			} catch (DateTimeParseException e) {
				errors.put(AddComputerValidator.ERROR_DISCONTINUED, "The discontinued date '" + discontinued + "' have not the good format.");
			}
		}
	}
	
	private void validateIntroducedBeforeDiscontinued(String introduced, String discontinued) {
		if (!introduced.isEmpty() && !discontinued.isEmpty()) {
			LocalDate firstDate = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(DATE_FORMAT));
			LocalDate secondDate = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(DATE_FORMAT));
			if (firstDate.isAfter(secondDate)) {
				errors.put(ERROR_DISCONTINUED, "The discontinued date have to be after the introduced date.");
			}
		}
	}
	
	
	public Map<String, String> getErrors() {
		return errors;
	}
}
