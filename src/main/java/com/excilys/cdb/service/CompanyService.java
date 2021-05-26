package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.CustomSQLException;
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
	
	public List<Company> getAll(Page page) throws CustomSQLException {
		return companyDAO.getAll(page);
	}
	
	public List<Company> getAll() throws CustomSQLException {
		return companyDAO.getAll();
	}
	
	public Optional<Company> getById(long id) throws CustomSQLException {
		return companyDAO.getById(id);
	}
	
	public int count() throws CustomSQLException {
		return companyDAO.count();
	}
}
