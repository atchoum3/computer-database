package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.excilys.cdb.binding.CompanyDTO;
import com.excilys.cdb.binding.ComputerDTO;
import com.excilys.cdb.binding.mapper.CompanyDTOMapper;
import com.excilys.cdb.binding.mapper.ComputerDTOMapper;
import com.excilys.cdb.dao.mapper.CompanyMapper;
import com.excilys.cdb.dao.mapper.ComputerMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDAO {
	private static ComputerDAO instance = null;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private static final String QUERY_SELECT_ALL = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id LIMIT ?,?";
	private static final String QUERY_SELECT_BY_ID = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l LEFT JOIN company AS c ON l.company_id = c.id WHERE l.id=?;";
	private static final String QUERY_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	private static final String QUERY_COUNT = "SELECT COUNT(1) FROM computer";
	
	private static ComputerDTOMapper dtoMapper = ComputerDTOMapper.getInstance();
	private static ComputerMapper mapper = ComputerMapper.getInstance();
	
	private ComputerDAO() {
		super();
	}
	
	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDAO();
		}
		return instance;
	}

	/**
	 * Get all Computer present on the range of the page.
	 * @param page  the page of values to take
	 * @return A computer list
	 * @throws CustomSQLException 
	 */
	public List<Computer> getAll(Page page) throws CustomSQLException {
		List<Computer> computers = new ArrayList<>();
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL)
		) {
			ps.setInt(1, page.getIndexFirstElement());
			ps.setInt(2, page.getElementByPage());
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					List<ComputerDTO> computersDTO = new ArrayList<>();
					dtoMapper.toComputers(rs, computersDTO);
					return mapper.toListComputer(computersDTO);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException(e.getMessage());
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
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
			
				if (rs.next()) {
					computer = Optional.of(mapper.toComputer(dtoMapper.toComputer(rs)));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException(e.getMessage());
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
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
		) {
			ps.setString(1, c.getName());
			setTimestampOrNull(ps, 2, c.getIntroduced());
			setTimestampOrNull(ps, 3, c.getDiscontinued());
			setCompanyOrNull(ps, 4, c.getCompany());
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
			throw new CustomSQLException(e.getMessage());
		}
	}

	/**
	 * To update a computer.
	 * @param c computer to update
	 * @throws CustomSQLException 
	 */
	public void update(Computer c) throws CustomSQLException {
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_UPDATE)
		) {
			ps.setString(1, c.getName());
			setTimestampOrNull(ps, 2, c.getIntroduced());
			setTimestampOrNull(ps, 3, c.getDiscontinued());
			setCompanyOrNull(ps, 4, c.getCompany());
			ps.setLong(5, c.getId());
			logger.debug(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException(e.getMessage());
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
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_DELETE)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			if (ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException(e.getMessage());
		}
		return false;
	}
	
	public int count() throws CustomSQLException {
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_COUNT)
		) {
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
				//TODO throw error
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException(e.getMessage());
		}
		return 0;
	}
	
	/**
	 * To insert a Timestamp on a PreparedStatement. This method insert the timestamp or the null value.
	 * @param ps a PreparedStatement object
	 * @param pos the position where will be inserted the Timestamp value
	 * @param localDate this value will be converted in Timestamp value. 
	 * If this value is null, null value is insert on the prepared statement
	 * @throws SQLException
	 */
	private void setTimestampOrNull(PreparedStatement ps, int pos, LocalDate localDate) 
			throws SQLException {
		if (localDate == null) {
			ps.setNull(pos, java.sql.Types.NULL);
		} else {
			ps.setTimestamp(pos, Timestamp.valueOf(localDate.atStartOfDay()));
		}
	}

	private void setCompanyOrNull(PreparedStatement ps, int pos, Company company) 
			throws SQLException {
		if (company == null) {
			ps.setNull(pos, java.sql.Types.NULL);
		} else {
			ps.setLong(pos, company.getId());
		}
	}
}
