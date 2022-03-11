package com.zensar.training.bean;

public class LoginResponse {
	String jwt;

	public LoginResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "LoginResponse [jwt=" + jwt + "]";
	}
	
	
}
