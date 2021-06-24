package com.excilys.cdb.binding.web.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.AddComputerDTO;
import com.excilys.cdb.binding.web.mapper.AddComputerMapper;

@Component
public class AddComputerValidator {
	public static final String ERROR_COMPUTER_NAME = "computerName";
	public static final String ERROR_COMPANY_ID = "companyId";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public Map<String, String> validate(AddComputerDTO computerDTO, Map<String, String> errors) {
		validateName(computerDTO.getName(), errors);
		validateIntroducedBeforeDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued(), errors);
		return errors;
	}

	private void validateName(String name, Map<String, String> errors) {
		if (name.isEmpty()) {
			errors.put(ERROR_COMPUTER_NAME, "error.computer.nameEmpty");
		}
	}

	private void validateIntroducedBeforeDiscontinued(String introduced, String discontinued, Map<String, String> errors) {
		if (!introduced.isEmpty() && !discontinued.isEmpty()) {
			LocalDate firstDate = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(DATE_FORMAT));
			LocalDate secondDate = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(DATE_FORMAT));
			if (firstDate.isAfter(secondDate)) {
				errors.put(AddComputerMapper.ERROR_DISCONTINUED, "error.computer.IntroducedBeforeDiscontinued");
			}
		}
	}
}
