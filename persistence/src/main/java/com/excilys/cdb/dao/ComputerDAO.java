package com.excilys.cdb.dao;

import com.excilys.cdb.bindingBack.ComputerDTO;
import com.excilys.cdb.bindingBack.mapper.ComputerDTOMapper;
import com.excilys.cdb.dao.mapper.ComputerDTORowMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

import java.util.List;
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
	private static final String QUERY_UPDATE = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:companyId WHERE id=:id";
	private static final String QUERY_DELETE = "DELETE FROM computer WHERE id=:id";
	private static final String QUERY_COUNT_SERCH_NAME = "SELECT COUNT(1)  FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE LOWER(computer.name) LIKE LOWER(CONCAT('%',:search,'%')) OR LOWER(company.name) LIKE LOWER(CONCAT('%',:search,'%'))";

	private static final String ORDER_BY = " ORDER BY ";
	private static final String LIMIT = " LIMIT :startLimit,:offset ";

	private ComputerDTOMapper mapper;
	private DataSource dataSource;
	private ComputerDTORowMapper computerDTORowMapper;
	private NamedParameterJdbcTemplate npJdbcTemplate;

	public ComputerDAO(ComputerDTOMapper mapper, DataSource dataSource, 
			ComputerDTORowMapper computerDTORowMapper, NamedParameterJdbcTemplate npJdbcTemplate) {
		this.mapper = mapper;
		this.dataSource = dataSource;
		this.computerDTORowMapper = computerDTORowMapper;
		this.npJdbcTemplate = npJdbcTemplate;
	}

	public List<Computer> searchByName(String name, Page page) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		String query = QUERY_SEARCH_NAME + ORDER_BY + page.getColumn() + " " + page.getOrder().toString() + LIMIT;

		params.addValue("search", name);
		params.addValue("startLimit", (page.getCurrentPage() -1) * page.getNbElementByPage());
		params.addValue("offset", page.getNbElementByPage());
		List<ComputerDTO> computersDTO = npJdbcTemplate.query(query, params, computerDTORowMapper);
		return mapper.toListComputer(computersDTO);
	}

	public Optional<Computer> getById(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", id);
		try {
			ComputerDTO computerDTO = npJdbcTemplate.queryForObject(QUERY_SELECT_BY_ID, params, computerDTORowMapper);
			return Optional.of(mapper.toComputer(computerDTO));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

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

		SqlParameterSource params = new BeanPropertySqlParameterSource(computerDTO);
		try {
			npJdbcTemplate.update(QUERY_UPDATE, params);
		} catch (DataIntegrityViolationException e) {
			throw new ComputerCompanyIdException("This company does not exist.");
		}
	}


	public int delete(long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("id", id);
		return npJdbcTemplate.update(QUERY_DELETE, params);
	}

	public int countSearchByName(String name) {
		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("search", name);
		return npJdbcTemplate.queryForObject(QUERY_COUNT_SERCH_NAME, params, Integer.class);
	}
}

