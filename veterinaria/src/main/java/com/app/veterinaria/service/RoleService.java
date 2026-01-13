package com.app.veterinaria.service;

import com.app.veterinaria.entity.Role;
import com.app.veterinaria.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    final private RoleRepository roleRepository;

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }
}
