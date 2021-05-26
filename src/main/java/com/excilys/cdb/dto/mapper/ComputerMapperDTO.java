package com.excilys.cdb.dto.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Mapper between DTO and DAO
 */
public class ComputerMapperDTO {
	private static ComputerMapperDTO instance = new ComputerMapperDTO();
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private ComputerMapperDTO() {
	}
	
	public static ComputerMapperDTO getInstance() {
		return instance;
	}
	
	public Computer toComputer(AddComputerDTO computerDTO) {
		Computer.Builder builder = new Computer.Builder(computerDTO.getName());
		
		if (!computerDTO.getIntroduced().isEmpty()) {
			builder.introduced(LocalDate.parse(
					computerDTO.getIntroduced(), DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (!computerDTO.getDiscontinued().isEmpty()) {
			builder.discontinued(LocalDate.parse(
					computerDTO.getDiscontinued(), DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		builder.company(new Company(computerDTO.getCompanyId()));
		return builder.build();
	}
}
