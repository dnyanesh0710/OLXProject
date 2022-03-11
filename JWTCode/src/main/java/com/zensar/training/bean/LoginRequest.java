package com.zensar.training.bean;

public class LoginRequest {
	String userName;
	String password;
	
	public LoginRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	public LoginRequest() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginRequest [userName=" + userName + ", password=" + password + "]";
	}
	
	
}
