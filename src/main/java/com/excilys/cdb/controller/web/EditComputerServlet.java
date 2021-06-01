package com.excilys.cdb.controller.web;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.bindingFront.AddComputerDTO;
import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.bindingFront.mapper.ComputerMapper;
import com.excilys.cdb.bindingFront.validator.EditComputerValidator;
import com.excilys.cdb.dao.mapper.CompanyMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/editComputer"}, name = "editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);
	
	public static final String VIEW = "/WEB-INF/view/editComputer.jsp";
	
	private static final String INPUT_COMPUTER_NAME = "computerName";
	private static final String INPUT_INTRODUCED = "introduced";
	private static final String INPUT_DISCONTINUED = "discontinued";
	private static final String INPUT_COMPANY_ID = "companyId";
	private static final String ATT_ALL_COMPANIES = "allCompanies";
	private static final String ATT_ERRORS = "errors";
	private static final String ATT_SUCCESS = "success";
	private static final String ATT_OTHER_ERROR = "otherError";
	private static final String ATT_COMPUTER = "computer";
	private static final String INPUT_ID = "id";
	private static final String URL_PARAM_ID = "id";
	
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerService computerService = ComputerService.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			setCompanyListOnView(req);
			setComputerOnView(req);
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
	
	private void setCompanyListOnView(HttpServletRequest req) throws CustomSQLException {
		req.setAttribute(ATT_ALL_COMPANIES, companyService.getAll());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {		
		try {
			setCompanyListOnView(req);
		} catch (CustomSQLException e) {
			Map<String, String> errors = new HashMap<>();
			errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			req.setAttribute(ATT_ERRORS, errors);
		}
		
		EditComputerDTO editComputerDTO = mapToDTO(req);
		editComputer(req, resp, editComputerDTO);
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private EditComputerDTO mapToDTO(HttpServletRequest req) {
		int companyId = 0, id = 0;
		String name = req.getParameter(INPUT_COMPUTER_NAME);
		String introduced = req.getParameter(INPUT_INTRODUCED);
		String discontinued = req.getParameter(INPUT_DISCONTINUED);
		try {
			companyId = Integer.parseInt(req.getParameter(INPUT_COMPANY_ID));
		} catch (NumberFormatException e) {
			req.setAttribute(INPUT_COMPANY_ID, "The company id must be a number.");
		}
		try {
			id = Integer.parseInt(req.getParameter(INPUT_ID));
		} catch (NumberFormatException e) {
			req.setAttribute(ATT_OTHER_ERROR, "The company id must be a number.");
		}
		return new EditComputerDTO.Builder(name).withIntroduced(introduced)
				.withDiscontinued(discontinued).withCompanyId(companyId).withId(id).build();
	}
	
	private void editComputer(HttpServletRequest req,  HttpServletResponse resp, EditComputerDTO  editComputerDTO) {
		boolean added = false;
		Map<String, String> errors = new HashMap<String, String>(); 
		EditComputerValidator editComputerValidator = new EditComputerValidator(errors); 
		
		Optional<Computer> computer = editComputerValidator.map(editComputerDTO);
		
		if (computer.isPresent()) {
			try {
				computerService.update(computer.get());
				added = true;
				req.setAttribute(ATT_SUCCESS, "This company has been edited.");
			} catch (ComputerCompanyIdException e) {
				errors.put(INPUT_COMPANY_ID, "This company id does not exist.");
			} catch (CustomSQLException e) {
				errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			}
		} else {
			req.setAttribute(ATT_ERRORS, errors);
		}
		req.setAttribute(ATT_COMPUTER, editComputerDTO);
	}
	
	private void setComputerOnView(HttpServletRequest req) throws CustomSQLException {
		int id = getNbElemByPage(req);
		if (id != -1) {
			Optional<Computer> computer = computerService.getById(id);
			if (computer.isPresent()) {
				EditComputerDTO editComputerDTO = ComputerMapper.getInstance().toEditComputerDTO(computer.get());
				req.setAttribute(ATT_COMPUTER, editComputerDTO);
			}
		}
	}

	/**
	 * Get the id of the computer to update.
	 * @param req object to get the number of element by page
	 * @return (1) the number of element by page passed by GET method. (2) -1 if the parameter in URL does not exist. 
	 */
	private int getNbElemByPage(HttpServletRequest req) {
		int id;
		try {
			id = Integer.parseInt(req.getParameter(URL_PARAM_ID));
		} catch (NumberFormatException e) {
			id = -1;
		}
		return id;
	}
}
