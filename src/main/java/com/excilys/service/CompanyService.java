package com.excilys.service;

import java.util.List;
import java.util.Optional;

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
	
	public Optional<Company> getById(long id) {
		return companyDAO.getById(id);
	}
}
