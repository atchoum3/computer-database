package com.excilys.cdb.binding.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import com.excilys.cdb.binding.CompanyDTO;

public class CompanyDTOMapper {
	private static CompanyDTOMapper instance = new CompanyDTOMapper();

	public static CompanyDTOMapper getInstance() {
		return instance;
	}

	/**
	 * Get data from a ResultSet to build a Company object.
	 * @param rs a result set object from the SQL relation of company
	 * @return Company object
	 * @throws SQLException
	 */
	public CompanyDTO toCompany(ResultSet rs) throws SQLException {
		long id = rs.getLong(1);
		String name = rs.getString(2);
		return new CompanyDTO(id, name);
	}

	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a ResultSet object from the SQL relation of company
	 * @param collection to fill in
	 * @throws SQLException
	 */
	public void toCompanies(ResultSet rs, Collection<CompanyDTO> collection) throws SQLException {
		do {
			collection.add(this.toCompany(rs));
		} while (rs.next());
	}		

}
