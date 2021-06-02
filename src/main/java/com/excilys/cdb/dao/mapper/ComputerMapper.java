package com.excilys.cdb.dao.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {
	private static ComputerMapper instance;

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
		}

		return new Computer.Builder(name).withId(id).withIntroduced(introduced)
				.withDiscontinued(discontinued).withCompany(company).build();
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

}