package com.excilys.cdb.bindingFront.mapper;

import java.util.Map;
import java.util.Optional;

import com.excilys.cdb.bindingFront.LoginDTO;
import com.excilys.cdb.bindingFront.validator.LoginValidator;
import com.excilys.cdb.model.User;

public class LoginDTOMapper {
	
	private LoginValidator validator;

	public Optional<User> toUser(LoginDTO dto, Map<String, String> errors) {
		Optional<User> opt = Optional.empty();
		
		if (validator.validate(dto, errors).isEmpty()) {
			User user = new User();
			user.setUsername(dto.getUsername());
			user.setPassword(dto.getPassword());
			opt = Optional.of(user);
		}
		return opt;
	}

}
