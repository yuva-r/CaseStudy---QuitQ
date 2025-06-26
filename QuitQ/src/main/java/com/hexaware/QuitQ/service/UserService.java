
  package com.hexaware.QuitQ.service;
  
  import java.util.List;
  
  import com.hexaware.QuitQ.dto.UserDto;
  
  
  
  public interface UserService { UserDto createUser(UserDto userDTO); UserDto
  getUserById(Long id); List<UserDto> getAllUsers(); UserDto updateUser(Long
  id, UserDto userDTO); void deleteUser(Long id); }
 