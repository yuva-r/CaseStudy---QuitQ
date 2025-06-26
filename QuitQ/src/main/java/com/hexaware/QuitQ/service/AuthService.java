package com.hexaware.QuitQ.service;

import com.hexaware.QuitQ.dto.JWTAuthResponse;
import com.hexaware.QuitQ.dto.LoginDto;
import com.hexaware.QuitQ.dto.RegisterDto;

public interface AuthService {
	JWTAuthResponse login(LoginDto dto);
	String register(RegisterDto dto);
}
