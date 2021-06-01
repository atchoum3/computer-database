package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.excilys.cdb.dao.mapper.ComputerMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
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
					ComputerMapper.getInstance().toComputers(rs, computers);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get all Computer.");
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
					computer = Optional.of(ComputerMapper.getInstance().toComputer(rs));
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
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
		) {
			ps.setString(1, c.getName());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 3, c.getDiscontinued());
			ComputerMapper.getInstance().setCompanyOrNull(ps, 4, c.getCompany());
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
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_UPDATE)
		) {
			ps.setString(1, c.getName());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 3, c.getDiscontinued());
			ComputerMapper.getInstance().setCompanyOrNull(ps, 4, c.getCompany());
			ps.setLong(5, c.getId());
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
			throw new CustomSQLException("failure to delete a Computer.");
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
			throw new CustomSQLException("failure to count all computers.");
		}
		return 0;
	}
}

