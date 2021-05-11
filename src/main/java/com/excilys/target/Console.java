package com.excilys.target;

import java.sql.SQLException;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.Database;


public class Console {
	public static void main(String[] args) throws SQLException {
		Database.getInstance().connection();
		
		ComputerDAO c = ComputerDAO.getInstance();
		System.out.println(c.getById(67L));
			
	}
}
