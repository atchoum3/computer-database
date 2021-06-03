package com.excilys.cdb.bindingFront.mapper;

import java.util.Map;
import java.util.Optional;

import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.bindingFront.validator.EditComputerValidator;
import com.excilys.cdb.model.Computer;

public class EditComputerMapper {
	private static EditComputerMapper instance = new EditComputerMapper();

	private EditComputerMapper() { }

	public static EditComputerMapper getInstance() {
		return instance;
	}

	public Optional<Computer> toComputer(EditComputerDTO dto,  Map<String, String> errors) {
		EditComputerValidator editComputerValidator = new EditComputerValidator(errors);
		Optional<Computer> opt = Optional.empty();

		if (editComputerValidator.validate(dto).isEmpty()) {
			opt = AddComputerMapper.getInstance().toComputer(dto, errors);
			opt.ifPresent(c -> c.setId(dto.getId()));
		}
		return opt;
	}

	public EditComputerDTO toEditComputerDTO(Computer computer) {
		EditComputerDTO.Builder builder = new EditComputerDTO.Builder(computer.getName());

		if (computer.getCompany() != null) {
			builder.withCompanyId(computer.getCompany().getId());
		}

		if (computer.getDiscontinued() != null) {
			builder.withDiscontinued(computer.getDiscontinued().toString());
		}

		if (computer.getIntroduced() != null) {
			builder.withIntroduced(computer.getIntroduced().toString());
		}

		builder.withId(computer.getId());
		return builder.build();
	}
}
