
package com.hexaware.QuitQ.serviceimpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQ.dto.UserDto;
import com.hexaware.QuitQ.entities.Role;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.RoleRepository;
import com.hexaware.QuitQ.repository.UserRepository;
import com.hexaware.QuitQ.service.UserService;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private UserDto mapToDTO(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        // Convert Set<Role> to String (get first role name)
        Set<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            dto.setRole(roles.iterator().next().getName());
        }

        return dto;
    }

    private User mapToEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        Role role = roleRepository.findByName(dto.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found: " + dto.getRole()));

            user.setRoles(Set.of(role));

            return user;
    }

    @Override
    public UserDto createUser(UserDto dto) {
        User user = mapToEntity(dto);
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto dto) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(dto.getName());
            existingUser.setUsername(dto.getUsername());
            existingUser.setEmail(dto.getEmail());

            Role role = new Role();
            role.setName(dto.getRole());
            existingUser.setRoles(Set.of(role));

            return mapToDTO(userRepository.save(existingUser));
        }).orElse(null);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}