package com.app.veterinaria.service;

import com.app.veterinaria.dto.AnyEntityRequest;
import com.app.veterinaria.entity.Role;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.exception.DuplicateResourceException;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService{

    final private UserRepository userRepository;
    final private RoleService roleService;

    // crear y guardar usuario
    public User createAndSaveUser(AnyEntityRequest newUser) {
        var createdUser = createUserFromRequest(newUser);
        return save(createdUser);
    }

    // crear usuario apartir de objeto que implemente AnyEntityRequest
    public User createUserFromRequest(AnyEntityRequest newUser) {
        Role role;
        String roleName = newUser.getRole();

        // rol por defecto: OWNER
        if (roleName == null) {
            role = roleService.findByRoleName("OWNER");
        } else {
            role = roleService.findByRoleName(roleName.toUpperCase());
        }

        return new User(newUser, role);
    }

    // guardar usuario
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) throw new DuplicateResourceException("The email is already registered", "");
        if (userRepository.existsByUsername(user.getUsername())) throw new DuplicateResourceException("The username is already registered", "");
        return userRepository.save(user);
    }


    // buscar usuario por id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("User not found with id: " + id);
                    error.setPath("api/users/" + id);
                    return error;
                });
    }

    // mostrar todos los usuarios segun su rol
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("User not found with username: " + username);
                    error.setPath("api/users/me");
                    return error;
                });
    }


}
