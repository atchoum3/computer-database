package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

import com.excilys.exception.ComputerDateGreaterLessThan1970;
import com.excilys.model.Computer;

public class ComputerMapper {
	
	private ComputerMapper() {}// static class, all method in this class are static

	public static Computer toComputer(ResultSet rs) throws SQLException {		
		long id = rs.getLong(1);
		String name = rs.getString(2);
		Timestamp timestampIntroduced = rs.getTimestamp(3);
		Timestamp timestampDiscontinued = rs.getTimestamp(4);
		
		LocalDateTime introduced = null;
		LocalDateTime discontinued = null;
		if (timestampIntroduced != null) 
			introduced = timestampIntroduced.toLocalDateTime();
		if (timestampDiscontinued != null)
			discontinued = timestampDiscontinued.toLocalDateTime();
		
		long idCompany = rs.getLong(5);
		return new Computer(id, name, introduced, discontinued, idCompany);
	}
	
	
	public static void toComputers(ResultSet rs, Collection<Computer> collection) throws SQLException {
		do {
			collection.add(ComputerMapper.toComputer(rs));
		} while(rs.next());
	}
	
	public static void setTimestampOrNull(PreparedStatement ps, int pos, LocalDateTime localDateTime) throws SQLException {
		if (localDateTime == null) {
			ps.setNull(pos, java.sql.Types.TIMESTAMP);
		} else {
			ps.setTimestamp(pos, Timestamp.valueOf(localDateTime));
		}
	}

}
