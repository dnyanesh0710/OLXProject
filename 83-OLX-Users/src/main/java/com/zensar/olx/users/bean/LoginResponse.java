package com.zensar.olx.users.bean;

public class LoginResponse {
	
	private String jwt;

	public LoginResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public LoginResponse() {
		super();
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
