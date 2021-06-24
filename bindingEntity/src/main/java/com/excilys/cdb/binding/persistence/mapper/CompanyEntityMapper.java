package com.excilys.cdb.binding.persistence.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.persistence.CompanyEntity;
import com.excilys.cdb.model.Company;

@Component
public class CompanyEntityMapper {

	public Company toCompany(CompanyEntity dto) {
		return new Company(dto.getId(), dto.getName());
	}

	public List<Company> toListCompany(List<CompanyEntity> list) {
		return list.stream().map(c -> toCompany(c)).collect(Collectors.toList());
	}

	public CompanyEntity toCompanyDTO(Company company) {
		return new CompanyEntity(company.getId(), company.getName());
	}
}