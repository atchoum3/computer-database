package com.excilys.cbd.persistence;

import java.sql.Connection;

public class DAO {
	
	protected Connection con;
	
	public DAO() {
		con = Database.getInstance().getConnection();
	}
	
}
