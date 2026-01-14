package com.app.veterinaria.dto;

import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppointmentDto {

    private Long id;
    private String petName;
    private String veterinarianName;
    private LocalDate date;
    private AppointmentStatus status;
    private String ownerName;
    private String reason;
    private String notes;

    public AppointmentDto(Appointment appointment) {
        this.id = appointment.getId();
        this.status = appointment.getStatus();
        this.date = appointment.getDate();
        this.petName = appointment.getPet().getName();
        this.veterinarianName = appointment.getVeterinarian().getFirstName() + " " + appointment.getVeterinarian().getLastName();
        this.ownerName = appointment.getPet().getOwner().getName() + " " + appointment.getPet().getOwner().getLastName();
        this.reason = appointment.getReason();
        this.notes = appointment.getNotes();
    }
}
