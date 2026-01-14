package com.app.veterinaria.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class NewAppointmentRequest {

    @NotNull(message = "Pet cannot be empty")
    private Long petId;

    @NotNull(message = "Veterinarian cannot be empty")
    private Long veterinarianId;

    @NotNull( message = "Date cannot be empty")
    private LocalDate date;

    private String reason;

    private String notes;

}
