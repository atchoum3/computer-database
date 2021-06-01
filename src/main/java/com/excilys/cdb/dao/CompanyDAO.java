package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.mapper.CompanyMapper;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyDAO {
	private static CompanyDAO instance = null;
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static final String QUERY_COUNT = "SELECT COUNT(1) FROM company";
	private static final String QUERY_SELECT_ALL = "SELECT id, name FROM company ORDER BY name";
	private static final String QUERY_SELECT_ALL_LIMIT = "SELECT id, name FROM company ORDER BY id LIMIT ?,?";
	private static final String QUERY_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	
	private CompanyDAO() {
		super();
	}
	
	public static CompanyDAO getInstance() {
		if (instance == null) {
			instance = new CompanyDAO();
		}
		return instance;
	}

	/**
	 * Get all Company present on the range of the page.
	 * @param page the page of values to take
	 * @return A company List
	 * @throws CustomSQLException 
	 */
	public List<Company> getAll(Page page) throws CustomSQLException {
		List<Company> companies = new ArrayList<>();
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL_LIMIT)
		) {	
			ps.setInt(1, page.getIndexFirstElement());
			ps.setInt(2, page.getElementByPage());
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					CompanyMapper.getInstance().toCompanies(rs, companies);
				} 
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get all Companies by page.");
		}
		return companies;
	}
	
	/**
	 * Get all Company.
	 * @return A company List
	 * @throws CustomSQLException 
	 */
	public List<Company> getAll() throws CustomSQLException {
		List<Company> companies = new ArrayList<>();
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL)
		) {	
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					CompanyMapper.getInstance().toCompanies(rs, companies);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get all Companies.");
		}
		return companies;
	}
	
	/**
	 * get a company by this id.
	 * @param id the id of the company
	 * @return the optional is empty if there is no company with this id
	 * @throws CustomSQLException 
	 */
	public Optional<Company> getById(long id) throws CustomSQLException {
		Optional<Company> company = Optional.empty();
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					company = Optional.of(CompanyMapper.getInstance().toCompany(rs));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get a Company.");
		}
		return company;
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
			throw new CustomSQLException("failure to count all Companies.");
		}
		return 0;
	}
}