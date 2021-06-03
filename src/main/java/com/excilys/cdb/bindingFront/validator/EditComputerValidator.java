package com.excilys.cdb.bindingFront.validator;

import java.util.Map;

import com.excilys.cdb.bindingFront.EditComputerDTO;

public class EditComputerValidator {
	public static final String ERROR_ID = "otherError";

	private Map<String, String> errors;

	public EditComputerValidator(Map<String, String> errors) {
		this.errors = errors;
	}

	public Map<String, String> validate(EditComputerDTO computerDTO) {
		validateId(computerDTO.getId());
		return errors;
	}

	private void validateId(long id) {
		if (id < 0) {
			errors.put(ERROR_ID, "Incorrect id.");
		}
	}

}
