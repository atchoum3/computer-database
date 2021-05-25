package com.excilys.cbd.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cbd.model.Company;
import com.excilys.cbd.model.Page;
import com.excilys.cbd.persistence.CompanyDAO;
public class CompanyService {
	private static CompanyService instance = null;
	private CompanyDAO companyDAO;
	
	private CompanyService() {
		companyDAO = CompanyDAO.getInstance();
	}
	
	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}
	
	public List<Company> getAll(Page page) {
		return companyDAO.getAll(page);
	}
	
	public Optional<Company> getById(long id) {
		return companyDAO.getById(id);
	}
}
