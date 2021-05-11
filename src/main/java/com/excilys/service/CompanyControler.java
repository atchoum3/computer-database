package com.excilys.service;

import java.util.Set;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;

public class CompanyControler {
	
	
	public Set<Company> getAllCompany() {
		return CompanyDAO.getInstance().getAllCompanies();
	}
	
	
}
