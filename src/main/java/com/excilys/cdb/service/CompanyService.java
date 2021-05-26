package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
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
	
	public List<Company> getAll() {
		return companyDAO.getAll();
	}
	
	public Optional<Company> getById(long id) {
		return companyDAO.getById(id);
	}
	
	public int count() {
		return companyDAO.count();
	}
}
