package com.app.veterinaria.service;

import com.app.veterinaria.dto.MedicalRecordDto;
import com.app.veterinaria.dto.NewMedicalRecordRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.MedicalRecord;
import com.app.veterinaria.repository.MedicalRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MedicalRecordService {

    final private MedicalRecordRepository medicalRecordRepository;
    final private PetService petService;
    final private VeterinarianService veterinarianService;
    final private AppointmentService appointmentService;


    // REGISTRAR UN NUEVO EXPEDIENTE MEDICO
    public MedicalRecordDto save(NewMedicalRecordRequest medicalRecord) {
        var pet = petService.findEntityById(medicalRecord.getPetId());
        var veterinarian = veterinarianService.findEntityById(medicalRecord.getVeterinarianId());
        var appointment = appointmentService.findById(medicalRecord.getAppointmentId());

        var record = new MedicalRecord(medicalRecord, appointment, pet, veterinarian);

        record = medicalRecordRepository.save(record);
        return new MedicalRecordDto(record);
    }

    // OBTENER TODOS LOS EXPEDIENTES MEDICOS
    public List<MedicalRecordDto> findAll() {
        var records = medicalRecordRepository.findAll();
        return records.stream().map(MedicalRecordDto::new).toList();
    }

    // OBTENER EXPEDIENTE MEDICO POR ID
    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical Record not found"));
    }

    public List<MedicalRecordDto> findAllByPet(Long petId) {
        var pet = petService.findEntityById(petId);

        var records = medicalRecordRepository.findAllByPet(pet);
        if (records.isEmpty()) return List.of();

        return records.stream()
                .map(MedicalRecordDto::new)
                .toList();
    }

    public MedicalRecordDto updateRecord(Long id, NewMedicalRecordRequest request) {
        var record = findById(id);
        if (request.getDescription() != null) {
            record.setDescription(request.getDescription());
        }
        if (request.getDate() != null) {
            record.setRecordDate(request.getDate());
        }
        record = medicalRecordRepository.save(record);
        return new MedicalRecordDto(record);
    }

}
