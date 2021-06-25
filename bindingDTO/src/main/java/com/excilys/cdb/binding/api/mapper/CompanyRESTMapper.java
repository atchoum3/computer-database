package com.excilys.cdb.binding.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.api.CompanyREST;
import com.excilys.cdb.model.Company;

@Component
public class CompanyRESTMapper {

	public CompanyREST map(Company company) {
		if (company == null) {
			return null;
		} else {
			CompanyREST companyREST = new CompanyREST();
			companyREST.setId(company.getId());
			companyREST.setName(company.getName());
			return companyREST;
		}
	}

	public Company map(CompanyREST dto) {
		if (dto == null) {
			return null;
		} else {
			return new Company(dto.getId(), dto.getName());
		}
	}

	public List<CompanyREST> map(List<Company> companies) {
		return companies.stream().map(this::map).collect(Collectors.toList());
	}
}
