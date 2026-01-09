package com.app.veterinaria.controller;

import com.app.veterinaria.dto.MedicalRecordDto;
import com.app.veterinaria.dto.NewMedicalRecordRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.service.MedicalRecordService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    final private MedicalRecordService medicalRecordService;

    @PostMapping
    public MedicalRecordDto save(@RequestBody NewMedicalRecordRequest request) {
        return medicalRecordService.save(request);
    }

    @GetMapping
    public List<MedicalRecordDto> findAll() {
        return medicalRecordService.findAll();
    }

    @GetMapping("/{id}")
    public MedicalRecordDto fidnById(@PathVariable Long id) {
        return new MedicalRecordDto(medicalRecordService.findById(id));
    }

    @GetMapping("/pet/{petId}")
    public List<MedicalRecordDto> findAllByPet(@PathVariable Long petId) {
        return medicalRecordService.findAllByPet(petId);
    }

    @PutMapping("/{id}")
    public MedicalRecordDto updateRecord(@PathVariable Long id, @RequestBody NewMedicalRecordRequest request) {
        return medicalRecordService.updateRecord(id, request);
    }
}
