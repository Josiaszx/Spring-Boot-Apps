package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.AppointmentRepository;
import com.app.veterinaria.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    final private AppointmentRepository appointmentRepository;
    final private PetService petService;

    // usamos el repositorio de veterinarios para evitar ciclo de inyeccion ya que esta clase tambien se inyecta en VeterinarianService
    final private VeterinarianRepository veterinarianRepository;
    public Veterinarian getVeterinarianById(Long id) {
        return veterinarianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinarian not found with id: " + id));
    }


    // listar citas segun id de veterinario
    public List<Appointment> findAllByVeterinarian(Veterinarian veterinarian) {
        return appointmentRepository.findAllByVeterinarian(veterinarian);
    }

    // lista citas segun fecha
    public List<Appointment> findAllByDate(LocalDate date) {
        return appointmentRepository.findAllByDate(date);
    }

    // listar citas segun estado
    public List<Appointment>findAllByStatus(String status) {
        return appointmentRepository.findAllByStatus(
                AppointmentStatus.valueOf(status.toUpperCase())
        );
    }

    // agendar cita
    public AppointmentDto save(NewAppointmentRequest request) {
        var pet = petService.findEntityById(request.getPetId());
        var veterinarian = getVeterinarianById(request.getVeterinarianId());
        var date = request.getDate();
        if (date.isBefore(LocalDate.now())) throw new IllegalArgumentException("Date must be greater than today");
        var appointment = new Appointment(date, pet, veterinarian);
        appointment.setReason(request.getReason());
        appointment.setNotes(request.getNotes());
        appointment = appointmentRepository.save(appointment);
        return new AppointmentDto(appointment);
    }

    // listar citas segun parametros
    public List<AppointmentDto> findAllByParams(
            Long petId, Long veterinarianId, String status, LocalDate date, Integer page, Integer size
    ) {

        List<Appointment> appointments = new ArrayList<>();

        if (petId != null) appointments = filterByPet(petId, appointments);

        if (veterinarianId != null) appointments = filterByVeterinarian(veterinarianId, appointments);

        if (status != null) appointments = filterByStatus(status, appointments);

        if (date != null) appointments = filterByDate(date, appointments);

        boolean areAllParamsNull = petId == null && veterinarianId == null && status == null && date == null;
        // areAllParamsNull == true, significa que no se dieron parametros, por lo tanto se asume que se quiere listar todas las citas
        if (appointments.isEmpty() && areAllParamsNull) {
            Pageable pageable = PageRequest.of(page, size);
            return appointmentRepository.findAll(pageable).getContent().stream()
                    .map(AppointmentDto::new)
                    .toList();
        }
        else if (appointments.isEmpty()) {
            // si la lista esta vacia y SI se dieron parametros, significa que no hay citas que coincidan con los parametros
            return List.of();
        }

        return paginate(appointments, page, size);
    }

    // filtrar citas por mascota
    public List<Appointment> filterByPet(Long petId, List<Appointment> appointments) {
        var pet = petService.findEntityById(petId);
        return appointmentRepository.findAllByPet(pet);
    }

    // filtrar citas por veterinario
    public List<Appointment> filterByVeterinarian(Long veterinarianId, List<Appointment> appointments) {
        var veterinarian = getVeterinarianById(veterinarianId);

        if (appointments.isEmpty()) {
            return appointmentRepository.findAllByVeterinarian(veterinarian);
        } else {
            return appointments.stream()
                    .filter(appointment -> appointment.getVeterinarian().equals(veterinarian))
                    .toList();
        }
    }

    // filtrar citas por status
    public List<Appointment> filterByStatus(String status, List<Appointment> appointments) {
        if (appointments.isEmpty()) {
            return findAllByStatus(status);
        } else {
            return appointments.stream()
                    .filter(appointment -> appointment.getStatus() == AppointmentStatus.valueOf(status.toUpperCase()))
                    .toList();
        }
    }

    // filtrar citas por fecha
    public List<Appointment> filterByDate(LocalDate date, List<Appointment> appointments) {
        if (appointments == null || date == null) throw new IllegalArgumentException("Date and appointments cannot be null");
        if (appointments.isEmpty()) {
            return findAllByDate(date);
        } else {
            return appointments.stream()
                    .filter(appointment -> appointment.getDate().isEqual(date))
                    .toList();
        }
    }

    // paginar respuesta
    public List<AppointmentDto> paginate(List<Appointment> appointments, Integer page, Integer size) {
        if (page < 0 || size < 0) throw new IllegalArgumentException("Page number cannot be negative");
        var appointmentsDto = new ArrayList<AppointmentDto>();
        int startItem = page * size;
        int endItem = startItem + size;

        for (int i = startItem; i < endItem; i++) {
            if (i >= appointments.size()) break;
            var appointmentDto = new AppointmentDto(appointments.get(i));
            appointmentsDto.add(appointmentDto);
        }

        return appointmentsDto;
    }

    // obtner por id
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> {
                    var error = new ResourceNotFoundException("Appointment not found with id: " + id);
                    error.setMethod(HttpMethod.GET);
                    error.setPath("api/appointments/" + id);
                    return error;
                });
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
