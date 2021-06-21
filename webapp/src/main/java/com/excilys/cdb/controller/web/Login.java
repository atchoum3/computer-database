package com.excilys.cdb.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.bindingFront.AddComputerDTO;
import com.excilys.cdb.bindingFront.CompanyDTO;
import com.excilys.cdb.bindingFront.LoginDTO;
import com.excilys.cdb.bindingFront.mapper.LoginDTOMapper;
import com.excilys.cdb.exception.ComputerCompanyIdException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.UserService;

//@Controller
//@RequestMapping("/computer")
public class Login {
	
	private static final String FORM_LOGIN = "login";
	private static final String OTHER_ERROR = "otherError";

	private static final String VIEW = "login";
	
	private LoginDTOMapper loginDTOMapper;
	private UserService userService;
	
	public Login(LoginDTOMapper loginDTOMapper) {
		this.loginDTOMapper =loginDTOMapper;
	}
	
	@GetMapping
	protected ModelAndView displayFormEmpty() {
		return new ModelAndView(VIEW);
	}

	@PostMapping
	protected ModelAndView saveComputer(@ModelAttribute(FORM_LOGIN) LoginDTO dto, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView(VIEW);
		Map<String, String> errors = new HashMap<String, String>();

		if (result.hasErrors()) {
			errors.put(OTHER_ERROR, "error.errorType");
		} else {
			Optional<User> user = loginDTOMapper.toUser(dto, errors);

			if (errors.isEmpty()) {
				try {
					userService.loadUserByUsername(user);
					modelAndView.addObject(ATT_SUCCESS, "text.added");
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
