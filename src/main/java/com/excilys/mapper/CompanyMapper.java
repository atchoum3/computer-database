package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.excilys.model.Company;

public class CompanyMapper {
	
	private CompanyMapper() {} // static class, all methods are static
	
	public static Company toCompany(ResultSet rs) throws SQLException {
		long id = rs.getLong(1);
		String name = rs.getString(2);
		return new Company(id, name);
	}

	public static void toCompanies(ResultSet rs, Collection<Company> collection) throws SQLException {
		do {
			collection.add(CompanyMapper.toCompany(rs));
		} while(rs.next());
	}



	
			
}
