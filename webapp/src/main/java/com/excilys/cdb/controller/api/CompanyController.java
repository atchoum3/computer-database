package com.excilys.cdb.controller.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.api.CompanyREST;
import com.excilys.cdb.binding.api.mapper.CompanyRESTMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {
	private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

	private CompanyService companyService;
	private CompanyRESTMapper mapper;

	public CompanyController(CompanyService companyService, CompanyRESTMapper mapper) {
		this.companyService = companyService;
		this.mapper = mapper;
	}

	@GetMapping
	public ResponseEntity<List<CompanyREST>> getAllCompany() {
		try {

			List<Company> companies = companyService.getAll();
			if (companies.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(mapper.map(companies));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> count() {
		try {
			return ResponseEntity.ok(companyService.count());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(null);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable long id) {
		try {
			return ResponseEntity.ok(companyService.delete(id));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseEntity.internalServerError().body(null);
		}
	}
}
