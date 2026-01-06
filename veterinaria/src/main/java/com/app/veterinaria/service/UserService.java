package com.app.veterinaria.service;

import com.app.veterinaria.dto.AnyEntityRequest;
import com.app.veterinaria.entity.Role;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.repository.RoleRepository;
import com.app.veterinaria.repository.UserRepository;
import com.app.veterinaria.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService{

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private VeterinarianRepository veterinarianRepository;


    // funcion para crear y guardar usuario
    public User createAndSaveUserFrom(AnyEntityRequest newUser) {
        Role role;
        String roleName = newUser.getRole();

        // rol por defecto: OWNER
        if (roleName == null) {
            role = roleRepository.findByRole("OWNER")
                    .orElseThrow(() -> new RuntimeException("Role not found: OWNER"));
        } else {
            role = roleRepository.findByRole(roleName.toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        }

        var user = new User(newUser, role);
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

    // obtener perfil del usuario autenticado (accesible con culquer rol: ADMIN, OWNER, VETERINARIAN)
//    public User getUser() {
//
//    }

}
