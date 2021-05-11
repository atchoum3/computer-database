package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
	
	
	public static Set<Computer> toComputers(ResultSet rs) throws SQLException {
		Set<Computer> computers = new HashSet<>();
		do {
			computers.add(ComputerMapper.toComputer(rs));
		} while(rs.next());
		return computers;
	}
	
	public static void setTimestampOrNull(PreparedStatement ps, int pos, LocalDateTime localDateTime) throws SQLException {
		if (localDateTime == null) {
			ps.setNull(pos, java.sql.Types.TIMESTAMP);
		} else {
			ps.setTimestamp(pos, Timestamp.valueOf(localDateTime));
		}
	}

}
