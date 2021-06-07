package com.excilys.cdb.bindingFront.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.ComputerCompanyNameDTO;
import com.excilys.cdb.model.Computer;

@Scope
@Component
public class ComputerCompanyNameMapper {

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
}
