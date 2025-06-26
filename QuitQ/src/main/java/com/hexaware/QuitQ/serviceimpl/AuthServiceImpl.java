package com.hexaware.QuitQ.serviceimpl;

import java.util.HashSet;
import java.util.Set;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQ.dto.JWTAuthResponse;
import com.hexaware.QuitQ.dto.LoginDto;
import com.hexaware.QuitQ.dto.RegisterDto;
import com.hexaware.QuitQ.dto.UserDto;
import com.hexaware.QuitQ.entities.Role;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.exceptions.BadRequestException;
import com.hexaware.QuitQ.repository.RoleRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.security.JwtTokenProvider;
import com.hexaware.QuitQ.service.AuthService;

import lombok.extern.slf4j.Slf4j;
 
 
 
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	private AuthenticationManager authenticationManager;
	private UserRepository userRepo;
	private RoleRepository roleRepo;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	public AuthServiceImpl(AuthenticationManager authenticationManager,
			UserRepository userRepo, RoleRepository roleRepo,PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/*
	 * @Override public JWTAuthResponse login(LoginDto dto) {
	 * System.out.println(("object received"+dto)); Authentication authentication =
	 * authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));
	 * SecurityContextHolder.getContext().setAuthentication(authentication); String
	 * token = jwtTokenProvider.generateToken(authentication);
	 * System.out.println("Token generated : "+token); User user =
	 * userRepo.findByUsername(dto.getName()).get();
	 * System.out.println("user object found in repo "+user); UserDto userDto = new
	 * UserDto(); userDto.setName(user.getName());
	 * userDto.setEmail(user.getEmail()); userDto.setUsername(user.getUsername());
	 * String role = "ROLE_USER"; Set<Role> roleUser = user.getRoles(); for(Role
	 * roleTemp:roleUser) { if(roleTemp.getName().equalsIgnoreCase("ROLE_ADMIN"))
	 * role = "ROLE_ADMIN"; } userDto.setRole(role); return new
	 * JWTAuthResponse(token,userDto); }
	 */
	@Override
	public JWTAuthResponse login(LoginDto dto) {
	    System.out.println(("object received"+dto));
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(dto.getName(), dto.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String token = jwtTokenProvider.generateToken(authentication);
	    System.out.println("Token generated : " + token);

	    User user = userRepo.findByUsername(dto.getName()).get();
	    System.out.println("user object found in repo " + user);

	    UserDto userDto = new UserDto();
	    userDto.setName(user.getName());
	    userDto.setEmail(user.getEmail());
	    userDto.setUsername(user.getUsername());

	    String role = "ROLE_USER";
	    Set<Role> roleUser = user.getRoles();
	    for (Role roleTemp : roleUser) {
	        if (roleTemp.getName().equalsIgnoreCase("ROLE_ADMIN")) {
	            role = "ROLE_ADMIN";
	            break;  // admin has highest priority
	        } else if (roleTemp.getName().equalsIgnoreCase("ROLE_SELLER")) {
	            role = "ROLE_SELLER";
	        }
	    }
	    userDto.setRole(role);
	    return new JWTAuthResponse(token, userDto);
	}
	/*
	 * @Override public String register(RegisterDto dto) {
	 * if(userRepo.existsByUsername(dto.getUsername())) throw new
	 * BadRequestException(HttpStatus.BAD_REQUEST, "Username already exist");
	 * if(userRepo.existsByEmail(dto.getEmail())) throw new
	 * BadRequestException(HttpStatus.BAD_REQUEST, "Email already exist"); User user
	 * = new User(); user.setName(dto.getName()); user.setEmail(dto.getEmail());
	 * user.setUsername(dto.getUsername());
	 * user.setPassword(passwordEncoder.encode(dto.getPassword())); Set<Role> roles
	 * = new HashSet<>(); Role role = roleRepo.findByName("ROLE_USER").get();
	 * roles.add(role); user.setRoles(roles); userRepo.save(user); return
	 * "Register Successfull!.."; }
	 */
	@Override
	public String register(RegisterDto dto) {
	    if (userRepo.existsByUsername(dto.getUsername()))
	        throw new BadRequestException(HttpStatus.BAD_REQUEST, "Username already exist");

	    if (userRepo.existsByEmail(dto.getEmail()))
	        throw new BadRequestException(HttpStatus.BAD_REQUEST, "Email already exist");

	    User user = new User();
	    user.setName(dto.getName());
	    user.setEmail(dto.getEmail());
	    user.setUsername(dto.getUsername());
	    user.setPassword(passwordEncoder.encode(dto.getPassword()));

	    Set<Role> roles = new HashSet<>();
	    Role role;

	    // Validate and assign role based on input
	    if ("admin".equalsIgnoreCase(dto.getRole())) {
	        role = roleRepo.findByName("ROLE_ADMIN")
	                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
	    } else if ("seller".equalsIgnoreCase(dto.getRole())) {
	        role = roleRepo.findByName("ROLE_SELLER")
	                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
	    } else {
	        role = roleRepo.findByName("ROLE_USER")
	                .orElseThrow(() -> new BadRequestException(HttpStatus.BAD_REQUEST, "Role not found"));
	    }

	    roles.add(role);
	    user.setRoles(roles);
	    userRepo.save(user);
	    return "Registration successful!..";
	}
}
