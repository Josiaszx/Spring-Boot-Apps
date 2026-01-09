package com.app.veterinaria.entity;

import com.app.veterinaria.dto.NewMedicalRecordRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Data
@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private LocalDate recordDate;

    private String description;


    public MedicalRecord(
            NewMedicalRecordRequest medicalRecord,
            Appointment appointment,
            Pet pet,
            Veterinarian veterinarian
    ) {
        this.recordDate = medicalRecord.getDate();
        this.description = medicalRecord.getDescription();
        this.appointment = appointment;
        this.pet = pet;
        this.veterinarian = veterinarian;
    }
}
