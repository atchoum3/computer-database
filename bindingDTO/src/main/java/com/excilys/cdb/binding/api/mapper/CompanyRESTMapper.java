package com.excilys.cdb.binding.api.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.api.CompanyREST;
import com.excilys.cdb.model.Company;

@Component
public class CompanyRESTMapper {

	public CompanyREST map(Company company) {
		CompanyREST companyREST = new CompanyREST();
		companyREST.setId(company.getId());
		companyREST.setName(company.getName());
		return companyREST;
	}
}
