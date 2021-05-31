package com.excilys.cdb.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;

public class ComputerServiceTest {
	private static ComputerService computerService = ComputerService.getInstance();

	
	public void getByIdShouldSucced() {
		int id = 3;
		Computer computer = new Computer.Builder("super Titan pro mega max plus X").build();

		Optional<Computer> returnValueMock = Optional.of(computer);
		
		try {
			when(computerService.getById(id)).thenReturn(returnValueMock);
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail();
		}
	}
}
