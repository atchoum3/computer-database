package com.excilys.cdb.bindingBack.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyDTOMapper {

	public Company toCompany(CompanyDTO dto) {
		return new Company(dto.getId(), dto.getName());
	}

	public List<Company> toListCompany(List<CompanyDTO> list) {
		return list.stream().map(c -> toCompany(c)).collect(Collectors.toList());
	}

	public CompanyDTO toCompanyDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}
}