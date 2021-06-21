package com.excilys.cdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.CompanyDTO;

@Component
public class CompanyDTORowMapper implements RowMapper<CompanyDTO> {

	private static final long COMPANY_NULL = 0;

	@Override
	public CompanyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		long id = rs.getLong("company.id");
		if (id == COMPANY_NULL) {
			return null;
		} else {
			return new CompanyDTO(id, rs.getString("company.name"));
		}

	}

}
