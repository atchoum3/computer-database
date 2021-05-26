package com.excilys.cdb.controler.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.controler.web.validator.AddComputerValidator;
import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.dto.mapper.ComputerMapperDTO;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


@WebServlet("/addComputer")
public class ComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ComputerServlet.class);
	
	public static final String ATT_ALL_COMPANIES = "allCompanies";
	public static final String ATT_ERRORS = "errors";
	public static final String ATT_ADDED = "added";
	
	public static final String INPUT_COMPUTER_NAME = "computerName";
	public static final String INPUT_INTRODUCED = "introduced";
	public static final String INPUT_DISCONTINUED = "discontinued";
	public static final String INPUT_COMPANY_ID = "companyId";
	
	private static final String VIEW = "/WEB-INF/view/addComputer.jsp";
	
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapperDTO computerMapperDTO = ComputerMapperDTO.getInstance();
	private AddComputerValidator addComputerValidator;
	
	public ComputerServlet() {
		super();
		addComputerValidator = new AddComputerValidator();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		addCompanyList(req);
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void addCompanyList(HttpServletRequest req) {
		req.setAttribute(ATT_ALL_COMPANIES, companyService.getAll());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addComputer(req);
		doGet(req, resp);
	}
	
	private AddComputerDTO addComputer(HttpServletRequest req) {
		int companyId = 0;
		String name = req.getParameter(INPUT_COMPUTER_NAME);
		String introduced = req.getParameter(INPUT_INTRODUCED);
		String discontinued = req.getParameter(INPUT_DISCONTINUED);
		try {
			companyId = Integer.parseInt(req.getParameter(INPUT_COMPANY_ID));
		} catch (NumberFormatException e) {
			req.setAttribute(INPUT_COMPANY_ID, "The company id must be a number.");
		}
		
		AddComputerDTO addComputerDTO = new AddComputerDTO(name, introduced, discontinued, companyId);
		Map<String, String> errors = addComputerValidator.validate(addComputerDTO);
		
		if (errors.isEmpty()) {
			Computer computer = computerMapperDTO.toComputer(addComputerDTO);
			try {
				computerService.create(computer);
				req.setAttribute(ATT_ADDED, "This company has been added.");
			} catch (ComputerCompanyIdException e) {
				req.setAttribute(INPUT_COMPANY_ID, "This company id does not exist.");
			}
		} else {
			req.setAttribute(ATT_ERRORS, errors);
		}
		return addComputerDTO;
	}
}
