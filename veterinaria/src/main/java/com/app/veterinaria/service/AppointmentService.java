package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import com.app.veterinaria.repository.AppointmentRepository;
import com.app.veterinaria.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    final private AppointmentRepository appointmentRepository;
    // usamos el repositorio de veterinarios para evitar ciclo de inyeccion
    final private VeterinarianRepository veterinarianRepository;
    final private PetService petService;


    // listar citas segun id de veterinario
    public List<Appointment> findAllByVeterianrian(Veterinarian veterinarian) {
        return appointmentRepository.findAllByVeterinarian(veterinarian);
    }

    // lista citas segun fecha
    public List<Appointment> findAllByDate(LocalDate date) {
        return appointmentRepository.findAllByDate(date);
    }

    // listar citas segun estado
    public List<Appointment> findAllByStatus(String status) {
        return appointmentRepository.findAllByStatus(
                AppointmentStatus.valueOf(status.toUpperCase())
        );
    }

    // agendar cita
    public AppointmentDto save(NewAppointmentRequest request) {
        var pet = petService.findEntityById(request.getPetId());
        var veterinarian = veterinarianRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new IllegalArgumentException("Veterinarian not found"));
        var date = request.getDate();
        var appointment = new Appointment(date, pet, veterinarian);
        appointment = appointmentRepository.save(appointment);
        return new AppointmentDto(appointment);
    }

    // listar citas segun parametros
    public List<AppointmentDto> findAllByParams(
            Long petId, Long veterinarianId, String status, LocalDate date,
            Integer page, Integer size
    ) {

        var appointments = new ArrayList<Appointment>();
        var notNullParams = new ArrayList<String>();
        String[] aff = new String[10];


        if (petId != null) {
            var pet = petService.findEntityById(petId);
            appointments.addAll(appointmentRepository.findAllByPet(pet));
            notNullParams.add("petId");
        }

        if (veterinarianId != null) {
            var veterinarian = veterinarianRepository.findById(veterinarianId)
                    .orElseThrow(() -> new IllegalArgumentException("Veterinarian not found"));
            appointments.addAll(appointmentRepository.findAllByVeterinarian(veterinarian));
            notNullParams.add("veterinarianId");
        }

        if (status != null) {
            appointments.addAll(findAllByStatus(status));
            notNullParams.add("status");
        }

        if (date != null) {
            appointments.addAll(findAllByDate(date));
            notNullParams.add("date");
        }

        // si notNullParams.isEmpty() == true, significa que no se dieron parametros, por lo tanto se asume que se quiere listar todas las citas
        //
        if (appointments.isEmpty() && notNullParams.isEmpty()) {
            Pageable pageable = PageRequest.of(page, size);
            return appointmentRepository.findAll(pageable).getContent().stream()
                    .map(AppointmentDto::new)
                    .toList();
        } else if (appointments.isEmpty()) {
            // si la lista esta vacia y SI se dieron parametros, significa que no hay citas que coincidan con los parametros
            return new ArrayList<>(); // retornamos una lista vacia
        }

        // filtrar citas que cumplan con todos los parametros dados
        notNullParams.forEach(param -> {
            switch (param) {
                case "petId" -> appointments.removeIf(appointment -> !Objects.equals(appointment.getPet().getId(), petId));
                case "veterinarianId" -> appointments.removeIf(appointment -> !appointment.getVeterinarian().getId().equals(veterinarianId));
                case "status" -> appointments.removeIf(appointment -> appointment.getStatus() != AppointmentStatus.valueOf(status.toUpperCase()));
                case "date" -> appointments.removeIf(appointment -> !appointment.getDate().isEqual(date));
            }
        });




        // paginacion
        var appointmentsDto = new ArrayList<AppointmentDto>();
        int startItem = page * size;
        int endItem = startItem + size - 1;

        for (int i = startItem; i < endItem; i++) {
            if (i >= appointments.size()) break;

            var appointmentDto = new AppointmentDto(appointments.get(i));
            appointmentsDto.add(appointmentDto);
        }

        // verificar que no haya duplicados
        return new ArrayList<>(Set.copyOf(appointmentsDto));
    }

    // obtner por id
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }

    public AppointmentDto updateStatus(Long id, AppointmentStatus status) {
        var appointment = findById(id);
        appointment.setStatus(status);
        appointment = appointmentRepository.save(appointment);
        return new AppointmentDto(appointment);
    }


    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
