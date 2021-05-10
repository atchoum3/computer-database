package com.excilys.persistence;

import java.sql.Connection;

public class DAO {
	
	protected Connection con;
	
	public DAO() {
		con = DatabaseImpl.getInstance().getConnection();
	}
	
}
