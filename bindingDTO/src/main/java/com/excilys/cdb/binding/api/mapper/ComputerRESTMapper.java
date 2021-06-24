package com.excilys.cdb.binding.api.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.api.ComputerREST;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerRESTMapper {

	private CompanyRESTMapper companyMapper;

	public ComputerRESTMapper(CompanyRESTMapper companyMapper) {
		this.companyMapper = companyMapper;
	}

	public ComputerREST map(Computer computer) {
		return ComputerREST.builder()
				.withId(computer.getId())
				.withIntroduced(computer.getDiscontinued())
				.withDisconduced(computer.getDiscontinued())
				.withCompany(companyMapper.map(computer.getCompany()))
				.build();
	}
}
