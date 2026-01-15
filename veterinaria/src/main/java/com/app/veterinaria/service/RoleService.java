package com.app.veterinaria.service;

import com.app.veterinaria.entity.Role;
import com.app.veterinaria.exception.DuplicateResourceException;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    final private RoleRepository roleRepository;

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("Role with name " + roleName + "not found");
                    error.setMethod(HttpMethod.GET);
                    error.setPath("api/roles/" + roleName);
                    return error;
                });
    }

    // agregar rol, solo admins
    public Role save(Role role) {
        if (roleRepository.existsByRole(role.getRole())) {
            var error = new DuplicateResourceException("Role already exists");
            error.setMethod(HttpMethod.POST);
            error.setPath("api/roles");
            throw error;
        }
        return roleRepository.save(role);
    }
}
