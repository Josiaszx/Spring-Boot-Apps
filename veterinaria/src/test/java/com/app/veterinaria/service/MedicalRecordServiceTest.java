package com.app.veterinaria.service;

import com.app.veterinaria.dto.MedicalRecordDto;
import com.app.veterinaria.dto.NewMedicalRecordRequest;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.MedicalRecord;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    @Mock
    private PetService petService;
    @Mock
    private VeterinarianService veterinarianService;
    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private Pet pet;
    private Veterinarian veterinarian;
    private Appointment appointment;
    private MedicalRecord medicalRecord;
    private NewMedicalRecordRequest request;

    @BeforeEach
    void setUp() {
        pet = Pet.builder().id(1L).name("Firulais").build();
        veterinarian = Veterinarian.builder().id(1L).firstName("John").lastName("Doe").build();
        appointment = Appointment.builder().id(1L).build();
        request = new NewMedicalRecordRequest(1L, 1L, 1L, "Checkup", LocalDate.now());
        medicalRecord = new MedicalRecord(request, appointment, pet, veterinarian);
        medicalRecord.setId(1L);
    }

    @Test
    @DisplayName("Guardar registro medico")
    void save() {
        when(petService.findEntityById(1L)).thenReturn(pet);
        when(veterinarianService.findEntityById(1L)).thenReturn(veterinarian);
        when(appointmentService.findById(1L)).thenReturn(appointment);
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

        var result = medicalRecordService.save(request);

        assertNotNull(result);
        assertEquals(medicalRecord.getDescription(), result.getDescription());
        assertEquals(pet.getName(), result.getPetName());
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
    }

    @Test
    @DisplayName("Listar registros")
    void findAll() {
        when(medicalRecordRepository.findAll()).thenReturn(List.of(medicalRecord));

        var result = medicalRecordService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(medicalRecord.getId(), result.get(0).getId());
        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Mostrar registro medico por id")
    void findById() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));

        var result = medicalRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(medicalRecordRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Lanzar error si el id es invalido")
    void findByIdNotFound() {
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.findById(1L));
    }

    @Test
    @DisplayName("Listar reportes medicos por id de mascota")
    void findAllByPet() {
        when(petService.findEntityById(1L)).thenReturn(pet);
        when(medicalRecordRepository.findAllByPet(pet)).thenReturn(List.of(medicalRecord));

        var result = medicalRecordService.findAllByPet(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(medicalRecordRepository, times(1)).findAllByPet(pet);
    }

    @Test
    @DisplayName("Lista vacia si no existen registros medicos por mascota")
    void findAllByPetEmpty() {
        when(petService.findEntityById(1L)).thenReturn(pet);
        when(medicalRecordRepository.findAllByPet(pet)).thenReturn(List.of());

        var result = medicalRecordService.findAllByPet(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("actualizar registro medico")
    void updateRecord() {
        var updateRequest = new NewMedicalRecordRequest();
        updateRequest.setDescription("Updated description");
        updateRequest.setDate(LocalDate.now().plusDays(1));

        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = medicalRecordService.updateRecord(1L, updateRequest);

        assertNotNull(result);
        assertEquals("Updated description", result.getDescription());
        assertEquals(updateRequest.getDate(), result.getRecordDate());
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
    }
}