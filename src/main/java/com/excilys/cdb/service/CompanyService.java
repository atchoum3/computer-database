package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
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

	public int delete(long id) throws CustomSQLException {
		return companyDAO.delete(id);
	}
}