package com.excilys.cdb.bindingFront.validator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingFront.LoginDTO;

@Component
public class LoginValidator {
	private static String ERROR_LOGIN_USERNAME = "username";
	
	
	public Map<String, String> validate(LoginDTO loginDTO, Map<String, String> errors) {
		validateUsername(loginDTO.getUsername(), errors);
		validatePassword(loginDTO.getPassword(), errors);
		return errors;
	}
	
	private void validateUsername(String username, Map<String, String> errors) {
		if (username.isEmpty()) {
			errors.put(ERROR_LOGIN_USERNAME, "error.login.usernameEmpty");
		} else if (username.length() > 50) {
			errors.put(ERROR_LOGIN_USERNAME, "error.login.usernameGreaterThan50");
		}
	}
	
	private void validatePassword(String password, Map<String, String> errors) {
		if (password.isEmpty()) {
			errors.put(ERROR_LOGIN_USERNAME, "error.login.passwordEmpty");
		}
	}
}
