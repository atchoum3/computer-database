package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.excilys.mapper.CompanyMapper;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class CompanyDAO extends DAO {
	private static CompanyDAO instance = null;
	
	private static final String QUERY_SELECT_ALL = "SELECT * FROM company";
	private static final String QUERY_SELECT_BY_ID = "SELECT * FROM company WHERE id = ?";
	
	private CompanyDAO() {
		super();
	}
	
	public static CompanyDAO getInstance() {
		if (instance == null)
			instance = new CompanyDAO();
		return instance;
	}

	public Set<Company> getAllCompanies() {
		Set<Company> companies = new HashSet<>();
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				companies = CompanyMapper.toCompanies(rs);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return companies;
	}
	
	public Optional<Company> getById(long id) {
		Optional<Company> company = Optional.empty();
		try {
			PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				company = Optional.of(CompanyMapper.toCompany(rs));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return company;
	}
}
