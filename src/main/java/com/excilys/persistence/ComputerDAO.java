package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

public class ComputerDAO extends DAO {
	private static ComputerDAO instance = null;
	
	private static final String QUERY_SELECT_ALL = "SELECT * FROM computer";
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

	public Set<Computer> getAllComputers() {
		Set<Computer> computers = new HashSet<>();
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				computers = ComputerMapper.toComputers(rs);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return computers;
	}

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
