package com.excilys.cdb.model.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.ComputerDTO;
import com.excilys.cdb.model.Computer;

@Scope
@Component
public class ComputerMapper {

	@Autowired
	private CompanyMapper companyMapper;

	public Computer toComputer(ComputerDTO dto) {
		Computer.Builder builder = new Computer.Builder(dto.getName());
		builder.withId(dto.getId());
		if (dto.getCompany() != null) {
			builder.withCompany(companyMapper.toCompany(dto.getCompany()));
		}


		if (!dto.getIntroduced().isEmpty()) {
			String introduced = dto.getIntroduced().substring(0, 10);
			builder.withIntroduced(LocalDate.parse(introduced));
		}
		if (!dto.getDiscontinued().isEmpty()) {
			String discontinued = dto.getDiscontinued().substring(0, 10);
			builder.withDiscontinued(LocalDate.parse(discontinued));
		}

		return builder.build();
	}

	public List<Computer> toListComputer(List<ComputerDTO> listDto) {
		return listDto.stream().map(c -> toComputer(c)).collect(Collectors.toList());
	}

}