package com.app.veterinaria.dto;

import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {

    private Long id;
    private String petName;
    private String veterinarianName;
    private LocalDate date;
    private AppointmentStatus status;
    private String ownerName;

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getId();
        this.status = appointment.getStatus();
        this.date = appointment.getDate();
        this.petName = appointment.getPet().getName();
        this.veterinarianName = appointment.getVeterinarian().getFirstName() + " " + appointment.getVeterinarian().getLastName();
        this.ownerName = appointment.getPet().getOwner().getName() + " " + appointment.getPet().getOwner().getLastName();
    }
}
