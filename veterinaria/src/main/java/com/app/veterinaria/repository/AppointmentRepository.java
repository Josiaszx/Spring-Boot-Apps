package com.app.veterinaria.repository;

import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDate(LocalDate date);

    List<Appointment> findAllByStatus(AppointmentStatus status);

    List<Appointment> findAllByVeterinarian(Veterinarian veterinarian);

    List<Appointment> findAllByPet(Pet pet);
}
