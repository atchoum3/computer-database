package com.excilys.cdb.binding.web.validator;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.PageDTO;

@Component
public class PageValidator {

	public boolean isValidNbElementTotal(PageDTO dto) {
		return dto.getNbElementTotal() >= 0;
	}

	public boolean isValidNbElementByPage(PageDTO dto) {
		int n = dto.getNbElementByPage();
		return  n == 10 || n == 50 || n == 100;
	}

	public boolean isValidCurrentPage(PageDTO dto) {
		return dto.getCurrentPage() > 0;
	}





}
