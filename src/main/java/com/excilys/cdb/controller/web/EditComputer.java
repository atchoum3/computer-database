package com.excilys.cdb.controller.web;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.bindingFront.CompanyDTO;
import com.excilys.cdb.bindingFront.EditComputerDTO;
import com.excilys.cdb.bindingFront.mapper.CompanyMapper;
import com.excilys.cdb.bindingFront.mapper.EditComputerMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.exception.ResourceNotFoundException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/computer/edit/{id}")
public class EditComputer {
	private static final String INPUT_COMPANY_ID = "companyId";
	private static final String ATT_ALL_COMPANIES = "allCompanies";
	private static final String ATT_ERRORS = "errors";
	private static final String ATT_SUCCESS = "success";
	private static final String ATT_COMPUTER = "computer";
	private static final String OTHER_ERROR = "otherError";

	public static final String VIEW = "editComputer";

	private CompanyService companyService;
	private ComputerService computerService;
	private EditComputerMapper editComputerMapper;
	private CompanyMapper companyMapper;

	public EditComputer(CompanyService companyService, ComputerService computerService, EditComputerMapper editComputerMapper, CompanyMapper companyMapper) {
		this.companyService = companyService;
		this.computerService = computerService;
		this.editComputerMapper = editComputerMapper;
		this.companyMapper = companyMapper;
	}


	@GetMapping
	protected ModelAndView displayFormEmpty(@PathVariable(value="id") String idString) {
		long id = 0;

		try {
			id = Long.parseLong(idString);
		} catch (NumberFormatException e) {
			throw new ResourceNotFoundException();
		}

		ModelAndView modelAndView = new ModelAndView(VIEW);
		Optional<Computer> opt = computerService.getById(id);
		if (opt.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		Computer computer = opt.get();
		EditComputerDTO dto = editComputerMapper.toEditComputerDTO(computer);

		addCompanyList(modelAndView);
		modelAndView.addObject(ATT_COMPUTER, dto);

		return modelAndView;
	}

	private void addCompanyList(ModelAndView modelAndView) {
		List<Company> companies = companyService.getAll();
		List<CompanyDTO> companiesDTO = companyMapper.toListCompanyDTO(companies);
		modelAndView.addObject(ATT_ALL_COMPANIES, companiesDTO);
	}

	@PostMapping
	protected ModelAndView saveComputer(@ModelAttribute(ATT_COMPUTER) EditComputerDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		Map<String, String> errors = new HashMap<String, String>();

		if (result.hasErrors()) {
			errors.put(OTHER_ERROR, "error.computer.errorType");
		} else {
			Optional<Computer> computer = editComputerMapper.toComputer(dto, errors);

			if (errors.isEmpty()) {
				try {
					computerService.create(computer.get());
					modelAndView.addObject(ATT_SUCCESS, "text.edited");
				} catch (ComputerCompanyIdException e) {
					errors.put(INPUT_COMPANY_ID, "error.computer.companyIdNotExist");
				}
			}
		}
		modelAndView.addObject(ATT_ERRORS, errors);
		modelAndView.addObject(ATT_COMPUTER, dto);
		addCompanyList(modelAndView);

		return modelAndView;
	}
}
