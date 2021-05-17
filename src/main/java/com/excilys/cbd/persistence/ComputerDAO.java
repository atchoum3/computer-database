package com.excilys.cbd.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import com.excilys.cbd.mapper.ComputerMapper;
import com.excilys.cbd.model.Computer;
import com.excilys.cbd.model.Page;

public class ComputerDAO extends DAO {
	private static ComputerDAO instance = null;
	
	private static final String QUERY_SELECT_ALL = "SELECT * FROM computer LIMIT ?,?";
	private static final String QUERY_SELECT_BY_ID = "SELECT * FROM computer WHERE id = ?";
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
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL);
			ps.setInt(1, page.getIndexFirstElement());
			ps.setInt(2, page.getElementByPage());
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				ComputerMapper.toComputers(rs, computers);
				// indicate to page if it's last page
				if (computers.size() < page.getElementByPage()) {
					page.setIsLastPage();
				}
			} else {
				page.setIsLastPage();
			}
			
		}catch(Exception e){
			e.printStackTrace();
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
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				computer = Optional.of(ComputerMapper.toComputer(rs));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return computer;
	}

	/**
	 * To create a computer 
	 * @param c this computer will be create in the database. 
	 * The id of the object will be determined and saved in this object
	 */
	public void create(Computer c) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getName());
			ComputerMapper.setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.setTimestampOrNull(ps, 3, c.getDiscontinued());
			ps.setLong(4, c.getIdCompany());
			ps.executeUpdate();
			
			//get id
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.first()) {
				long id = rs.getLong(1);
				c.setId(id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * TO update a computer
	 * @param computer to update
	 */
	public void update(Computer c) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_UPDATE);
			ps.setString(1, c.getName());
			ComputerMapper.setTimestampOrNull(ps, 2, c.getIntroduced());
			ComputerMapper.setTimestampOrNull(ps, 3, c.getDiscontinued());
			ps.setLong(4, c.getIdCompany());
			ps.setLong(5, c.getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * To delete a Computer thanks to this id 
	 * @param id the id of the computer
	 */
	public void delete(long id) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_DELETE);
			ps.setLong(1, id);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
