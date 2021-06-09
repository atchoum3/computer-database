package com.excilys.cdb.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.excilys.cdb.SpringTestConfig;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@SpringJUnitConfig(SpringTestConfig.class)
public class CompanyDAOTest {

	private CompanyDAO companyDAO;

	private final int ELEM_BY_PAGE = 8;
	private Page page;

	public CompanyDAOTest(@Autowired CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;

		page = new Page.Builder().withNbElementByPage(ELEM_BY_PAGE).withNbElementTotal(companyDAO.count()).build();
	}

	@Test
	public void getAllShouldSucced() {
		List<Company> companies;
		companies = companyDAO.getAll(page);
		assertEquals(ELEM_BY_PAGE, companies.size());
	}
}
