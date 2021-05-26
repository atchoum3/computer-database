package com.excilys.cdb.controler.web;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerCompanyNameDTO;
import com.excilys.cdb.dto.mapper.ComputerMapper;
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
	private static final String SESION_NUMBER_BY_PAGE = "numberByPage";
	private static final String ATT_COMPUTER_LIST = "computerList";
	private static final String ATT_CURRENT_PAGE = "currentPage";
	private static final String ATT_MAX_PAGE = "maxPage";
	private static final String ATT_PAGE_INDEX_BEGIN = "pageIndexBegin";
	private static final String ATT_PAGE_INDEX_END = "pageIndexEnd";
	private static final String ATT_COMPUTER_NUMBER = "computerNumber";
	
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		Page page = getPage(req);
		System.out.println(page);
		
		List<Computer> computers = computerService.getAll(page);
		List<ComputerCompanyNameDTO> computersDTO = computerMapper.toComputerCompanyName(computers);
		req.setAttribute(ATT_COMPUTER_LIST, computersDTO);
		System.out.println(page);
		setPageAttributes(req, page);
		try {
			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private void setPageAttributes(HttpServletRequest req, Page page) {
		req.setAttribute(ATT_MAX_PAGE, page.getIndexLastPage());
		req.setAttribute(ATT_CURRENT_PAGE, page.getCurrentPage());
		req.setAttribute(ATT_COMPUTER_NUMBER, page.getTotalNumberElem());
		setIndexPageAttributes(req, page);
	}
	
	private void setIndexPageAttributes(HttpServletRequest req, Page page) {
		int half_page_window = INDEX_PAGE_WINDOW/2;
		int pageIndexBegin = Math.max(page.getCurrentPage() - half_page_window, page.INDEX_FIRST_PAGE);
		int pageIndexEnd = Math.min(pageIndexBegin + INDEX_PAGE_WINDOW -1, page.getIndexLastPage()); // because the loop on jsp do the last element
		req.setAttribute(ATT_PAGE_INDEX_BEGIN, pageIndexBegin);
		req.setAttribute(ATT_PAGE_INDEX_END, pageIndexEnd);
	}
	
	private Page getPage(HttpServletRequest req) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(req.getParameter(URL_PARAM_PAGE));
		} catch (NumberFormatException e) {
			currentPage = Page.INDEX_FIRST_PAGE;
		}
		
		HttpSession session = req.getSession();
		String stringNbByPage = (String) session.getAttribute(SESION_NUMBER_BY_PAGE);
		
		Integer nbByPage = DEFAULT_NB_ELEMENT_BY_PAGE;
		if (stringNbByPage != null) {
			nbByPage = Integer.valueOf(stringNbByPage);
		}
		
		return new Page(currentPage, nbByPage, computerService.count());
	}
}