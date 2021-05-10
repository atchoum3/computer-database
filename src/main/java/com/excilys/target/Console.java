package com.excilys.target;

import java.sql.SQLException;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAOImpl;
import com.excilys.persistence.DatabaseImpl;
import com.excilys.persistence.IComputerDAO;


public class Console {
	public static void main(String[] args) throws SQLException {
		DatabaseImpl.getInstance().connection();
		
		IComputerDAO c = ComputerDAOImpl.getInstance();
		System.out.println(c.getById(67L));
			
	}
}
