package com.excilys.cdb.bindingFront.validator;

import java.util.Map;
import java.util.Optional;

import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.bindingFront.mapper.ComputerMapper;
import com.excilys.cdb.exception.DateFormaNotValidException;
import com.excilys.cdb.model.Computer;

public class EditComputerValidator extends AddComputerValidator {
	public static final String ERROR_ID = "otherError";

	public EditComputerValidator(Map<String, String> errors) {
		super(errors);
	}
	
	public Optional<Computer> map(EditComputerDTO computerDTO) {
		Optional<Computer> computer = Optional.empty();
		validate(computerDTO);

		if (errors.isEmpty()) {
			try {
				computer = Optional.of(ComputerMapper.getInstance().toComputer(computerDTO));
			} catch (DateFormaNotValidException e) {
				errors.put(ERROR_COMPUTER_NAME, "Computer name cannot be empty.");
			}
		}
		return computer;
		
	}
	
	protected void validate(EditComputerDTO computerDTO) {
		super.validate(computerDTO);
		validateId(computerDTO.getId());
	}
	
	private void validateId(long id) {
		if (id < 0) {
			errors.put(ERROR_ID, "Incorrect id.");
		}
	}

}
