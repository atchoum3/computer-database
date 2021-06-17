package com.excilys.cdb.bindingBack.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.ComputerEntity;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerEntityMapper {

	private CompanyEntityMapper companyMapper;

	public ComputerEntityMapper(CompanyEntityMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	public Computer toComputer(ComputerEntity dto) {
		Computer.Builder builder = new Computer.Builder(dto.getName());
		builder.withId(dto.getId());
		if (dto.getCompany() != null) {
			builder.withCompany(companyMapper.toCompany(dto.getCompany()));
		}

		if (dto.getIntroduced() != null) {
			builder.withIntroduced(dto.getIntroduced());
		}
		if (dto.getDiscontinued() != null) {
			builder.withDiscontinued(dto.getDiscontinued());
		}
		return builder.build();
	}

	public List<Computer> toListComputer(List<ComputerEntity> listDto) {
		return listDto.stream().map(c -> toComputer(c)).collect(Collectors.toList());
	}


	public ComputerEntity toComputerDTO(Computer computer) {
		ComputerEntity entity = new ComputerEntity();
		entity.setName(computer.getName());
		entity.setId(computer.getId());
		entity.setIntroduced(computer.getIntroduced());
		entity.setDiscontinued(computer.getDiscontinued());
		entity.setCompany(companyMapper.toCompanyDTO(computer.getCompany()));
		return entity;
	}

}