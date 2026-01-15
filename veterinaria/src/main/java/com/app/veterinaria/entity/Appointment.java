package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column
    private String reason;

    @Column
    private String notes;

    public Appointment(LocalDateTime date, Pet pet, Veterinarian veterinarian) {
        this.date = date.truncatedTo(ChronoUnit.MINUTES);
        this.pet = pet;
        this.veterinarian = veterinarian;
        this.status = AppointmentStatus.SCHEDULED;
    }
}
