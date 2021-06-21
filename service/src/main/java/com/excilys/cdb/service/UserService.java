package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service
public class UserService implements UserDetailsService {

	 private UserDAO userDAO;
	 
	 public UserService(UserDAO userDAO) {
		 this.userDAO = userDAO;
	 }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 Optional<User> user = userDAO.getByUsername(username);
	     if (!user.isPresent()) {
	    	 throw new UsernameNotFoundException(username);
	     }
	     return user.get();
	}

}
