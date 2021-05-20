package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cbd.model.Company;
import com.excilys.cbd.model.Page;
import com.excilys.cbd.persistence.CompanyDAO;
import com.excilys.cbd.persistence.Database;

public class CompanyDAOTest {
	private static CompanyDAO companyDAO;
	private final int ELEM_BY_PAGE = 8;
	private final int COMPANY_REFERENCED_ID = 22;
	private final Company COMPANY_COMPUTER_REFERENCED;
	
	public CompanyDAOTest() {
		COMPANY_COMPUTER_REFERENCED = new Company(COMPANY_REFERENCED_ID, "Timex Sinclair");
	}
	
	@BeforeAll
	public static void setUpInstance() {
		companyDAO = CompanyDAO.getInstance();
	}
	
	@Test
	public void getAllRightParam() {
		Page page = new Page(ELEM_BY_PAGE);
		List<Company> companies =  companyDAO.getAll(page);
		assertEquals(ELEM_BY_PAGE, companies.size());
	}
	
	@Test
	public void getByIdRightParam() {
		Optional<Company> companies = companyDAO.getById(COMPANY_COMPUTER_REFERENCED.getId());
		assertEquals(true, companies.isPresent());
		assertEquals(COMPANY_COMPUTER_REFERENCED, companies.get());
	}
}
