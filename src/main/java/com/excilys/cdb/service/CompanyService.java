package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Scope
@Service
public class CompanyService {

	@Autowired
	private CompanyDAO companyDAO;


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