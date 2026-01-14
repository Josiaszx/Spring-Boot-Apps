package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    public Appointment(LocalDate date, Pet pet, Veterinarian veterinarian) {
        this.date = date;
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.status = AppointmentStatus.SCHEDULED;
    }
}
