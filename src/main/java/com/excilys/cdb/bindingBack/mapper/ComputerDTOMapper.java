package com.excilys.cdb.bindingBack.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.ComputerDTO;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerDTOMapper {

	private CompanyDTOMapper companyMapper;

	public ComputerDTOMapper(CompanyDTOMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	public Computer toComputer(ComputerDTO dto) {
		Computer.Builder builder = new Computer.Builder(dto.getName());
		builder.withId(dto.getId());
		if (dto.getCompany() != null) {
			builder.withCompany(companyMapper.toCompany(dto.getCompany()));
		}

		if (dto.getIntroduced() != null) {
			String introduced = dto.getIntroduced().substring(0, 10);
			builder.withIntroduced(LocalDate.parse(introduced));
		}
		if (dto.getDiscontinued() != null) {
			String discontinued = dto.getDiscontinued().substring(0, 10);
			builder.withDiscontinued(LocalDate.parse(discontinued));
		}
		return builder.build();
	}

	public List<Computer> toListComputer(List<ComputerDTO> listDto) {
		return listDto.stream().map(c -> toComputer(c)).collect(Collectors.toList());
	}


	public ComputerDTO toComputerDTO(Computer computer) {
		ComputerDTO.Builder builder = new ComputerDTO.Builder(computer.getName());
		builder.withId(computer.getId());

		if (computer.getIntroduced() != null) {
			builder.withIntroduced(computer.getIntroduced().toString());
		}

		if (computer.getDiscontinued() != null) {
			builder.withDiscontinued(computer.getDiscontinued().toString());
		}

		if (computer.getCompany() != null) {
			builder.withCompany(companyMapper.toCompanyDTO(computer.getCompany()));
		}
		return builder.build();
	}

}