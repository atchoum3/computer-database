package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.bindingFront.ComputerCompanyNameDTO;
import com.excilys.cdb.bindingFront.mapper.ComputerCompanyNameMapper;
import com.excilys.cdb.exception.CustomSQLException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/dashboard"}, name = "dashboard")
public class ListComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);

	private static final int INDEX_PAGE_WINDOW = 5;
	private static final int DEFAULT_NB_ELEMENT_BY_PAGE = 10;
	private static final int DEFAULT_ORDER_COLUMN = 2;
	private static final String VIEW = "/WEB-INF/view/dashboard.jsp";

	private static final String ATT_COMPUTER_LIST = "computerList";
	private static final String ATT_COMPUTER_NUMBER = "computerNumber";
	private static final String ATT_CURRENT_PAGE = "currentPage";
	private static final String ATT_ERRORS = "errors";
	private static final String ATT_MAX_PAGE = "maxPage";
	private static final String ATT_ORDER = "order";
	private static final String ATT_ORDER_REVERSED = "orderReversed";
	private static final String ATT_ORDER_COLUMN = "column";
	private static final String ATT_OTHER_ERROR = "otherError";
	private static final String ATT_PAGE_INDEX_BEGIN = "pageIndexBegin";
	private static final String ATT_PAGE_INDEX_END = "pageIndexEnd";
	private static final String ATT_SEARCH = "search";
	private static final String INPUT_ID_DELETE = "selection";
	private static final String SESSION_PAGE = "page";
	private static final String URL_PARAM_ELEM_BY_PAGE = "nbElemByPage";
	private static final String URL_PARAM_ORDER = "order";
	private static final String URL_PARAM_ORDER_COLUMN = "column";
	private static final String URL_PARAM_PAGE = "page";
	private static final String URL_PARAM_SEARCH_NAME = "search";

	private ComputerService computerService = ComputerService.getInstance();
	private ComputerCompanyNameMapper mapper = ComputerCompanyNameMapper.getInstance();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		String searchName = (String) req.getParameter(URL_PARAM_SEARCH_NAME);
		List<Computer> computers = null;

		try {
			Page page = getPage(req);
			System.err.println(page);

			if (searchName == null) {
				page.setTotalNumberElem(computerService.count());
				computers = computerService.getAll(page);
			} else {
				page.setTotalNumberElem(computerService.countSearchByName(searchName));
				computers = computerService.searchByName(searchName, page);
			}

			List<ComputerCompanyNameDTO> computersDTO = mapper.toComputerCompanyName(computers);
			req.setAttribute(ATT_COMPUTER_LIST, computersDTO);
			setPageAttributes(req, searchName, page);

			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		} catch (CustomSQLException e) {
			Map<String, String> errors = new HashMap<>();
			errors.put(ATT_OTHER_ERROR, "Error on database, try again.");
			req.setAttribute(ATT_ERRORS, errors);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String stringId = (String) req.getParameter(INPUT_ID_DELETE);

		Arrays.stream(stringId.split(","))
				.map(s -> Long.valueOf(s))
				.forEach(id -> {
					try {
						computerService.delete(id);
					} catch (CustomSQLException e1) {
						req.setAttribute(ATT_OTHER_ERROR, "All select computers have not been deleted. Try again.");
					}
				});
		doGet(req, resp);
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
	 * Get the column id to sort by the URL.
	 * @param req object to get the current page
	 * @return (1) the  column id to sort passed by GET method. (2) 1 if the parameter in URL does not exist.
	 */
	private int getSortedColumn(HttpServletRequest req) {
		int id;
		try {
			id = Integer.parseInt(req.getParameter(URL_PARAM_ORDER_COLUMN));
		} catch (NumberFormatException e) {
			id = 1;
		}
		return id;
	}

	/**
	 * Get the order by the URL.
	 * @param req object to get the current page
	 * @return return true if it is in ascincronous order
	 */
	private OrderBy getOrder(HttpServletRequest req) {
		String order = req.getParameter(URL_PARAM_ORDER);
		if ("DESC".equals(order)) {
			return OrderBy.DESC;
		}
		return OrderBy.ASC;
	}

	/**
	 * Get the number of element by page by the URL.
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

			page.setIndexColumn(getSortedColumn(req) + 1); // +1 because we don't print the first column (id)
			page.setOrder(getOrder(req));
		} else {
			page = new Page.Builder().withElementByPage(DEFAULT_NB_ELEMENT_BY_PAGE).withIndexColumn(DEFAULT_ORDER_COLUMN).build();
		}

		return page;
	}

	private void setIndexPageAttributes(HttpServletRequest req, Page page) {
		int halfPageWindow = INDEX_PAGE_WINDOW / 2;
		int pageIndexBegin = Math.max(page.getCurrentPage() - halfPageWindow, Page.INDEX_FIRST_PAGE);
		int pageIndexEnd = Math.min(pageIndexBegin + INDEX_PAGE_WINDOW - 1, page.getIndexLastPage()); // because the loop on jsp do the last element
		req.setAttribute(ATT_PAGE_INDEX_BEGIN, pageIndexBegin);
		req.setAttribute(ATT_PAGE_INDEX_END, pageIndexEnd);
	}

	private void setPageAttributes(HttpServletRequest req, String searchName, Page page) {
		req.setAttribute(ATT_SEARCH, searchName);
		req.setAttribute(ATT_ORDER, page.getOrder().name());
		req.setAttribute(ATT_ORDER_REVERSED, page.getOrder().reverse().name());
		req.setAttribute(ATT_ORDER_COLUMN, page.getIndexColumn() - 1); // because we don't print the first column (id)
		req.setAttribute(ATT_MAX_PAGE, page.getIndexLastPage());
		req.setAttribute(ATT_MAX_PAGE, page.getIndexLastPage());
		req.setAttribute(ATT_CURRENT_PAGE, page.getCurrentPage());
		req.setAttribute(ATT_COMPUTER_NUMBER, page.getTotalNumberElem());
		setIndexPageAttributes(req, page);

		HttpSession session = req.getSession();
		session.setAttribute(SESSION_PAGE, page);
	}


}