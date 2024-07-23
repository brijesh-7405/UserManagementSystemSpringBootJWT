package com.UserManagementSystem.UserManagementSystemSpringBoot.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.CustomUserDetails;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Bean.User;
import com.UserManagementSystem.UserManagementSystemSpringBoot.Dao.UserDao;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserDao userdao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<User> userlist = userdao.findByEmail(username);
		User user = null;
		if (userlist.size() > 0) {
			user = userlist.get(0);
		}
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
	}

}
