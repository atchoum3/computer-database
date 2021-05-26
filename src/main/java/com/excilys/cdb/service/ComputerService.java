package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

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
	
	public int count() {
		return computerDAO.count();
	}
}
