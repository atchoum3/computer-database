package com.excilys.cdb.bindingFront.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.bindingFront.AddComputerDTO;
import com.excilys.cdb.bindingFront.ComputerCompanyNameDTO;
import com.excilys.cdb.bindingFront.ComputerCompanyNameDTO.Builder;
import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.exception.DateFormaNotValidException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Mapper between DTO and DAO.
 */
public class ComputerMapper {
	private static ComputerMapper instance = new ComputerMapper();
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final int COMPANY_ID_NULL = 0;
	
	private ComputerMapper() {
	}
	
	public static ComputerMapper getInstance() {
		return instance;
	}
	
	public Computer toComputer(AddComputerDTO computerDTO) throws DateFormaNotValidException {
		Computer.Builder builder = new Computer.Builder(computerDTO.getName());
		
		if (!computerDTO.getIntroduced().isEmpty()) {
			builder.withIntroduced(LocalDate.parse(
					computerDTO.getIntroduced(), DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (!computerDTO.getDiscontinued().isEmpty()) {
			builder.withDiscontinued(LocalDate.parse(
					computerDTO.getDiscontinued(), DateTimeFormatter.ofPattern(DATE_FORMAT)));
		}
		if (computerDTO.getCompanyId() != COMPANY_ID_NULL) {
			builder.withCompany(new Company(computerDTO.getCompanyId()));
		}
		return builder.build();
	}
	
	public List<ComputerCompanyNameDTO> toComputerCompanyName(List<Computer> computers) {
		List<ComputerCompanyNameDTO> result = new ArrayList<>();
		for (Computer computer : computers) {
			result.add(toComputerCompanyName(computer));
		}
		return result;
	}
	
	public ComputerCompanyNameDTO toComputerCompanyName(Computer computer) {
		String companyName = "", introduced = "", discontinued = "";
		
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toString();
		}
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toString();
		}
		if (computer.getCompany() != null) {
			companyName = computer.getCompany().getName();
		}
		return new ComputerCompanyNameDTO.Builder(computer.getName()).withIntroduced(introduced)
				.withDiscontinued(discontinued).withCompanyName(companyName).withId(computer.getId()).build();
	}
	
	public Computer toComputer(EditComputerDTO computerDTO) throws DateFormaNotValidException {
		Computer computer =  toComputer((AddComputerDTO) computerDTO);
		computer.setId(computerDTO.getId());
		return computer;
	}
	
	public AddComputerDTO toAddComputerDTO(Computer computer) {
		AddComputerDTO.Builder<?> builder = new AddComputerDTO.Builder<>(computer.getName());
		if (computer.getCompany() != null) {
			builder.withCompanyId(computer.getCompany().getId());
		}
		if (computer.getDiscontinued() != null) {
			builder.withDiscontinued(computer.getDiscontinued().toString());
		}
		if (computer.getIntroduced() != null) {
			builder.withIntroduced(computer.getIntroduced().toString());
		}
		return builder.build();
		
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
