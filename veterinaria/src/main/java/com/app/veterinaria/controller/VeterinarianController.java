package com.app.veterinaria.controller;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.service.VeterinarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/veterinarians")
public class VeterinarianController {

    final private VeterinarianService veterinarianService;

    @GetMapping
    public List<VeterinarianDto> findAll(@RequestParam(defaultValue = "all") String speciality) {
        return veterinarianService.findAll(speciality);
    }

    @GetMapping("/{id}")
    public VeterinarianDto findById(@PathVariable Long id) {
        return veterinarianService.findById(id);
    }

    @GetMapping("/{id}/appointments")
    public List<AppointmentDto> findAllAppointmentsByVeterinarian(
            @PathVariable Long id,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate date
    ) {
        return veterinarianService.findAllAppointmentsByVeterinarian(id, date, status);
    }
}
