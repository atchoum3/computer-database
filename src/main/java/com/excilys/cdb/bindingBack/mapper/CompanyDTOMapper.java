package com.excilys.cdb.bindingBack.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.CompanyDTO;

@Component
public class CompanyDTOMapper {

	/**
	 * Get data from a ResultSet to build a CompanyDTO object.
	 * @param rs a result set object from the SQL relation of company
	 * @return CompanyDTO object
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
	 * @return CompanyDTO list
	 * @throws SQLException
	 */
	public List<CompanyDTO> toListCompany(ResultSet rs) throws SQLException {
		List<CompanyDTO> list = new ArrayList<>();
		do {
			list.add(this.toCompany(rs));
		} while (rs.next());
		return list;
	}

}
