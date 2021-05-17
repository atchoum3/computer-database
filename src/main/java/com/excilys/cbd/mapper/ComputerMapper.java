package com.excilys.cbd.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;

import com.excilys.cbd.model.Computer;

public class ComputerMapper {
	
	/**
	 * Constructor is private because all method in this class are static.
	 */
	private ComputerMapper() {}// 

	/**
	 * Get data from a ResultSet to build a Computer object.
	 * @param rs a result set object from the SQL relation of computer
	 * @return Computer object
	 * @throws SQLException
	 */
	public static Computer toComputer(ResultSet rs) throws SQLException {		
		long id = rs.getLong(1);
		String name = rs.getString(2);
		Timestamp timestampIntroduced = rs.getTimestamp(3);
		Timestamp timestampDiscontinued = rs.getTimestamp(4);
		
		LocalDate introduced = null;
		LocalDate discontinued = null;
		if (timestampIntroduced != null) 
			introduced = timestampIntroduced.toLocalDateTime().toLocalDate();
		if (timestampDiscontinued != null)
			discontinued = timestampDiscontinued.toLocalDateTime().toLocalDate();
		
		long idCompany = rs.getLong(5);
		return new Computer(id, name, introduced, discontinued, idCompany);
	}
	
	/**
	 * Get data from a ResultSet to fill in the collection.
	 * @param rs a result set object from the SQL relation of computer
	 * @param collection to fill in
	 * @throws SQLException
	 */
	public static void toComputers(ResultSet rs, Collection<Computer> collection) throws SQLException {
		do {
			collection.add(ComputerMapper.toComputer(rs));
		} while(rs.next());
	}
	
	/**
	 * To insert a Timestamp on a PreparedStatement. This method insert the timestamp or the null value.
	 * @param ps a PreparedStatement object
	 * @param pos the position where will be inserted the Timestamp value
	 * @param localDateTime this value will be converted in Timestamp value. 
	 * If this value is null, null value is insert on the prepared statement
	 * @throws SQLException
	 */
	public static void setTimestampOrNull(PreparedStatement ps, int pos, LocalDate localDate) 
			throws SQLException {
		if (localDate == null) {
			ps.setNull(pos, java.sql.Types.TIMESTAMP);
		} else {
			ps.setTimestamp(pos, Timestamp.valueOf(localDate.atStartOfDay()));
		}
	}

}
