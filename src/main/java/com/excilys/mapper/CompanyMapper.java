package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.excilys.model.Company;

public class CompanyMapper {
	
	private CompanyMapper() {} // static class, all methods are static
	
	/**
	 * Get data from a ResultSet to build a Company object.
	 * @param rs a result set object from the SQL relation of company
	 * @return Company object
	 * @throws SQLException
	 */
	public static Company toCompany(ResultSet rs) throws SQLException {
		long id = rs.getLong(1);
		String name = rs.getString(2);
		return new Company(id, name);
	}

	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a ResultSet object from the SQL relation of company
	 * @param collection to fill in
	 * @throws SQLException
	 */
	public static void toCompanies(ResultSet rs, Collection<Company> collection) throws SQLException {
		do {
			collection.add(CompanyMapper.toCompany(rs));
		} while(rs.next());
	}		
}
