package com.excilys.cdb.controller.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.dto.validator.AddComputerValidator;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


@WebServlet("/addComputer")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ComputerServlet.class);

	private static final String INPUT_COMPUTER_NAME = "computerName";
	private static final String INPUT_INTRODUCED = "introduced";
	private static final String INPUT_DISCONTINUED = "discontinued";
	private static final String INPUT_COMPANY_ID = "companyId";
	private static final String ATT_ALL_COMPANIES = "allCompanies";
	private static final String ATT_ERRORS = "errors";
	private static final String ATT_ADDED = "added";
	private static final String ATT_OTHER_ERROR = "otherError";
	private static final String ATT_COMPUTER = "computer";
	
	private static final String VIEW = "/WEB-INF/view/addComputer.jsp";
	
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerService computerService = ComputerService.getInstance();
	
	public ComputerServlet() {
		super();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			addCompanyList(req);
		} catch (CustomSQLException e) {
			Map<String, String> errors = new HashMap<>();
			errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			req.setAttribute(ATT_ERRORS, errors);
		}
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void addCompanyList(HttpServletRequest req) throws CustomSQLException {
		req.setAttribute(ATT_ALL_COMPANIES, companyService.getAll());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		AddComputerDTO computerDTO = mapToDTO(req);
		addComputer(req, computerDTO);
		doGet(req, resp);
	}
	
	private AddComputerDTO mapToDTO(HttpServletRequest req) {
		int companyId = 0;
		String name = req.getParameter(INPUT_COMPUTER_NAME);
		String introduced = req.getParameter(INPUT_INTRODUCED);
		String discontinued = req.getParameter(INPUT_DISCONTINUED);
		try {
			companyId = Integer.parseInt(req.getParameter(INPUT_COMPANY_ID));
		} catch (NumberFormatException e) {
			req.setAttribute(INPUT_COMPANY_ID, "The company id must be a number.");
		}
		return new AddComputerDTO.Builder(name).introduced(introduced)
				.discontinued(discontinued).companyId(companyId).build();
	}
	
	/**
	 * Try to save the computer if each field are correct.
	 * @param req object to set error on the page
	 * @param addComputerDTO the computer to try to add
	 * @return true if the computer is added, otherwise false.
	 */
	private boolean addComputer(HttpServletRequest req, AddComputerDTO addComputerDTO) {
		Map<String, String> errors = new HashMap<String, String>(); 
		AddComputerValidator addComputerValidator = new AddComputerValidator(errors); 
		
		Optional<Computer> computer = addComputerValidator.validate(addComputerDTO);
		
		if (computer.isPresent()) {
			try {
				computerService.create(computer.get());
				req.setAttribute(ATT_ADDED, "This company has been added.");
				return true;
			} catch (ComputerCompanyIdException e) {
				errors.put(INPUT_COMPANY_ID, "This company id does not exist.");
			} catch (CustomSQLException e) {
				errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			}
		}
		req.setAttribute(ATT_ERRORS, errors);
		req.setAttribute(ATT_COMPUTER, addComputerDTO);
		req.setAttribute(ATT_ADDED, "");
		return false;
	}
}
