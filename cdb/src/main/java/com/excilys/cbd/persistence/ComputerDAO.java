package com.excilys.cbd.persistence;

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

import com.excilys.cbd.exception.ComputerCompanyIdException;
import com.excilys.cbd.mapper.ComputerMapper;
import com.excilys.cbd.model.Computer;
import com.excilys.cbd.model.Page;

public class ComputerDAO {
	private static ComputerDAO instance = null;
	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	
	private static final String QUERY_SELECT_ALL = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l JOIN company AS c ON l.company_id = c.id LIMIT ?,?";
	private static final String QUERY_SELECT_BY_ID = "SELECT l.id, l.name, l.introduced, l.discontinued, c.id, c.name FROM computer AS l JOIN company AS c ON l.company_id = c.id WHERE l.id=?;";
	private static final String QUERY_INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?)";
	private static final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String QUERY_DELETE = "DELETE FROM computer WHERE id=?";
	
	private ComputerDAO() {
		super();
	}
	
	public static ComputerDAO getInstance() {
		if (instance == null)
			instance = new ComputerDAO();
		return instance;
	}

	/**
	 * Get all Computer present on the range of the page
	 * @param page  the page of values to take
	 * @return
	 */
	public List<Computer> getAll(Page page) {
		List<Computer> computers = new ArrayList<>();
		try(
				Connection con = Database.getInstance().connection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL)
		) {
			ps.setInt(1, page.getIndexFirstElement());
			ps.setInt(2, page.getElementByPage());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					ComputerMapper.getInstance().toComputers(rs, computers);
					// indicate to page if it's last page
					if (computers.size() < page.getElementByPage()) {
						page.setIsLastPage();
					}
				} else {
					page.setIsLastPage();
				}
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}
		return computers;
	}

	/**
	 * To get a computer by this id.
	 * @param id the id of the computer
	 * @return the optional is empty if there is no Computer with this id 
	 */
	public Optional<Computer> getById(long id) {
		Optional<Computer> computer = Optional.empty();
		try(
				Connection con = Database.getInstance().connection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID)
		) {
			ps.setLong(1, id);
			try(ResultSet rs = ps.executeQuery()) {
			
				if (rs.next()) {
					computer = Optional.of(ComputerMapper.getInstance().toComputer(rs));
				}
			}
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}
		return computer;
	}

	/**
	 * To create a computer 
	 * @param c this computer will be create in the database. 
	 * The id of the object will be determined and saved in this object
	 * @throws ComputerCompanyIdException if the company id does not exist
	 */
	public void create(Computer c) throws ComputerCompanyIdException {
		try(
				Connection con = Database.getInstance().connection();
				PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)
		) {
			ps.setString(1, c.getName());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 3, c.getDiscontinued());
			ComputerMapper.getInstance().setCompanyOrNull(ps, 4, c.getCompany());
			ps.executeUpdate();
			
			//get id
			try(ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.first()) {
					long id = rs.getLong(1);
					c.setId(id);
				}
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			throw new ComputerCompanyIdException("This company id does not exist");
		}catch(SQLException e){
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * TO update a computer
	 * @param computer to update
	 */
	public void update(Computer c) {
		try(
				Connection con = Database.getInstance().connection();
				PreparedStatement ps = con.prepareStatement(QUERY_UPDATE)
		) {
			ps.setString(1, c.getName());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.getInstance().setTimestampOrNull(ps, 3, c.getDiscontinued());
			ComputerMapper.getInstance().setCompanyOrNull(ps, 4, c.getCompany());
			ps.setLong(5, c.getId());
			ps.executeUpdate();
		} catch(SQLException e){
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * To delete a Computer thanks to this id 
	 * @param id the id of the computer
	 */
	public void delete(long id) {
		try(
				Connection con = Database.getInstance().connection();
				PreparedStatement ps = con.prepareStatement(QUERY_DELETE)
		) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch(SQLException e){
			logger.error(e.getMessage(), e);
		}
	}
}
