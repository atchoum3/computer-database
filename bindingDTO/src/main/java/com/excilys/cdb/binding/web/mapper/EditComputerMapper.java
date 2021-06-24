package com.excilys.cdb.binding.web.mapper;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.EditComputerDTO;
import com.excilys.cdb.binding.web.validator.EditComputerValidator;
import com.excilys.cdb.model.Computer;

@Component
public class EditComputerMapper {

	private AddComputerMapper addComputerMapper;
	private EditComputerValidator editComputerValidator;

	public EditComputerMapper(AddComputerMapper addComputerMapper,
			EditComputerValidator editComputerValidator) {
		this.addComputerMapper = addComputerMapper;
		this.editComputerValidator = editComputerValidator;
	}



	public Optional<Computer> toComputer(EditComputerDTO dto,  Map<String, String> errors) {
		Optional<Computer> opt = Optional.empty();

		if (editComputerValidator.validate(dto, errors).isEmpty()) {
			opt = addComputerMapper.toComputer(dto, errors);
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
