package com.excilys.cdb.controller.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.api.ComputerREST;
import com.excilys.cdb.binding.api.mapper.ComputerRESTMapper;
import com.excilys.cdb.binding.web.PageDTO;
import com.excilys.cdb.binding.web.mapper.PageMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Order;
import com.excilys.cdb.model.OrderBy;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/api/computer")
public class ComputerController {
	private static Logger logger = LoggerFactory.getLogger(ComputerController.class);

	private ComputerService computerService;
	private ComputerRESTMapper mapper;
	private PageMapper pageMapper;

	public ComputerController(ComputerService computerService, ComputerRESTMapper mapper, PageMapper pageMapper) {
		this.computerService = computerService;
		this.mapper = mapper;
		this.pageMapper = pageMapper;
	}

	@GetMapping
	public  ResponseEntity<List<ComputerREST>> search(
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "nbItemByPage", defaultValue = "10") int nbItemByPage,
			@RequestParam(value = "orderColumn", defaultValue = "computer.name") OrderBy column,
			@RequestParam(value = "order", defaultValue = "ASC") Order order
	) {
		try {
			PageDTO pageDTO = mapToPageDTO(currentPage, nbItemByPage, column, order);
			Page page = pageMapper.toPage(pageDTO);

			List<Computer> computers = computerService.searchByName(search, page);
			if (computers.isEmpty()) {
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.ok(mapper.map(computers));

		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 return ResponseEntity.internalServerError().body(null);
		 }
	}

	@GetMapping("/count")
	public ResponseEntity<Integer> countSearch(
			@RequestParam(value = "search", defaultValue = "") String search
	) {
		try {
			int count = computerService.countSearchByName(search);
			return ResponseEntity.ok(count);

		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 return ResponseEntity.internalServerError().body(null);
		 }
	}

	@GetMapping("/{id}")
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

	@PostMapping("/{id}")
	public ResponseEntity<?> updateById(@RequestBody ComputerREST dto) {
		try {
			computerService.update(mapper.map(dto));
			return ResponseEntity.ok().body(null);
		} catch (ComputerCompanyIdException e) {
			return ResponseEntity.badRequest().build();

		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 return ResponseEntity.internalServerError().body(null);
		 }
	}

	@DeleteMapping
	public ResponseEntity<?> deleteById(@RequestParam long id) {
		try {
			computerService.delete(id);
			return ResponseEntity.ok(null);

		} catch (Exception e) {
			 logger.error(e.getMessage(), e);
			 return ResponseEntity.internalServerError().body(null);
		 }
	}

	private PageDTO mapToPageDTO(int currentPage, int nbItemByPage, OrderBy column, Order order) {
		return new PageDTO.Builder()
				.withCurrentPage(currentPage)
				.withNbElementByPage(nbItemByPage)
				.withColumn(column)
				.withOrder(order)
				.build();
	}
}
