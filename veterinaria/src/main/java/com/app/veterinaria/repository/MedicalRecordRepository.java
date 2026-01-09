package com.app.veterinaria.repository;

import com.app.veterinaria.entity.MedicalRecord;
import com.app.veterinaria.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findAllByPet(Pet pet);
}
