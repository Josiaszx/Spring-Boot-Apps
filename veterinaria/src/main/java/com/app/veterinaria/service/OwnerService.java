package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewOwnerRequest;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OwnerService {

    final private OwnerRepository ownerRepository;
    final private UserService userService;

    // listar todos los dueños (requiere rol: ADMIN o VETERINARIAN)
    public Page<Owner> findAll(Integer page, Integer size, String sort) {
        Direction direction;

        if (sort.equalsIgnoreCase("DESC")) direction = Direction.DESC;
        else direction = Direction.ASC;
        var pageRequest = PageRequest.of(page, size, Sort.by(direction, "name"));
        return ownerRepository.findAll(pageRequest);
    }

    // obtener dueño por id (requiere rol: ADMIN, VETERINARIAN u OWNER(si es su propio perfil))
    public Owner findById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
    }

    // actualizar perfil (requiere rol: ADMIN u OWNER(si es su propio perfil))
    public Owner update(Long id, Owner owner) {
        var ownerEntity = ownerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        ownerEntity.updateOwner(owner);

        return ownerRepository.save(ownerEntity);
    }

    // crear dueño (requiere rol: ADMIN o VETERINARIAN)
    public Owner save(NewOwnerRequest ownerRequest) {
        var user = userService.createAndSaveUser(ownerRequest);
        var owner = new Owner(ownerRequest, user);
        return ownerRepository.save(owner);
    }
}
