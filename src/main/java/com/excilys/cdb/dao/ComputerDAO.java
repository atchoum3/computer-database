package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.excilys.cdb.bindingBack.CompanyDTO;
import com.excilys.cdb.bindingBack.ComputerDTO;
import com.excilys.cdb.bindingBack.mapper.ComputerDTOMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.mapper.ComputerMapper;
import com.excilys.cdb.service.Paginable;

@Scope
@Repository
public class ComputerDAO {
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private static final String QUERY_SELECT_ALL = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id";
	private static final String QUERY_SERCH_NAME = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id WHERE l.name LIKE CONCAT('%',?,'%') OR c.name LIKE CONCAT('%',?,'%')";
	private static final String QUERY_SELECT_BY_ID = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id WHERE l.id=?;";
	private static final String QUERY_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	private static final String QUERY_COUNT = "SELECT COUNT(1) FROM computer";
	private static final String QUERY_COUNT_SERCH_NAME = "SELECT COUNT(1)  FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id WHERE l.name LIKE CONCAT('%',?,'%') OR c.name LIKE CONCAT('%',?,'%')";
	private static final String QUERY_DELETE_BY_COMPUTER_ID = "DELETE FROM computer WHERE company_id = ?";

	private static final String ORDER_BY = " ORDER BY ? ";
	private static final String LIMIT = " LIMIT ?,? ";

	@Autowired
	private ComputerDTOMapper mapperDTO;
	@Autowired
	private ComputerMapper mapper;
	@Autowired
	private Database database;
	@Autowired
	private Paginable paginable;

	/**
	 * Get all Computer present on the range of the page.
	 * @param page  the page of values to take
	 * @return A computer list
	 * @throws CustomSQLException
	 */
	public List<Computer> getAll(Page page) throws CustomSQLException {
		List<Computer> computers = new ArrayList<>();
		String query = QUERY_SELECT_ALL + ORDER_BY + page.getOrder().toString() + LIMIT;
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(query)
		) {
			setPage(ps, 1, page);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					computers = mapper.toListComputer(mapperDTO.toListComputer(rs));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get all Computer.");
		}
		return computers;
	}

	public List<Computer> searchByName(String name, Page page) throws CustomSQLException {
		List<Computer> computers = new ArrayList<>();
		String query = QUERY_SERCH_NAME + ORDER_BY + page.getOrder().toString() + LIMIT;
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(query)
		) {
			ps.setString(1, name);
			ps.setString(2, name);
			setPage(ps, 3, page);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					computers = mapper.toListComputer(mapperDTO.toListComputer(rs));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get Computer by name search.");
		}
		return computers;
	}

	/**
	 * To get a computer by this id.
	 * @param id the id of the computer
	 * @return the optional is empty if there is no Computer with this id
	 * @throws CustomSQLException
	 */
	public Optional<Computer> getById(long id) throws CustomSQLException {
		Optional<Computer> computer = Optional.empty();
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					computer = Optional.of(mapper.toComputer(mapperDTO.toComputer(rs)));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get a computer.");
		}
		return computer;
	}

	/**
	 * To create a computer.
	 * @param c this computer will be create in the database.
	 * The id of the object will be determined and saved in this object
	 * @throws ComputerCompanyIdException if the company id does not exist
	 * @throws CustomSQLException
	 */
	public void create(Computer c) throws ComputerCompanyIdException, CustomSQLException {
		ComputerDTO dto = mapper.toComputerDTO(c);
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
		) {
			ps.setString(1, dto.getName());
			setTimestampOrNull(ps, 2, dto.getIntroduced());
			setTimestampOrNull(ps, 3, dto.getDiscontinued());
			setCompanyIdOrNull(ps, 4, dto.getCompany());

			logger.debug(ps.toString());
			ps.executeUpdate();

			//get id
			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.first()) {
					long id = rs.getLong(1);
					c.setId(id);
					logger.debug("id of computer in database:" + id);
				}
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new ComputerCompanyIdException("This company id does not exist");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to create a computer.");
		}
	}

	/**
	 * To update a computer.
	 * @param c computer to update
	 * @throws CustomSQLException
	 * @throws ComputerCompanyIdException
	 */
	public void update(Computer c) throws CustomSQLException, ComputerCompanyIdException {
		ComputerDTO dto = mapper.toComputerDTO(c);
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_UPDATE)
		) {
			ps.setString(1, dto.getName());
			setTimestampOrNull(ps, 2, dto.getIntroduced());
			setTimestampOrNull(ps, 3, dto.getDiscontinued());
			setCompanyIdOrNull(ps, 4, dto.getCompany());
			ps.setLong(5, dto.getId());

			logger.debug(ps.toString());
			ps.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new ComputerCompanyIdException("This company id does not exist");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to update a Computer.");
		}
	}

	/**
	 * To delete a Computer thanks to this id .
	 * @param id the id of the computer
	 * @return true if the Computer has been deleted.
	 * @throws CustomSQLException
	 */
	public boolean delete(long id) throws CustomSQLException {
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_DELETE)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to delete a Computer.");
		}
		return false;
	}

	int deleteByCompanyId(Connection con, long computerId) throws CustomSQLException {
		int nbDeleted = 0;
		try (
				PreparedStatement ps = con.prepareStatement(QUERY_DELETE_BY_COMPUTER_ID)
		) {
			ps.setLong(1, computerId);
			logger.debug(ps.toString());
			nbDeleted = ps.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to delete a Computer by this computer id.");
		}
		return nbDeleted;
	}

	public int count() throws CustomSQLException {
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_COUNT)
		) {
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to count all computers.");
		}
	}

	public int countSearchByName(String name) throws CustomSQLException {
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_COUNT_SERCH_NAME)
		) {
			ps.setString(1, name);
			ps.setString(2, name);

			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to count Computer by name search.");
		}
	}

	private void setTimestampOrNull(PreparedStatement ps, int pos, String date) throws SQLException {
		if ("".equals(date)) {
			ps.setNull(pos, java.sql.Types.TIMESTAMP);
		} else {
			ps.setString(pos, date);
		}
	}

	private void setCompanyIdOrNull(PreparedStatement ps, int pos, CompanyDTO companyDTO) throws SQLException {
		if (companyDTO == null) {
			ps.setNull(pos, java.sql.Types.NULL);
		} else {
			ps.setLong(4, companyDTO.getId());
		}
	}

	private void setPage(PreparedStatement ps, int pos, Page page) throws SQLException {
		ps.setInt(pos, page.getColumn().getIndex());
		ps.setInt(++pos, paginable.getIndexFirstElement(page));
		ps.setInt(++pos, page.getNbElementByPage());
	}
}

