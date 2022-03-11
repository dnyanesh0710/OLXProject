package com.zensar.training.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zensar.training.bean.LoginUser;
import com.zensar.training.db.LoginUserDAO;

@Service
public class LoginServiceImpl implements UserDetailsService {

	@Autowired
	LoginUserDAO loginUserDAO;
	
	public LoginUser addLoginUser(LoginUser loginUser) {
		return this.loginUserDAO.save(loginUser);
	}
	
	public LoginUser findLoginUserByName(String name) {
		return this.loginUserDAO.findByName(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser=findLoginUserByName(username);
		User user=new User(loginUser.getName(),
				loginUser.getPassword(),
				new ArrayList());
		return user;
	}
	
	
}
