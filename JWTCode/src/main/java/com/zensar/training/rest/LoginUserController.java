package com.zensar.training.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.training.bean.LoginRequest;
import com.zensar.training.bean.LoginResponse;
import com.zensar.training.bean.LoginUser;
import com.zensar.training.security.util.JwtUtil;
import com.zensar.training.service.LoginServiceImpl;

@RestController
public class LoginUserController {

	@Autowired
	LoginServiceImpl loginServiceImpl;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/addUser")
	public LoginUser add(@RequestBody LoginUser loginUser) {
		return this.loginServiceImpl.addLoginUser(loginUser);
	}
	
	@GetMapping("/findUser/{name}")
	public LoginUser findLoginUserByName(@PathVariable(name="name") String name) {
		return this.loginServiceImpl.findLoginUserByName(name);
	}
	
	@PostMapping("/authenticate")
	public LoginResponse createToken(@RequestBody LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken authUserPass=
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
		this.authenticationManager.authenticate(authUserPass);
		
		UserDetails userDetails;
		userDetails=loginServiceImpl.loadUserByUsername(loginRequest.getUserName());
		String jwtstr=jwtUtil.generateToken(loginRequest.getUserName());
		
		return new LoginResponse(jwtstr);
	}
}
