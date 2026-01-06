package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewVeterinarianRequest;
import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import com.app.veterinaria.repository.VeterinarianRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class VeterinarianService {

    final private VeterinarianRepository veterinarianRepository;
    final private UserService userService;
    final private AppointmentService appointmentService;

    // crear veterinario
    public Veterinarian createAndSaveVeterinarian(NewVeterinarianRequest request) {
        if (request.getRole() == null) {
            request.setRole("VETERINARIAN");
        } else if (!request.getRole().equalsIgnoreCase("VETERINARIAN")) {
            throw new IllegalArgumentException("Role must be VETERINARIAN");
        }

        var user = userService.createAndSaveUserFrom(request);
        var veterinarian = new Veterinarian(request, user);
        return veterinarianRepository.save(veterinarian);
    }

    // crear veterinario y retornar respuesta
    public ResponseEntity<?> createAndSaveVeterinarianWithResponse(NewVeterinarianRequest request) {
        var veterinarian = createAndSaveVeterinarian(request);
        var response = new HashMap<String, Object>();
        response.put("message", "Veterinario creado exitosamente");
        response.put("status", HttpStatus.CREATED.value() + " " + HttpStatus.CREATED.getReasonPhrase());
        response.put("path", "/api/users/veterinarian");
        response.put("name", veterinarian.getFirstName() + " " + veterinarian.getLastName());
        response.put("username", request.getUsername());
        response.put("role", request.getRole());
        response.put("email", request.getEmail());
        response.put("Licsence number", veterinarian.getLicenseNumber());
        return ResponseEntity.ok(response);
    }


    // listar veterinarios segun especialidad
    public List<VeterinarianDto> findAll(String speciality) {
        if (speciality.equalsIgnoreCase("all")) {
            return veterinarianRepository.findAll().stream()
                    .map(vet -> {
                        var email = vet.getUser().getEmail();
                        return new VeterinarianDto(vet, email);
                    })
                    .toList();
        }

        return veterinarianRepository.findAllBySpecialty(speciality).stream()
                .map(vet -> {
                    var email = vet.getUser().getEmail();
                    return new VeterinarianDto(vet, email);
                })
                .toList();
    }

    // obtener veterinario por id
    public VeterinarianDto findById(Long id) {
        var veterianarioan = veterinarianRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinarian not found"));

        return new VeterinarianDto(veterianarioan, veterianarioan.getUser().getEmail());
    }

    // obtener citas de un veterinario
    public List<AppointmentDto> findAllAppointmentsByVeterinarian(Long veterinarianId, LocalDate date, String status) {

        var veterinarian = veterinarianRepository.findById(veterinarianId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinarian not found"));

        var appointments = appointmentService.findAllByVeterianrian(veterinarian);

        if (date != null) {
            appointments = appointments.stream()
                    .filter(appointment -> appointment.getDate().isEqual(date))
                    .toList();
        }

        if (status != null) {
            appointments = appointments.stream()
                    .filter(appointment -> appointment.getStatus() == AppointmentStatus.valueOf(status.toUpperCase()))
                    .toList();
        }

        return appointments.stream()
                .map(AppointmentDto::new)
                .toList();
    }
}
