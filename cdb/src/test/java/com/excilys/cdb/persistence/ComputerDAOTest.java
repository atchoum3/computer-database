package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cbd.model.Company;
import com.excilys.cbd.model.Computer;
import com.excilys.cbd.persistence.ComputerDAO;
import com.excilys.cbd.persistence.Database;

public class ComputerDAOTest {

	private static ComputerDAO computerDAO;
	private final long GET_BY_ID = 23;
	private final Computer UPDATE_COMPUTER;
	private final Company UPDATE_COMPANY;
	private final long UPDATE_COMPUTER_ID = 44;
	
	public ComputerDAOTest() {
		LocalDate date1 = LocalDate.of(2010, 1, 7);
		LocalDate date2 = LocalDate.of(2011, 1, 3);
		UPDATE_COMPANY = new Company(4, "Netronics"); // company exist on database
		UPDATE_COMPUTER = new Computer.Builder(UPDATE_COMPUTER_ID, "MyCompyuter").introduced(date1).discontinued(date2).company(UPDATE_COMPANY).build();
	}
	
	@BeforeAll
	public static void setUpInstance() {
		computerDAO = ComputerDAO.getInstance();
	}
	
	@BeforeEach
	public void setUp() {
		 try {
			Database.getInstance().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void tearDown() {
		Database.getInstance().close();
	}

	@Test
	public void getById() {
		Optional<Computer> computer = computerDAO.getById(GET_BY_ID);
		assertEquals(true, computer.isPresent());
		assertEquals(GET_BY_ID, computer.get().getId());
	}
	
	@Test
	public void update() {
		computerDAO.update(UPDATE_COMPUTER);
		Optional<Computer> computer = computerDAO.getById(UPDATE_COMPUTER_ID);
		assertEquals(true, computer.isPresent());
		assertEquals(UPDATE_COMPUTER, computer.get());
	}
	
}
