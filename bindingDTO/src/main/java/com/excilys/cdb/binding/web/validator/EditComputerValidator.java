package com.excilys.cdb.binding.web.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.EditComputerDTO;

@Component
public class EditComputerValidator {
	public static final String ERROR_ID = "otherError";

	public Map<String, String> validate(EditComputerDTO computerDTO, Map<String, String> errors) {
		validateId(computerDTO.getId(), errors);
		return errors;
	}

	private void validateId(long id, Map<String, String> errors) {
		if (id < 0) {
			errors.put(ERROR_ID, "error.computer.incorrectId");
		}
	}

}
