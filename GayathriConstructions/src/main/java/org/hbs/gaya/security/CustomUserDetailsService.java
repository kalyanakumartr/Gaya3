package org.hbs.gaya.security;

import java.util.List;

import org.hbs.gaya.dao.UsersDao;
import org.hbs.gaya.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersDao usersDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<Users> userList= usersDao.getLoginDetails(username);
		if (userList == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(userList.get(0));
	}

}
