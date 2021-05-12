package com.excilys.service;

import java.util.List;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
public class CompanyService {
	private static CompanyService instance = null;
	private CompanyDAO companyDAO;
	
	private CompanyService() {
		companyDAO = CompanyDAO.getInstance();
	}
	
	public static CompanyService getInstance() {
		if (instance == null)
			instance = new CompanyService();
		return instance;
	}
	
	public List<Company> getAll() {
		return companyDAO.getAll();
	}
}
