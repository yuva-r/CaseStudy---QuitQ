package com.hexaware.QuitQ.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.QuitQ.entities.Role;
import com.hexaware.QuitQ.entities.User;
import com.hexaware.QuitQ.repository.RoleRepository;
import com.hexaware.QuitQ.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:5173")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        Set<Role> attachedRoles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role attached = roleRepository.findById(role.getId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));
            attachedRoles.add(attached);
        }
        user.setRoles(attachedRoles);
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(user.getName());
            existing.setUsername(user.getUsername());
            existing.setEmail(user.getEmail());
            existing.setPassword(user.getPassword());

            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : user.getRoles()) {
                Role attached = roleRepository.findById(role.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + role.getId()));
                updatedRoles.add(attached);
            }
            existing.setRoles(updatedRoles);
            return userRepository.save(existing);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}