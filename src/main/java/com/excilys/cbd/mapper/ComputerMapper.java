package com.excilys.cbd.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cbd.model.Company;
import com.excilys.cbd.model.Computer;
import com.excilys.cbd.persistence.ComputerDAO;

public class ComputerMapper {
	private static ComputerMapper instance;
	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	
	private static final int KEY_NULL = 0;

	private ComputerMapper() { }
	
	public static ComputerMapper getInstance() {
		if (instance == null) {
			instance = new ComputerMapper();
		}
		return instance;
	}

	/**
	 * Get data from a ResultSet to build a Computer object.
	 * @param rs a result set object from the SQL relation of computer
	 * @return Computer object
	 * @throws SQLException
	 */
	public Computer toComputer(ResultSet rs) throws SQLException {		
		long id = rs.getLong(1);
		String name = rs.getString(2);
		Timestamp timestampIntroduced = rs.getTimestamp(3);
		Timestamp timestampDiscontinued = rs.getTimestamp(4);
		
		LocalDate introduced = null;
		LocalDate discontinued = null;
		if (timestampIntroduced != null) { 
			introduced = timestampIntroduced.toLocalDateTime().toLocalDate();
		}
		if (timestampDiscontinued != null) {
			discontinued = timestampDiscontinued.toLocalDateTime().toLocalDate();
		}
		
		long companyId = rs.getLong(5);
		String companyName = rs.getString(6);
		Company company = null;
		
		if (companyId != KEY_NULL) { 
			company = new Company(companyId, companyName);
		} else {
			logger.debug("company_id null");
		}
		
		return new Computer.Builder(id, name).introduced(introduced).discontinued(discontinued).company(company).build();
	}
	
	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a result set object from the SQL relation of computer
	 * @param collection to fill in
	 * @throws SQLException
	 */
	public void toComputers(ResultSet rs, Collection<Computer> collection) throws SQLException {
		do {
			collection.add(this.toComputer(rs));
		} while (rs.next());
	}
	
	/**
	 * To insert a Timestamp on a PreparedStatement. This method insert the timestamp or the null value.
	 * @param ps a PreparedStatement object
	 * @param pos the position where will be inserted the Timestamp value
	 * @param localDate this value will be converted in Timestamp value. 
	 * If this value is null, null value is insert on the prepared statement
	 * @throws SQLException
	 */
	public void setTimestampOrNull(PreparedStatement ps, int pos, LocalDate localDate) 
			throws SQLException {
		if (localDate == null) {
			ps.setNull(pos, java.sql.Types.NULL);
		} else {
			ps.setTimestamp(pos, Timestamp.valueOf(localDate.atStartOfDay()));
		}
	}

	public void setCompanyOrNull(PreparedStatement ps, int pos, Company company) 
			throws SQLException {
		if (company == null) {
			ps.setNull(pos, java.sql.Types.NULL);
		} else {
			ps.setLong(pos, company.getId());
		}
			
		
	}

}
