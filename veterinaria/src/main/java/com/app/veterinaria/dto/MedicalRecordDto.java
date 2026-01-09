package com.app.veterinaria.dto;

import com.app.veterinaria.entity.MedicalRecord;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordDto {

    private Long id;
    private String petName;
    private String veterinarianName;
    private Long appointmentId;
    private LocalDate recordDate;
    private String description;


    public MedicalRecordDto(MedicalRecord medicalRecord) {
        this.recordDate = medicalRecord.getRecordDate();
        this.description = medicalRecord.getDescription();
        this.appointmentId = medicalRecord.getAppointment().getId();
        this.petName = medicalRecord.getPet().getName();
        this.veterinarianName = medicalRecord.getVeterinarian().getFirstName() + " " + medicalRecord.getVeterinarian().getLastName();
        this.id = medicalRecord.getId();
    }
}
