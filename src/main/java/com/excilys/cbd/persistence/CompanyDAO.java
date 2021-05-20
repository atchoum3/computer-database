package com.excilys.cbd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cbd.mapper.CompanyMapper;
import com.excilys.cbd.model.Company;
import com.excilys.cbd.model.Page;

public class CompanyDAO {
	private static CompanyDAO instance = null;
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static final String QUERY_SELECT_ALL = "SELECT id, name FROM company LIMIT ?,?";
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
	 */
	public List<Company> getAll(Page page) {
		List<Company> companies = new ArrayList<>();
		try (
				Connection con = Database.getInstance().getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL)
		) {	
			ps.setInt(1, page.getIndexFirstElement());
			ps.setInt(2, page.getElementByPage());
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				
				if (rs.next()) {
					CompanyMapper.getInstance().toCompanies(rs, companies);
					// indicate to page if it's last page
					if (companies.size() < page.getElementByPage()) {
						page.setIsLastPage();
					}
				} else {
					// indicate to page if it's last page
					page.setIsLastPage();
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return companies;
	}
	
	/**
	 * get a company by this id.
	 * @param id the id of the company
	 * @return the optional is empty if there is no company with this id
	 */
	public Optional<Company> getById(long id) {
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
		}
		return company;
	}
}
