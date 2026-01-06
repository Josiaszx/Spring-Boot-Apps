package com.app.veterinaria.service;

import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import com.app.veterinaria.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class AppointmentService {

    final private AppointmentRepository appointmentRepository;

    // listar citas segun id de veterinario
    public List<Appointment> findAllByVeterianrian(Veterinarian veterinarian) {
        return appointmentRepository.findAllByVeterinarian(veterinarian);
    }

    // lista citas segun fecha
    public List<Appointment> findAllByDate(LocalDate date) {
        return appointmentRepository.findAllByDate(date);
    }

    public List<Appointment> findAllByStatus(String status) {
        return appointmentRepository.findAllByStatus(AppointmentStatus.valueOf(status.toUpperCase()));
    }
}
