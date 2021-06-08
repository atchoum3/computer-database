package com.excilys.cdb.bindingFront.mapper;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.bindingFront.validator.EditComputerValidator;
import com.excilys.cdb.model.Computer;

@Component
public class EditComputerMapper {

	private AddComputerMapper addComputerMapper;

	public EditComputerMapper(AddComputerMapper addComputerMapper) {
		this.addComputerMapper = addComputerMapper;
	}



	public Optional<Computer> toComputer(EditComputerDTO dto,  Map<String, String> errors) {
		EditComputerValidator editComputerValidator = new EditComputerValidator(errors);
		Optional<Computer> opt = Optional.empty();

		if (editComputerValidator.validate(dto).isEmpty()) {
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
