package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewPetRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PetService {

    final private PetRepository petRepository;
    final private OwnerService ownerService;

    // registrar mascota (roles: VETERINARIAN, ADMIN y OWNER)
    public PetDto save(NewPetRequest request) {
        // si el usuario tiene rol OWNER, asignar automaticamente la mascota al owner logueado
        // ...


        // si no, requerir el id en body de la request
        var ownerId = request.getOwnerId();
        if (ownerId == null) {
            throw new IllegalArgumentException("El ID del dueño es obligatorio");
        }

        var owner = ownerService.findById(ownerId);
        var pet = new Pet(request, owner);
        pet = petRepository.save(pet);
        return new PetDto(pet);
    }

    // listar mascotas (roles: VETERINARIAN, ADMIN y OWNER(solo podra ver sus mascotas))
    public List<PetDto> findAll(Long ownerId) {
        // si el usuario tiene rol OWNER, obtener solo sus mascotas
        // ...

        // si el usuario tiene rol ADMIN o VETERINARIAN
        List<Pet> pets;
        if (ownerId != -1) {
            var owner = ownerService.findById(ownerId);
            pets = petRepository.findByOwner(owner);
        } else {
            pets = petRepository.findAll();
        }
        return pets.stream()
                .map(PetDto::new)
                .toList();
    }

    // ver mascota por id
    public PetDto findById(Long petId) {
        var pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
        return new PetDto(pet);
    }

    // retornar entidad mascota por id
    public Pet findEntityById(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));
    }

    // actualizar mascota
    public PetDto update(Long petId, Pet newPet) {
        var pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("Pet not found"));

        pet.updatePet(newPet);
        return new PetDto(petRepository.save(pet));
    }

    // eliminar mascota
    public void delete(Long petId) {
        petRepository.deleteById(petId);
    }
}
