package com.excilys.persistence;

import java.sql.SQLException;

public interface IDatabase {
	public void connection() throws SQLException;
	public void close();
}
