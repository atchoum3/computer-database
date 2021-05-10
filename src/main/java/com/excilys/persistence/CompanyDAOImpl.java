package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.excilys.mapper.CompanyMapper;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class CompanyDAOImpl extends DAO implements ICompanyDAO {
	private static CompanyDAOImpl instance = null;
	
	
	private CompanyDAOImpl() {
		// noting to initialize
	}
	
	public static CompanyDAOImpl getIsntance() {
		if (instance == null)
			instance = new CompanyDAOImpl();
		return instance;
	}

	@Override
	public Set<Company> getAllCompanies() {
		String query = "SELECT * FROM company";
		Set<Company> companies = new HashSet<>();
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				companies = CompanyMapper.toCompanies(rs);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return companies;
	}
}
