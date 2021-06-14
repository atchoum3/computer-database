package com.excilys.cdb.dao;

import com.excilys.cdb.bindingBack.ComputerDTO;
import com.excilys.cdb.bindingBack.mapper.ComputerDTOMapper;
import com.excilys.cdb.dao.mapper.ComputerDTORowMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.Paginable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ComputerDAO {
	private static final String TABLE_NAME = "computer";
	private static final String COLUMN_ID_NAME = "id";
	
	private static final String QUERY_SEARCH_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE LOWER(computer.name) LIKE LOWER(CONCAT('%',:search,'%')) OR LOWER(company.name) LIKE LOWER(CONCAT('%',:search,'%'))";
	private static final String QUERY_SELECT_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id=:id;";
	private static final String QUERY_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :companyId)";
	private static final String QUERY_UPDATE = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:companyId WHERE id=:id";
	private static final String QUERY_DELETE = "DELETE FROM computer WHERE id=:id";
	private static final String QUERY_COUNT_SERCH_NAME = "SELECT COUNT(1)  FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE LOWER(computer.name) LIKE LOWER(CONCAT('%',:search,'%')) OR LOWER(company.name) LIKE LOWER(CONCAT('%',:search,'%'))";

	private static final String ORDER_BY = " ORDER BY ";
	private static final String LIMIT = " LIMIT :startLimit,:offset ";

	private ComputerDTOMapper mapper;
	private DataSource dataSource;
	private Paginable paginable;
	private ComputerDTORowMapper computerDTORowMapper;

	public ComputerDAO(ComputerDTOMapper mapper, DataSource dataSource, Paginable paginable, ComputerDTORowMapper computerDTORowMapper) {
		this.mapper = mapper;
		this.dataSource = dataSource;
		this.paginable = paginable;
		this.computerDTORowMapper = computerDTORowMapper;
	}

	public List<Computer> searchByName(String name, Page page) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = QUERY_SEARCH_NAME + ORDER_BY + page.getColumn() + " " + page.getOrder().toString() + LIMIT;

		params.addValue("search", name);
		params.addValue("startLimit", paginable.getIndexFirstElement(page));
		params.addValue("offset", page.getNbElementByPage());
		List<ComputerDTO> computersDTO = jdbcTemplate.query(query, params, computerDTORowMapper);
		return mapper.toListComputer(computersDTO);
	}

	public Optional<Computer> getById(long id) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", id);
		try {
			ComputerDTO computerDTO = jdbcTemplate.queryForObject(QUERY_SELECT_BY_ID, params, computerDTORowMapper);
			return Optional.of(mapper.toComputer(computerDTO));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	/*public void create(Computer computer) throws ComputerCompanyIdException {
		ComputerDTO computerDTO = mapper.toComputerDTO(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		SqlParameterSource params = new BeanPropertySqlParameterSource(computerDTO);
		try {
			jdbcTemplate.update(QUERY_INSERT, params);
		} catch (DataIntegrityViolationException e) {
			throw new ComputerCompanyIdException("This company does not exist.");
		}
	}*/
	
	public void create(Computer computer) throws ComputerCompanyIdException {
		ComputerDTO computerDTO = mapper.toComputerDTO(computer);
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
		          .withTableName(TABLE_NAME).usingGeneratedKeyColumns(COLUMN_ID_NAME);
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(computerDTO);
		try {
		    Number newId = simpleJdbcInsert.executeAndReturnKey(params);
		    computer.setId(newId.longValue());
		} catch (DataIntegrityViolationException e) {
			throw new ComputerCompanyIdException("This company does not exist.");
		}
	}

	public void update(Computer computer) throws ComputerCompanyIdException {
		ComputerDTO computerDTO = mapper.toComputerDTO(computer);
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		SqlParameterSource params = new BeanPropertySqlParameterSource(computerDTO);
		try {
			jdbcTemplate.update(QUERY_UPDATE, params);
		} catch (DataIntegrityViolationException e) {
			throw new ComputerCompanyIdException("This company does not exist.");
		}
	}


	public int delete(long id) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", id);
		return jdbcTemplate.update(QUERY_DELETE, params);
	}

	public int countSearchByName(String name) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("search", name);
		return jdbcTemplate.queryForObject(QUERY_COUNT_SERCH_NAME, params, Integer.class);
	}
}

