package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewPetRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.exception.InvalidOperationException;
import com.app.veterinaria.service.AuthenticationService;
import com.app.veterinaria.service.OwnerService;
import com.app.veterinaria.service.PetService;
import com.app.veterinaria.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    final private PetService petService;
    final private OwnerService ownerService;
    final private UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public PetDto save(@Valid @RequestBody NewPetRequest request) {
        boolean isAnOwner = authenticationService.getUserRole().equals("OWNER");

        if (!isAnOwner && request.getOwnerId() == null) {
            var error = new InvalidOperationException("OwnerId can not be null");
            error.setPath("api/pets");
            error.setHttpMethod(HttpMethod.POST);
            throw error;
        }

        if (isAnOwner) {
            var ownerUsername = authenticationService.getUsername();
            var user = userService.findByUsername(ownerUsername);
            var ownerId = ownerService.findByUser(user).getId();
            request.setOwnerId(ownerId);
        }

        return petService.save(request);
    }

    @GetMapping
    public List<PetDto> findAll(@RequestParam(defaultValue = "-1") Long ownerId) {

        var userRole = authenticationService.getUserRole();
        if (userRole.equals("OWNER")) {
            var username = authenticationService.getUsername();
            var owner = ownerService.findOwnerByUsername(username);
            ownerId = owner.getId();
        }


        return petService.findAll(ownerId);
    }

    @GetMapping("/{id}")
    public PetDto findById(@PathVariable Long id) {
        var userRole = authenticationService.getUserRole();
        if (userRole.equals("OWNER"))
            checkIfThePetBelongsToTheLoggedInOwner(id, HttpMethod.GET);

        return petService.getDtoWithId(id);
    }

    @PutMapping("/{id}")
    public PetDto update(@PathVariable Long id, @RequestBody Pet pet) {
        var userRole = authenticationService.getUserRole();
        if (userRole.equals("OWNER"))
            checkIfThePetBelongsToTheLoggedInOwner(id, HttpMethod.PUT);

        return petService.update(id, pet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        var userRole = authenticationService.getUserRole();
        if (userRole.equals("OWNER"))
            checkIfThePetBelongsToTheLoggedInOwner(id, HttpMethod.DELETE);

        petService.delete(id);
    }

    public void checkIfThePetBelongsToTheLoggedInOwner(Long petId, HttpMethod httpMethod) {
        var petOwnerUsername = petService.getOwner(petId).getUser().getUsername();
        var loggedInOwnerUsername = authenticationService.getUsername();
        if (!petOwnerUsername.equals(loggedInOwnerUsername)) {
            var error = new InvalidOperationException("You are not the owner of this pet");
            error.setPath("api/pets/" + petId);
            error.setHttpMethod(httpMethod);
            throw error;
        }
    }
}
