package com.excilys.cdb.bindingFront.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.AddComputerDTO;
import com.excilys.cdb.bindingFront.validator.AddComputerValidator;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class AddComputerMapper {

	public static final String ERROR_INTRODUCED = "introduced";
	public static final String ERROR_DISCONTINUED = "discontinued";

	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final int COMPANY_ID_NULL = 0;

	public Optional<Computer> toComputer(AddComputerDTO dto, Map<String, String> errors) {
		AddComputerValidator addComputerValidator = new AddComputerValidator(errors);

		if (addComputerValidator.validate(dto).isEmpty()) {
			Computer.Builder builder = new Computer.Builder(dto.getName());

			mapIntroducedToCompuer(builder, dto, errors);
			mapDiscontinuedToCompuer(builder, dto, errors);

			if (dto.getCompanyId() != COMPANY_ID_NULL) {
				builder.withCompany(new Company(dto.getCompanyId()));
			}
			if (errors.isEmpty()) {
				return Optional.of(builder.build());
			}
		}
		return Optional.empty();
	}

	private void mapIntroducedToCompuer(Computer.Builder builder, AddComputerDTO dto,
			Map<String, String> errors) {
		if (!dto.getIntroduced().isEmpty()) {
			try {
				builder.withIntroduced(LocalDate.parse(dto.getIntroduced(),
						DateTimeFormatter.ofPattern(DATE_FORMAT)));

			} catch (DateTimeParseException e) {
				errors.put(ERROR_INTRODUCED, "The introduced date '" + dto.getIntroduced()
						+ "' have not the good format.");
			}
		}
	}

	private void mapDiscontinuedToCompuer(Computer.Builder builder, AddComputerDTO dto,
			Map<String, String> errors) {
		if (!dto.getDiscontinued().isEmpty()) {

			try {
				builder.withDiscontinued(LocalDate.parse(dto.getDiscontinued(),
						DateTimeFormatter.ofPattern(DATE_FORMAT)));

			} catch (DateTimeParseException e) {
				errors.put(ERROR_DISCONTINUED, "The discontinued date '" + dto.getDiscontinued()
						+ "' have not the good format.");
			}
		}
	}






}
