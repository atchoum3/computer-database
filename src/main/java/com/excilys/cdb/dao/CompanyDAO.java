package com.excilys.cdb.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.bindingBack.CompanyDTO;
import com.excilys.cdb.bindingBack.mapper.CompanyDTOMapper;
import com.excilys.cdb.dao.mapper.CompanyDTORowMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Paginable;

@Repository
public class CompanyDAO {
	private static final String QUERY_SELECT_ALL = "SELECT company.id, company.name FROM company ORDER BY name";
	private static final String QUERY_COUNT = "SELECT COUNT(1) FROM company";
	private static final String QUERY_SELECT_ALL_LIMIT = "SELECT id, name FROM company ORDER BY id LIMIT :startLimit,:offset";
	private static final String QUERY_DELETE_BY_ID = "DELETE FROM company WHERE id = :id";
	private static final String QUERY_DELETE_COMPUTER_BY_COMPANY_ID = "DELETE FROM computer WHERE company_id = :id";

	private CompanyDTOMapper mapper;
	private DataSource dataSource;
	private Paginable paginable;
	private CompanyDTORowMapper companyDTORowMapper;

	public CompanyDAO(CompanyDTOMapper mapper, DataSource dataSource,
			Paginable paginable, CompanyDTORowMapper companyDTORowMapper) {
		this.mapper = mapper;
		this.dataSource = dataSource;
		this.paginable = paginable;
		this.companyDTORowMapper = companyDTORowMapper;
	}

	public List<Company> getAll(Page page) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("startLimit", paginable.getIndexFirstElement(page));
		params.addValue("offset", page.getNbElementByPage());
		List<CompanyDTO> companiesDTO = jdbcTemplate.query(QUERY_SELECT_ALL_LIMIT, params, companyDTORowMapper);
		return mapper.toListCompany(companiesDTO);
	}

	public List<Company> getAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		List<CompanyDTO> companiesDTO = jdbcTemplate.query(QUERY_SELECT_ALL, companyDTORowMapper);
		return mapper.toListCompany(companiesDTO);
	}

	public int count() {
		 return new JdbcTemplate(dataSource).queryForObject(QUERY_COUNT, Integer.class);
	}
	
	@Transactional
	public int delete(long id) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", id);
		int nbComputerDeleted = jdbcTemplate.update(QUERY_DELETE_COMPUTER_BY_COMPANY_ID, params);
		jdbcTemplate.update(QUERY_DELETE_BY_ID, params);
		return nbComputerDeleted;
	}
}