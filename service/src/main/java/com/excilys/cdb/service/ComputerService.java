package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	private ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public List<Computer> searchByName(String name, Page page) {
		return computerDAO.searchByName(name, page);
	}

	public Optional<Computer> getById(long id) {
		return computerDAO.getById(id);
	}

	public void create(Computer computer) throws ComputerCompanyIdException {
		computerDAO.create(computer);
	}

	public int delete(long id) {
		return computerDAO.delete(id);
	}

	public void update(Computer computer) throws ComputerCompanyIdException {
		computerDAO.update(computer);
	}

	public int countSearchByName(String name) {
		return computerDAO.countSearchByName(name);
	}
}
