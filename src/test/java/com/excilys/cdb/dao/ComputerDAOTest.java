package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDAOTest {

	private static ComputerDAO computerDAO = ComputerDAO.getInstance();
	private final long COMPUTER_REFERENCED_ID = 321;
	private final long COMPUTER_REFERENCED_ID_DELETE = 232;
	private final Computer COMPUTER_TO_UPDATE;
	private final Company COMPANY_COMPUTER_TO_UPDATE;
	private final int ELEM_BY_PAGE = 4;
	private final long COMPANY_ID_NOT_EXIST = -1;
	private  Page page;

	public ComputerDAOTest() {
		LocalDate date1 = LocalDate.of(2010, 1, 7);
		LocalDate date2 = LocalDate.of(2011, 1, 3);
		COMPANY_COMPUTER_TO_UPDATE = new Company(4, "Netronics"); // company exist on database
		COMPUTER_TO_UPDATE = new Computer.Builder("MyCompyuter").withId(44).withIntroduced(date1)
				.withDiscontinued(date2).withCompany(COMPANY_COMPUTER_TO_UPDATE).build();

		try {
			page = new Page.Builder().withElementByPage(ELEM_BY_PAGE).withTotalNumberElem(computerDAO.count()).build();
		} catch (CustomSQLException e) {
			page = null;
			e.printStackTrace();
		}
	}

	@Test
	public void getByIdShouldSucced() {
		Optional<Computer> computer;
		try {
			computer = computerDAO.getById(COMPUTER_REFERENCED_ID);
			assertEquals(true, computer.isPresent());
			assertEquals(COMPUTER_REFERENCED_ID, computer.get().getId());
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void updateShouldSucced() {
		try {
			computerDAO.update(COMPUTER_TO_UPDATE);
			Optional<Computer> computer = computerDAO.getById(COMPUTER_TO_UPDATE.getId());
			assertEquals(true, computer.isPresent());
			assertEquals(COMPUTER_TO_UPDATE, computer.get());
		} catch (CustomSQLException | ComputerCompanyIdException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getAllShouldSucced() {
		List<Computer> computers;
		try {
			computers = computerDAO.getAll(page);
			assertEquals(ELEM_BY_PAGE, computers.size());
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void deleteShouldSucced() {
		try {
			computerDAO.delete(COMPUTER_REFERENCED_ID_DELETE);
			Optional<Computer> opt = computerDAO.getById(COMPUTER_REFERENCED_ID_DELETE);
			assertEquals(false, opt.isPresent());
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void createShouldSucced() throws ComputerCompanyIdException {
		LocalDate date1 = LocalDate.of(2007, 3, 7);
		LocalDate date2 = LocalDate.of(2010, 9, 3);
		Computer computer = new Computer.Builder("Hey").withIntroduced(date1).withDiscontinued(date2).build();

		try {
			computerDAO.create(computer);
			System.out.println(computer.getId());
			Optional<Computer> opt = computerDAO.getById(computer.getId());
			assertEquals(true, opt.isPresent());
			assertEquals(computer, opt.get());

		} catch (ComputerCompanyIdException | CustomSQLException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void createWrongCompanyId() {
		LocalDate date1 = LocalDate.of(2007, 3, 7);
		LocalDate date2 = LocalDate.of(2010, 9, 3);
		Company company = new Company(COMPANY_ID_NOT_EXIST);
		Computer computer = new Computer.Builder("Hey").withIntroduced(date1)
				.withDiscontinued(date2).withCompany(company).build();
		try {
			computerDAO.create(computer);
			fail("exception ComputerCompanyIdException not thrown");
		} catch (ComputerCompanyIdException e) {
			// success test
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail();
		}
	}

}
