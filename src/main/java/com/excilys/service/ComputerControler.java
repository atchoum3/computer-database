package com.excilys.service;

import java.util.Optional;
import java.util.Set;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

public class ComputerControler {
	
	public Set<Computer> getAllComputer() {
		return ComputerDAO.getInstance().getAllComputers();
	}
	
	public Optional<Computer> getComputerById(long id) {
		return ComputerDAO.getInstance().getById(id);
	}
	
	public void createComputer(Computer computer) {
		ComputerDAO.getInstance().create(computer);
	}
	
	public void deleteComputer(long id) {
		ComputerDAO.getInstance().delete(id);
	}
	
	public void updateComputer(Computer computer) {
		ComputerDAO.getInstance().update(computer);
	}
}
