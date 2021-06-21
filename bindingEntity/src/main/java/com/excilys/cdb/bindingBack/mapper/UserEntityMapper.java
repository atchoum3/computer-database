package com.excilys.cdb.bindingBack.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.bindingBack.UserEntity;
import com.excilys.cdb.model.User;

@Component
public class UserEntityMapper {
	
	public User toUser(UserEntity entity) {
		User u = new User();
		u.setId(entity.getId());
		u.setUsername(entity.getUsername());
		u.setPassword(entity.getPassword());
		return u;
	}
	
	public UserEntity toEntity(User user) {
		UserEntity entity = new UserEntity();
		entity.setId(user.getId());
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());
		return entity;
	}
}
