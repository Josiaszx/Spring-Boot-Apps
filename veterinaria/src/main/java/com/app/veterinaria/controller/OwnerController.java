package com.app.veterinaria.controller;

import com.app.veterinaria.dto.NewOwnerRequest;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    final private OwnerService ownerService;

    @GetMapping
    public List<Owner> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "asc") String sort
    ){
        return ownerService.findAll(page, size, sort).getContent();
    }

    @GetMapping("/{id}")
    public Owner findById(@PathVariable Long id){
        return ownerService.findById(id);
    }

    @PutMapping("/{id}")
    public Owner update(@PathVariable Long id, @RequestBody Owner owner) {
        return ownerService.update(id, owner);
    }

    @PostMapping
    public Owner save(@RequestBody NewOwnerRequest owner) {
        return ownerService.save(owner);
    }
}
