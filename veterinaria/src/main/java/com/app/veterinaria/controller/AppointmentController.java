package com.app.veterinaria.controller;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    final private AppointmentService appointmentService;


    @PostMapping
    public AppointmentDto post(@Valid @RequestBody NewAppointmentRequest request) {
        return appointmentService.save(request);
    }

    @GetMapping
    public List<AppointmentDto> findAllByParams(
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) Long veterinarianId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return appointmentService.findAllByParams(petId, veterinarianId, status, date, page, size);
    }
}
