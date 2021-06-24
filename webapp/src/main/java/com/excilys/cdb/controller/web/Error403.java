package com.excilys.cdb.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Error403 {

	@GetMapping("/403")
	private String displayError() {
		return "403";
	}
}
