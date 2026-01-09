package com.app.veterinaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewMedicalRecordRequest {

    private Long petId;
    private Long veterinarianId;
    private Long appointmentId;
    private String description;
    private LocalDate date;
}
