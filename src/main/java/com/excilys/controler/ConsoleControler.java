package com.excilys.controler;

import java.util.List;
import java.util.Optional;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

public class ConsoleControler {
	private static ConsoleControler instance = null;
	private CompanyService companyService;
	private ComputerService computerService;
	
	private ConsoleControler() {
		companyService = CompanyService.getInstance();
		computerService = ComputerService.getInstance();
	}
	
	public static ConsoleControler getInstance() {
		if (instance == null)
			instance = new ConsoleControler();
		return instance;
	}
	
	public List<Company> getAllCompanies(Page page) {
		return companyService.getAll(page);
	}
	
	public Optional<Company> getCompanyById(long id) {
		return companyService.getById(id);
	}
	
	public List<Computer> getAllComputers(Page page) {
		return computerService.getAll(page);
	}
	
	public Optional<Computer> getComputerById(long id) {
		return computerService.getById(id);
	}
	
	public void createComputer(Computer computer) {
		computerService.create(computer);
	}
	
	public void deleteComputer(long id) {
		computerService.delete(id);
	}
	
	public void updateComputer(Computer computer) {
		computerService.update(computer);
	}
}
