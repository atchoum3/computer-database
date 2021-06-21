package com.excilys.cdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.ComputerDTO;

@Component
public class ComputerDTORowMapper implements RowMapper<ComputerDTO> {

	private CompanyDTORowMapper companyMapper;

	public ComputerDTORowMapper(CompanyDTORowMapper companyDTORowMapper) {
		this.companyMapper = companyDTORowMapper;
	}

	@Override
	public ComputerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ComputerDTO.Builder builder = new ComputerDTO.Builder(rs.getString("computer.name"));
		builder.withId(rs.getLong("computer.id"));
		builder.withDiscontinued(rs.getString("computer.discontinued"));
		builder.withIntroduced(rs.getString("computer.introduced"));
		builder.withCompany(companyMapper.mapRow(rs, rowNum));
		return builder.build();
	}

}
