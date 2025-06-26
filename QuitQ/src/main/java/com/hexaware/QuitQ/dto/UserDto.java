package com.hexaware.QuitQ.dto;

public class UserDto {
	private String name;
	private String username;
	private String email;
	private String role;
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(String name, String username, String email, String role) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserDtoNew [name=" + name + ", username=" + username + ", email=" + email + ", role=" + role + "]";
	}
	
 
}
