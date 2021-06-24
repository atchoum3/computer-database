package com.excilys.cdb.binding.web.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {

	public CompanyDTO toCompanyDTO(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}

	public List<CompanyDTO> toListCompanyDTO(List<Company> listCompany) {
		return listCompany.stream().map(c -> toCompanyDTO(c)).collect(Collectors.toList());
	}
}
