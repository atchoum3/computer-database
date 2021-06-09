package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.SpringConfig;
import com.excilys.cdb.bindingFront.ComputerCompanyNameDTO;
import com.excilys.cdb.bindingFront.PageDTO;
import com.excilys.cdb.bindingFront.mapper.ComputerCompanyNameMapper;
import com.excilys.cdb.bindingFront.mapper.PageMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.Paginable;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@WebServlet(urlPatterns = {"/dashboard"}, name = "dashboard")
public class ListComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);

	private static final int DEFAULT_CURRENT_PAGE = 1;
	private static final int DEFAULT_ELEM_BY_PAGE = 10;


	private static final String ATT_COMPUTER_LIST = "computerList";
	private static final String ATT_PAGE = "page";
	private static final String ATT_SEARCH = "search";
	private static final String INPUT_ID_DELETE = "selection";
	private static final String URL_PARAM_CURRENT_PAGE = "currentPage";
	private static final String URL_PARAM_ELEM_BY_PAGE = "nbElementByPage";
	private static final String URL_PARAM_ORDER = "order";
	private static final String URL_PARAM_ORDER_COLUMN = "column";
	private static final String URL_PARAM_SEARCH_NAME = "search";
	private static final String VIEW = "/WEB-INF/view/dashboard.jsp";


	private ComputerService computerService;
	private ComputerCompanyNameMapper mapper;
	private PageMapper pageMapper;
	private Paginable paginable;

	@Override
	public void init() {
		try {
			super.init();
			ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
			computerService = context.getBean(ComputerService.class);
			mapper = context.getBean(ComputerCompanyNameMapper.class);
			paginable = context.getBean(Paginable.class);
			pageMapper = context.getBean(PageMapper.class);

		} catch (ServletException e) {
			logger.error(e.getMessage(), e);
		}
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		logger.debug(req.getQueryString());

		String searchName = (String) req.getParameter(URL_PARAM_SEARCH_NAME);
		if (searchName == null) {
			searchName = "";
		}

		try {
			Page page = getPage(req);
			paginable.setNbElementTotal(page, computerService.countSearchByName(searchName));
			paginable.changeCurrentPage(page, getCurrentpage(req));

			List<Computer> computers = computerService.searchByName(searchName, page);
			List<ComputerCompanyNameDTO> computersDTO = mapper.toComputerCompanyName(computers);

			req.setAttribute(ATT_COMPUTER_LIST, computersDTO);
			setPageAttributes(req, searchName, page);

			this.getServletContext().getRequestDispatcher(VIEW).forward(req, resp);
		} catch (ServletException | IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		logger.debug(req.getQueryString());

		String stringId = (String) req.getParameter(INPUT_ID_DELETE);

		Arrays.stream(stringId.split(","))
				.map(s -> Long.valueOf(s))
				.forEach(id -> computerService.delete(id));
		doGet(req, resp);
	}

	private int getCurrentpage(HttpServletRequest req) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(req.getParameter(URL_PARAM_CURRENT_PAGE));
		} catch (NumberFormatException e) {
			currentPage = DEFAULT_CURRENT_PAGE;
		}
		return currentPage;
	}

	private OrderBy getSortedColumn(HttpServletRequest req) {
		OrderBy column;
		String paramColumn = req.getParameter(URL_PARAM_ORDER_COLUMN);

		if (paramColumn == null) {
			column = OrderBy.COMPUTER_NAME;
		} else {
			column = OrderBy.valueOf(paramColumn);
		}
		return column;
	}

	private Order getOrder(HttpServletRequest req) {
		String order = req.getParameter(URL_PARAM_ORDER);
		if ("DESC".equals(order)) {
			return Order.DESC;
		}
		return Order.ASC;
	}

	private int getNbElemByPage(HttpServletRequest req) {
		int nbElemByPage;
		try {
			nbElemByPage = Integer.parseInt(req.getParameter(URL_PARAM_ELEM_BY_PAGE));
		} catch (NumberFormatException e) {
			nbElemByPage = DEFAULT_ELEM_BY_PAGE;
		}
		return nbElemByPage;
	}


	private Page getPage(HttpServletRequest req) {
		PageDTO.Builder builder = new PageDTO.Builder();
		builder.withColumn(getSortedColumn(req));
		builder.withOrder(getOrder(req));
		builder.withNbElementByPage(getNbElemByPage(req));
		return pageMapper.toPage(builder.build());
	}

	private void setPageAttributes(HttpServletRequest req, String searchName, Page page) {
		PageDTO pageDTO = pageMapper.toPageDTO(page);
		req.setAttribute(ATT_PAGE, pageDTO);
		req.setAttribute(ATT_SEARCH, searchName);
	}


}