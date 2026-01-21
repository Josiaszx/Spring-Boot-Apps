package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewOwnerRequest;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.exception.DuplicateResourceException;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpMethod;
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
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("Owner not found with id: " + id);
                    error.setMethod(HttpMethod.GET);
                    error.setPath("api/owners/" + id);
                    return error;
                });
    }

    // actualizar perfil (requiere rol: ADMIN u OWNER(si es su propio perfil))
    public Owner update(Long id, Owner owner) {
        var ownerEntity = findById(id);
        ownerEntity.updateOwner(owner);
        return ownerRepository.save(ownerEntity);
    }

    // crear dueño (requiere rol: ADMIN o VETERINARIAN)
    public Owner save(NewOwnerRequest ownerRequest) {
        if (ownerRepository.existsByPhoneNumber(ownerRequest.getPhoneNumber()))
            throw  new DuplicateResourceException("The phone number is already registered", "api/owners");

        var user = userService.createAndSaveUser(ownerRequest);
        var owner = new Owner(ownerRequest, user);
        return ownerRepository.save(owner);
    }

    public Owner findByUser(User user) {
        if (user == null) throw new IllegalArgumentException("User can not be null");
        return ownerRepository.findByUser(user)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("Owner not found with user: " + user.getUsername());
                    error.setMethod(HttpMethod.GET);
                    error.setPath("api/owners");
                    return error;
                });
    }

    public Owner findOwnerByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username can not be null");
        User user = userService.findByUsername(username);
        return findByUser(user);
    }
}
