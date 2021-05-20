package com.excilys.cbd.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cbd.exception.ComputerCompanyIdException;
import com.excilys.cbd.model.Computer;
import com.excilys.cbd.model.Page;
import com.excilys.cbd.persistence.ComputerDAO;

public class ComputerService {
	private static ComputerService instance = null;
	private ComputerDAO computerDAO;
	
	private ComputerService() {
		computerDAO = ComputerDAO.getInstance();
	}
	
	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}
	
	public List<Computer> getAll(Page page) {
		return computerDAO.getAll(page);
	}
	
	public Optional<Computer> getById(long id) {
		return computerDAO.getById(id);
	}
	
	public void create(Computer computer) throws ComputerCompanyIdException {
		computerDAO.create(computer);
	}
	
	public void delete(long id) {
		computerDAO.delete(id);
	}
	
	public void update(Computer computer) {
		computerDAO.update(computer);
	}
}
