package com.excilys.persistence;

import java.util.Set;

import com.excilys.model.Company;

public interface ICompanyDAO {
	public Set<Company> getAllCompanies();
}
