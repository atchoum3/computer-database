package com.excilys.cdb.binding.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

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
				.withName(computer.getName())
				.withId(computer.getId())
				.withIntroduced(computer.getIntroduced())
				.withDiscontinued(computer.getDiscontinued())
				.withCompany(companyMapper.map(computer.getCompany()))
				.build();
	}

	public Computer map(ComputerREST dto) {
		return Computer.builder(dto.getName())
				.withId(dto.getId())
				.withIntroduced(dto.getIntroduced())
				.withDiscontinued(dto.getDiscontinued())
				.withCompany(companyMapper.map(dto.getCompany()))
				.build();
	}

	public List<ComputerREST> map(List<Computer> computers) {
		return computers.stream().map(this::map).collect(Collectors.toList());
	}
}
