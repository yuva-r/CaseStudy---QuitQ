package com.hexaware.QuitQ.dto;

public class RegisterDto {
	private String name;
	private String username;
	private String email;
	private String password;
    private String role;
	public RegisterDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RegisterDto(String name, String username, String email, String password, String role) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
}