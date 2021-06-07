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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.bindingBack.mapper.CompanyDTOMapper;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.mapper.CompanyMapper;
import com.excilys.cdb.service.Paginable;

@Scope
@Repository
public class CompanyDAO {
	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	private static final String QUERY_SELECT_ALL = "SELECT id, name FROM company ORDER BY name";
	private static final String QUERY_SELECT_ALL_LIMIT = "SELECT id, name FROM company ORDER BY id LIMIT ?,?";
	private static final String QUERY_SELECT_BY_ID = "SELECT id, name FROM company WHERE id = ?";
	private static final String QUERY_DELETE = "DELETE FROM company WHERE id = ?";

	@Autowired
	private CompanyMapper mapper;
	@Autowired
	private CompanyDTOMapper mapperDTO;
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private Database database;
	@Autowired
	private Paginable paginable;

	/**
	 * Get all Company present on the range of the page.
	 * @param page the page of values to take
	 * @return A company List
	 * @throws CustomSQLException
	 */
	public List<Company> getAll(Page page) throws CustomSQLException {
		List<Company> companies = new ArrayList<>();
		try (
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL_LIMIT)
		) {
			ps.setInt(1, paginable.getIndexFirstElement(page));
			ps.setInt(2, page.getNbElementByPage());
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					companies = mapper.toListCompany(mapperDTO.toListCompany(rs));
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
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ALL)
		) {
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					companies = mapper.toListCompany(mapperDTO.toListCompany(rs));
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
				Connection con = database.getConnection();
				PreparedStatement ps = con.prepareStatement(QUERY_SELECT_BY_ID)
		) {
			ps.setLong(1, id);
			logger.debug(ps.toString());
			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {
					company = Optional.of(mapper.toCompany(mapperDTO.toCompany(rs)));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to get a Company.");
		}
		return company;
	}

	public int count() throws CustomSQLException {

		return 0;
	}

	public int delete(long id) throws CustomSQLException {
		int nbComputerDeleted = 0;

		try (Connection con = database.getConnection()) {
			try {
				con.setAutoCommit(false);

				int nbCompanyDeleted = computerDAO.deleteByCompanyId(con,  id);

				PreparedStatement ps = con.prepareStatement(QUERY_DELETE);
				ps.setLong(1, id);
				ps.executeUpdate();
				logger.debug(ps.toString());

				con.commit();
				System.out.println("nbCompanyDeleted " + nbCompanyDeleted);

			} catch (SQLException e) {
				if (con != null) {
					con.rollback();
				}
				logger.error(e.getMessage(), e);
				throw new CustomSQLException("failure to delete a Company.");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new CustomSQLException("failure to communicate with the database.");
		}
		return nbComputerDeleted;
	}
}