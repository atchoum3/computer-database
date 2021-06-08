package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}


	public List<Computer> getAll(Page page) throws CustomSQLException {
		return computerDAO.getAll(page);
	}

	public List<Computer> searchByName(String name, Page page) throws CustomSQLException {
		return computerDAO.searchByName(name, page);
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

	public void update(Computer computer) throws CustomSQLException, ComputerCompanyIdException {
		computerDAO.update(computer);
	}

	public int count() throws CustomSQLException {
		return computerDAO.count();
	}

	public int countSearchByName(String name) throws CustomSQLException {
		return computerDAO.countSearchByName(name);
	}
}
