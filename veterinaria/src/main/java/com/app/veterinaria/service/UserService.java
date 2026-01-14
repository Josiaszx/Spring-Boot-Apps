package com.app.veterinaria.service;

import com.app.veterinaria.dto.AnyEntityRequest;
import com.app.veterinaria.entity.Role;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return userRepository.save(user);
    }


    // buscar usuario por id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found for this id: " + id));
    }

    // mostrar todos los usuarios segun su rol
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found for this username: " + username));
    }


}
