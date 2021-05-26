package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyDAOTest {
	private static CompanyDAO companyDAO = CompanyDAO.getInstance();
	private final int ELEM_BY_PAGE = 8;
	private final int COMPANY_REFERENCED_ID = 22;
	private final Company COMPANY_COMPUTER_REFERENCED;
	private Page page;
	
	public CompanyDAOTest() {
		COMPANY_COMPUTER_REFERENCED = new Company(COMPANY_REFERENCED_ID, "Timex Sinclair");
		try {
			page = new Page(1, ELEM_BY_PAGE, companyDAO.count());
		} catch (CustomSQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllRightParam() {
		List<Company> companies;
		try {
			companies = companyDAO.getAll(page);
			assertEquals(ELEM_BY_PAGE, companies.size());
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail("CustomSQLException thrown");
		}
		
	}
	
	@Test
	public void getByIdRightParam() {
		Optional<Company> companies;
		try {
			companies = companyDAO.getById(COMPANY_COMPUTER_REFERENCED.getId());
			assertEquals(true, companies.isPresent());
			assertEquals(COMPANY_COMPUTER_REFERENCED, companies.get());
		} catch (CustomSQLException e) {
			e.printStackTrace();
			fail("CustomSQLException thrown");
		}
	}
}
