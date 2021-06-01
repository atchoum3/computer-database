package com.excilys.cdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.excilys.cdb.model.Company;

public class CompanyMapper {
	private static CompanyMapper instance = null;
	
	private CompanyMapper() { }
	
	public static CompanyMapper getInstance() {
		if (instance == null) {
			instance = new CompanyMapper();
		}
		return instance;
	}
	
	/**
	 * Get data from a ResultSet to build a Company object.
	 * @param rs a result set object from the SQL relation of company
	 * @return Company object
	 * @throws SQLException
	 */
	public Company toCompany(ResultSet rs) throws SQLException {
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
	public void toCompanies(ResultSet rs, Collection<Company> collection) throws SQLException {
		do {
			collection.add(this.toCompany(rs));
		} while (rs.next());
	}		
}