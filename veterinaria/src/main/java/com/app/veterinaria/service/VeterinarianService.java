package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewVeterinarianRequest;
import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import com.app.veterinaria.exception.DuplicateResourceException;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VeterinarianService {

    final private VeterinarianRepository veterinarianRepository;
    final private UserService userService;
    final private AppointmentService appointmentService;

    // crear veterinario
    public Veterinarian createVeterinarian(NewVeterinarianRequest request) {
        if (request.getRole() == null) {
            request.setRole("VETERINARIAN");
        } else if (!request.getRole().equalsIgnoreCase("VETERINARIAN")) {
            throw new IllegalArgumentException("Role must be VETERINARIAN");
        }

        validateNewVeterinarian(request.getLicenseNumber());
        var user = userService.createAndSaveUser(request);
        return new Veterinarian(request, user);
    }

    // crear veterinario y guardarlo
    public Veterinarian saveFromRequest(NewVeterinarianRequest request) {
        var veterinarian = createVeterinarian(request);
        return veterinarianRepository.save(veterinarian);
    }

    // crear veterinario y retornar respuesta
    public ResponseEntity<?> createAndSaveVeterinarianWithResponse(NewVeterinarianRequest request) {
        var veterinarian = saveFromRequest(request);
        var response = new LinkedHashMap<String, Object>();
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
        var veterinarian = findEntityById(id);
        return new VeterinarianDto(veterinarian, veterinarian.getUser().getEmail());
    }

    // obtener entidad veterinario por id
    public Veterinarian findEntityById(Long id) {
        return veterinarianRepository.findById(id)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("Veterinarian not found with id: " + id);
                    error.setPath("api/veterinarians/" + id);
                    return error;
                });
    }

    // obtener citas de un veterinario
    public List<AppointmentDto> findAllAppointmentsByVeterinarian(Long veterinarianId, LocalDate date, String status) {

        var veterinarian = findEntityById(veterinarianId);
        var appointments = appointmentService.findAllByVeterinarian(veterinarian);

        if (date != null) appointments = filterByDate(date, appointments);

        if (status != null) appointments = filterByStatus(status, appointments);

        return appointments.stream()
                .map(AppointmentDto::new)
                .toList();
    }

    public List<Appointment> filterByDate(LocalDate date, List<Appointment> appointments) {
        return appointments.stream()
                .filter(appointment -> appointment.getDate().isEqual(date))
                .toList();
    }

    public List<Appointment> filterByStatus(String status, List<Appointment> appointments) {
        var finalStatus = status.toUpperCase();
        var validStatuses = List.of("CANCELLED", "SCHEDULED", "CLOSED");
        if (!validStatuses.contains(finalStatus)) {
            throw new IllegalArgumentException("Status must be CANCELLED, SCHEDULED or CLOSED");
        }

        return appointments.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.valueOf(finalStatus))
                .toList();
    }

    // verificar que no exista un veterinario con el mismo numero de licencia
    public void validateNewVeterinarian(String licenseNumber) {
        if (veterinarianRepository.existsByLicenseNumber(licenseNumber)) {
            var error = new DuplicateResourceException("Already exists veterinarian with license number: " + licenseNumber);
            error.setPath("api/veterinarians");
            throw error;
        }
    }
}
