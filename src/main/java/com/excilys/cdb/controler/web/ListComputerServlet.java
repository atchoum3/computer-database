package com.excilys.cdb.controler.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerCompanyNameDTO;
import com.excilys.cdb.dto.mapper.ComputerMapper;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard")
public class ListComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);
	
	private static final int INDEX_PAGE_WINDOW = 5;
	private static final int DEFAULT_NB_ELEMENT_BY_PAGE = 10;
	private static final String VIEW = "/WEB-INF/view/dashboard.jsp";
	private static final String URL_PARAM_PAGE = "page";
	private static final String URL_PARAM_ELEM_BY_PAGE = "nbElemByPage";
	private static final String SESSION_PAGE = "page";
	private static final String ATT_COMPUTER_LIST = "computerList";
	private static final String ATT_CURRENT_PAGE = "currentPage";
	private static final String ATT_MAX_PAGE = "maxPage";
	private static final String ATT_PAGE_INDEX_BEGIN = "pageIndexBegin";
	private static final String ATT_PAGE_INDEX_END = "pageIndexEnd";
	private static final String ATT_COMPUTER_NUMBER = "computerNumber";
	private static final String ATT_ERRORS = "errors";
	private static final String ATT_OTHER_ERROR = "otherError";
	
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			Page page = getPage(req);
			
			List<Computer> computers = computerService.getAll(page);
			List<ComputerCompanyNameDTO> computersDTO = computerMapper.toComputerCompanyName(computers);
			req.setAttribute(ATT_COMPUTER_LIST, computersDTO);
			setPageAttributes(req, page);
			
			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		} catch (CustomSQLException e) {
			Map<String, String> errors = new HashMap<>();
			errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			req.setAttribute(ATT_ERRORS, errors);
		}
	}
	
	private void setPageAttributes(HttpServletRequest req, Page page) {
		req.setAttribute(ATT_MAX_PAGE, page.getIndexLastPage());
		req.setAttribute(ATT_CURRENT_PAGE, page.getCurrentPage());
		req.setAttribute(ATT_COMPUTER_NUMBER, page.getTotalNumberElem());
		setIndexPageAttributes(req, page);
		
		HttpSession session = req.getSession();
		session.setAttribute(SESSION_PAGE, page);
	}
	
	private void setIndexPageAttributes(HttpServletRequest req, Page page) {
		int halfPageWindow = INDEX_PAGE_WINDOW / 2;
		int pageIndexBegin = Math.max(page.getCurrentPage() - halfPageWindow, Page.INDEX_FIRST_PAGE);
		int pageIndexEnd = Math.min(pageIndexBegin + INDEX_PAGE_WINDOW - 1, page.getIndexLastPage()); // because the loop on jsp do the last element
		req.setAttribute(ATT_PAGE_INDEX_BEGIN, pageIndexBegin);
		req.setAttribute(ATT_PAGE_INDEX_END, pageIndexEnd);
	}
	
	private Page getPage(HttpServletRequest req) throws CustomSQLException {
		HttpSession session = req.getSession();
		Page page = (Page) session.getAttribute(SESSION_PAGE);

		if (page != null) {
			int currentPage = getCurrentpage(req);
			if (currentPage != -1) {
				page.setCurrentPage(currentPage);
			}
			
			
			int nbElemByPage = getNbElemByPage(req);
			if (nbElemByPage != -1) {
				page.setElementByPage(nbElemByPage);
			}
		} else {
			page = new Page(Page.INDEX_FIRST_PAGE, DEFAULT_NB_ELEMENT_BY_PAGE, computerService.count());
		}
		
		return page;
	}
	
	/**
	 * Get the current page by the URL.
	 * @param req object to get the current page
	 * @return (1) the current page passed by GET method. (2) -1 if the parameter in URL does not exist. 
	 */
	private int getCurrentpage(HttpServletRequest req) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(req.getParameter(URL_PARAM_PAGE));
		} catch (NumberFormatException e) {
			currentPage = -1;
		}
		return currentPage;
	}
	
	/**
	 * Get the he number of element by page by the URL.
	 * @param req object to get the number of element by page
	 * @return (1) the number of element by page passed by GET method. (2) -1 if the parameter in URL does not exist. 
	 */
	private int getNbElemByPage(HttpServletRequest req) {
		int nbElemByPage;
		try {
			nbElemByPage = Integer.parseInt(req.getParameter(URL_PARAM_ELEM_BY_PAGE));
		} catch (NumberFormatException e) {
			nbElemByPage = -1;
		}
		return nbElemByPage;
	}
}