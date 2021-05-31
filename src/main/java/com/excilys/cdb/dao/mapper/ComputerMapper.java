package com.excilys.cdb.dao.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.excilys.cdb.binding.ComputerDTO;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {
	private static ComputerMapper instance = new ComputerMapper();
	
	private static final int KEY_NULL = 0;

	private ComputerMapper() { }
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	public Computer toComputer(ComputerDTO dto) {
		Computer.Builder builder = new Computer.Builder(dto.getName());
		builder.withId(dto.getId());
		builder.withCompany(CompanyMapper.getInstance().toCompany(dto.getCompany()));
		

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
