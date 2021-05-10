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

public class ComputerDAOImpl extends DAO implements IComputerDAO {
	private static ComputerDAOImpl instance = null;
	
	private ComputerDAOImpl() {
		super();
	}
	
	public static ComputerDAOImpl getInstance() {
		if (instance == null)
			instance = new ComputerDAOImpl();
		return instance;
	}

	@Override
	public Set<Computer> getAllComputers() {
		String query = "SELECT * FROM computer";
		Set<Computer> computers = new HashSet<>();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				computers = ComputerMapper.toComputers(rs);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return computers;
	}

	@Override
	public Optional<Computer> getById(long id) {
		String query = "SELECT * FROM computer WHERE id = ?";
		Optional<Computer> computer = Optional.empty();
		try {
			PreparedStatement ps = con.prepareStatement(query);
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

	@Override
	public void save(Computer c) {
		String query = "INSERT INTO computer VALUES (?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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

	@Override
	public void update(Computer c) {
		String query = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
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

	@Override
	public void delete(Computer c) {
		String query = "DELETE computer WHERE id=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setLong(1, c.getId());
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
