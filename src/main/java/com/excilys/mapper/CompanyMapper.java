package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class CompanyMapper {
	
	private CompanyMapper() {} // static class, all methods are static
	
	private static Company toCompany(ResultSet rs) throws SQLException {
		long id = rs.getLong(1);
		String name = rs.getString(2);
		return new Company(id, name);
	}

	public static Set<Company> toCompanies(ResultSet rs) throws SQLException {
		Set<Company> companies = new HashSet<>();
		do {
			companies.add(CompanyMapper.toCompany(rs));
		} while(rs.next());
		return companies;
	}



	
			
}
