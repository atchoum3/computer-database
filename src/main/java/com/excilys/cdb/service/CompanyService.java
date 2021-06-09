package com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Service
public class CompanyService {

	private CompanyDAO companyDAO;

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public List<Company> getAll(Page page)  {
		return companyDAO.getAll(page);
	}

	public List<Company> getAll() {
		return companyDAO.getAll();
	}

	public int count() {
		return companyDAO.count();
	}

	public int delete(long id) {
		return companyDAO.delete(id);
	}
}