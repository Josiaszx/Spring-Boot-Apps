package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewPetRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.service.PetService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pets")
public class PetController {

    final private PetService petService;

    @PostMapping
    public PetDto save(@Valid @RequestBody NewPetRequest request) {
        return petService.save(request);
    }

    @GetMapping
    public List<PetDto> findAll(@RequestParam(defaultValue = "-1") Long ownerId) {
        return petService.findAll(ownerId);
    }

    @GetMapping("/{id}")
    public PetDto findById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @PutMapping("/{id}")
    public PetDto update(@PathVariable Long id, @RequestBody Pet pet) {
        return petService.update(id, pet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        petService.delete(id);
    }
}
