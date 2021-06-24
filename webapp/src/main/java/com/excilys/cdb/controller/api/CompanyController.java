package com.excilys.cdb.controller.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.web.CompanyDTO;
import com.excilys.cdb.binding.web.mapper.CompanyMapper;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {

	private CompanyService companyService;
	private CompanyMapper mapper;

	public CompanyController(CompanyService companyService, CompanyMapper mapper) {
		this.companyService = companyService;
		this.mapper = mapper;
	}

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<CompanyDTO> get() {
		return mapper.toListCompanyDTO(companyService.getAll());
	}
}
