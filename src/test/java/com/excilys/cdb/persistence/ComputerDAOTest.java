package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDAOTest {

	private static ComputerDAO computerDAO = ComputerDAO.getInstance();
	private final long COMPUTER_REFERENCED_ID = 321;
	private final Computer COMPUTER_TO_UPDATE;
	private final Company COMPANY_COMPUTER_TO_UPDATE;
	private final int ELEM_BY_PAGE = 4;
	private final long COMPANY_ID_NOT_EXIST = -1;
	private final Page PAGE;
	
	public ComputerDAOTest() {		
		LocalDate date1 = LocalDate.of(2010, 1, 7);
		LocalDate date2 = LocalDate.of(2011, 1, 3);
		COMPANY_COMPUTER_TO_UPDATE = new Company(4, "Netronics"); // company exist on database
		COMPUTER_TO_UPDATE = new Computer.Builder(44, "MyCompyuter").introduced(date1).discontinued(date2).company(COMPANY_COMPUTER_TO_UPDATE).build();
		
		PAGE = new Page(1, ELEM_BY_PAGE, computerDAO.count());
	}

	@Test
	public void getByIdRightParam() {
		Optional<Computer> computer = computerDAO.getById(COMPUTER_REFERENCED_ID);
		assertEquals(true, computer.isPresent());
		assertEquals(COMPUTER_REFERENCED_ID, computer.get().getId());
	}
	
	@Test
	public void updateRightParam() {
		computerDAO.update(COMPUTER_TO_UPDATE);
		Optional<Computer> computer = computerDAO.getById(COMPUTER_TO_UPDATE.getId());
		assertEquals(true, computer.isPresent());
		assertEquals(COMPUTER_TO_UPDATE, computer.get());
		
		// post treatment
		computerDAO.delete(COMPUTER_TO_UPDATE.getId());
	}
	
	@Test
	public void getAllRightParam() {
		List<Computer> computers =  computerDAO.getAll(PAGE);
		assertEquals(ELEM_BY_PAGE, computers.size());
	}
	
	@Test 
	public void deleteRightParam() {
		computerDAO.delete(COMPUTER_REFERENCED_ID);
		Optional<Computer> opt = computerDAO.getById(COMPUTER_REFERENCED_ID);
		assertEquals(false, opt.isPresent());
	}
	
	@Test
	public void createRightParam() throws ComputerCompanyIdException {
		LocalDate date1 = LocalDate.of(2007, 3, 7);
		LocalDate date2 = LocalDate.of(2010, 9, 3);
		Computer computer = new Computer.Builder("Hey").introduced(date1).discontinued(date2).build();
		computerDAO.create(computer);
		
		Optional<Computer> opt = computerDAO.getById(computer.getId());
		assertEquals(true, opt.isPresent());
		assertEquals(computer, opt.get());
		
		// post treatment
		computerDAO.delete(computer.getId());
	}
	
	@Test
	public void createWrongCompanyId() {
		LocalDate date1 = LocalDate.of(2007, 3, 7);
		LocalDate date2 = LocalDate.of(2010, 9, 3);
		Company company = new Company(COMPANY_ID_NOT_EXIST);
		Computer computer = new Computer.Builder("Hey").introduced(date1).discontinued(date2).company(company).build();
		try {
			computerDAO.create(computer);
			fail("exception ComputerCompanyIdException not thrown");
			computerDAO.delete(computer.getId());
		} catch (ComputerCompanyIdException e) {
		}
	}
	
}
