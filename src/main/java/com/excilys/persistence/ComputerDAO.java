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
	private static final String QUERY_INSERT = "INSERT INTO computer VALUES (?,?,?,?,?)";
	private static final String QUERY_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
	private static final String QUERY_DELETE = "DELETE computer WHERE id=?";
	
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

	public void save(Computer c) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setLong(1, c.getId());
			ps.setString(2, c.getName());
			ps.setTimestamp(3, Timestamp.valueOf(c.getIntroduced()));
			ps.setTimestamp(4, Timestamp.valueOf(c.getDiscontinued()));
			ps.setLong(5, c.getIdCompany());
			
			c.setId(ps.executeUpdate());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void update(Computer c) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_UPDATE);
			ps.setString(1, c.getName());
			ps.setTimestamp(2, Timestamp.valueOf(c.getIntroduced()));
			ps.setTimestamp(3, Timestamp.valueOf(c.getDiscontinued()));
			ps.setLong(4, c.getIdCompany());
			ps.setLong(5, c.getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void delete(Computer c) {
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_DELETE);
			ps.setLong(1, c.getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
