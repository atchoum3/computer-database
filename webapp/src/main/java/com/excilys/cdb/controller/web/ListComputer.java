package com.excilys.cdb.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/computer")
public class ListComputer {
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

	public static final String VIEW = "dashboard";

	private ComputerService computerService;
	private PageMapper pageMapper;
	private ComputerCompanyNameMapper computerMapper;
	private Paginable paginable;


	public ListComputer(ComputerService computerService, PageMapper pageMapper, ComputerCompanyNameMapper computerMapper, Paginable paginable) {
		this.computerService = computerService;
		this.pageMapper = pageMapper;
		this.computerMapper = computerMapper;
		this.paginable = paginable;
	}

	@GetMapping("/list")
	protected ModelAndView diplayListComputer(
			@RequestParam(name = URL_PARAM_SEARCH_NAME, defaultValue = "") String searchName,
			@RequestParam(name = URL_PARAM_ELEM_BY_PAGE, required = false) String nbElemByPage,
			@RequestParam(name = URL_PARAM_ORDER, required = false) String order,
			@RequestParam(name = URL_PARAM_ORDER_COLUMN, required = false) String column,
			@RequestParam(name = URL_PARAM_CURRENT_PAGE, required = false) String currentPage
	) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		Page page = getPage(column, order, nbElemByPage);
		paginable.setNbElementTotal(page, computerService.countSearchByName(searchName));
		paginable.changeCurrentPage(page, getCurrentpage(currentPage));

		List<Computer> computers = computerService.searchByName(searchName, page);
		List<ComputerCompanyNameDTO> computersDTO = computerMapper.toComputerCompanyName(computers);

		PageDTO pageDTO = pageMapper.toPageDTO(page);
		modelAndView.addObject(ATT_PAGE, pageDTO);
		modelAndView.addObject(ATT_COMPUTER_LIST, computersDTO);
		modelAndView.addObject(ATT_SEARCH, searchName);
		return modelAndView;
	}


	@PostMapping("/delete")
	protected RedirectView deleteComputer(@RequestParam(INPUT_ID_DELETE) String stringId) {
		Arrays.stream(stringId.split(","))
				.map(s -> Long.valueOf(s))
				.forEach(id -> computerService.delete(id));
		return new RedirectView("/computer/list", true);
	}



	private Page getPage(String column, String order, String nbElementByPage) {
		PageDTO.Builder builder = new PageDTO.Builder();
		builder.withColumn(getSortedColumn(column));
		builder.withOrder(getOrder(order));
		builder.withNbElementByPage(getNbElemByPage(nbElementByPage));
		return pageMapper.toPage(builder.build());
	}

	private int getCurrentpage(String string) {
		int currentPage;
		try {
			currentPage = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			currentPage = DEFAULT_CURRENT_PAGE;
		}
		return currentPage;
	}

	private OrderBy getSortedColumn(String string) {
		OrderBy column;
		if (string == null) {
			column = OrderBy.COMPUTER_NAME;
		} else {
			column = OrderBy.valueOf(string);
		}
		return column;
	}

	private Order getOrder(String string) {
		if ("DESC".equals(string)) {
			return Order.DESC;
		}
		return Order.ASC;
	}

	private int getNbElemByPage(String string) {
		int nbElemByPage;
		try {
			nbElemByPage = Integer.parseInt(string);
		} catch (NumberFormatException e) {
			nbElemByPage = DEFAULT_ELEM_BY_PAGE;
		}
		return nbElemByPage;
	}


}
