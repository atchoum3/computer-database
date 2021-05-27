package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
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
	
	public List<Computer> getAll(Page page) throws CustomSQLException {
		return computerDAO.getAll(page);
	}
	
	public Optional<Computer> getById(long id) throws CustomSQLException {
		return computerDAO.getById(id);
	}
	
	public void create(Computer computer) throws ComputerCompanyIdException, CustomSQLException {
		computerDAO.create(computer);
	}
	
	public boolean delete(long id) throws CustomSQLException {
		return computerDAO.delete(id);
	}
	
	public void update(Computer computer) throws CustomSQLException {
		computerDAO.update(computer);
	}
	
	public int count() throws CustomSQLException {
		return computerDAO.count();
	}
}