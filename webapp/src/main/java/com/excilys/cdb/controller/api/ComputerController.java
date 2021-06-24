package com.excilys.cdb.controller.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.advice.RedirectErrorPage;
import com.excilys.cdb.binding.api.ComputerREST;
import com.excilys.cdb.binding.api.mapper.ComputerRESTMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/api/computer")
public class ComputerController {
	private static Logger logger = LoggerFactory.getLogger(RedirectErrorPage.class);

	private ComputerService computerService;
	private ComputerRESTMapper mapper;

	public ComputerController(ComputerService computerService, ComputerRESTMapper mapper) {
		this.computerService = computerService;
		this.mapper = mapper;
	}

	@GetMapping(value = "/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ComputerREST> getById(@PathVariable long id) {
		try {
			Optional<Computer> opt = computerService.getById(id);

			if (opt.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().body(mapper.map(opt.get()));

		 } catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 return ResponseEntity.internalServerError().body(null);
		 }
	}
}
