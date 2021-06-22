package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.Role;
import com.excilys.cdb.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	public UserService() {
		super();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*System.out.println("UserService.loadUserByUsername " + userDAO);
		Optional<User> optUser = userDAO.getByUsername(username);
		System.out.println("UserService.loadUserByUsername 1");
		optUser.orElseThrow(() -> new UsernameNotFoundException(username));
	    System.out.println("UserService.loadUserByUsername 2");*/
		System.err.println(userDAO);
		User u = new User();
		u.setEnabled(true);
		u.setUsername("user1");
		u.setPassword("$2a$10$RS.AR8jVfZyuqlvlAmQqeujHSVCWWagQYgsMwoKKIhukysDptd9Fu");
		u.setRole(Role.USER);
		System.out.println(u);
	    return u;
	}



	/*public void setUserDAO(UserDAO userDAO) {
		System.out.println("Add userDAO");
		this.userDAO = userDAO;
		System.out.println("added on userDAO" + this.userDAO);
	}*/

}
