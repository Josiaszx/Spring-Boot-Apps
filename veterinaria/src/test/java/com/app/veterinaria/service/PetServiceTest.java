package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewPetRequest;
import com.app.veterinaria.dto.PetDto;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private PetService petService;

    private Owner owner;
    private Pet pet;
    private NewPetRequest request;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setName("John");
        owner.setLastName("Doe");
        owner.setPhoneNumber("123456789");

        pet = new Pet();
        pet.setId(1L);
        pet.setName("Fido");
        pet.setSpecies("Dog");
        pet.setGender("Male");
        pet.setOwner(owner);

        request = new NewPetRequest();
        request.setOwnerId(1L);
        request.setName("Fido");
        request.setSpecies("Dog");
        request.setGender("Male");
    }

    @Test
    @DisplayName("Debe guardar una mascota correctamente")
    void save_Success() {
        when(ownerService.findById(1L)).thenReturn(owner);
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        PetDto result = petService.save(request);

        assertNotNull(result);
        assertEquals("Fido", result.getName());
        verify(ownerService).findById(1L);
        verify(petRepository).save(any(Pet.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al guardar si ownerId es nulo")
    void save_ThrowsException_WhenOwnerIdIsNull() {
        request.setOwnerId(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petService.save(request);
        });

        assertEquals("El ID del dueño es obligatorio", exception.getMessage());
        verifyNoInteractions(petRepository);
        verifyNoInteractions(ownerService);
    }

    @Test
    @DisplayName("Debe encontrar todas las mascotas por ownerId")
    void findAll_ByOwnerId() {
        when(ownerService.findById(1L)).thenReturn(owner);
        when(petRepository.findByOwner(owner)).thenReturn(List.of(pet));

        List<PetDto> result = petService.findAll(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Fido", result.get(0).getName());
        verify(ownerService).findById(1L);
        verify(petRepository).findByOwner(owner);
    }

    @Test
    @DisplayName("Debe encontrar todas las mascotas cuando ownerId es -1")
    void findAll_AllPets() {
        when(petRepository.findAll()).thenReturn(List.of(pet));

        List<PetDto> result = petService.findAll(-1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(petRepository).findAll();
        verifyNoInteractions(ownerService);
    }

    @Test
    @DisplayName("Debe encontrar mascota por ID")
    void getDtoWithId_Success() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        PetDto result = petService.getDtoWithId(1L);

        assertNotNull(result);
        assertEquals("Fido", result.getName());
        verify(petRepository).findById(1L);
    }


    @Test
    @DisplayName("Debe retornar entidad mascota por ID")
    void findEntityById_Success() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet result = petService.findEntityById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(petRepository).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción si entidad mascota no existe por ID")
    void findEntityById_ThrowsException_WhenNotFound() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> petService.findEntityById(1L));
    }

    @Test
    @DisplayName("Debe actualizar mascota correctamente")
    void update_Success() {
        Pet newPetData = new Pet();
        newPetData.setName("Fido Updated");

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        PetDto result = petService.update(1L, newPetData);

        assertNotNull(result);
        verify(petRepository).findById(1L);
        verify(petRepository).save(pet);
    }

    @Test
    @DisplayName("Debe eliminar mascota")
    void delete_Success() {
        doNothing().when(petRepository).deleteById(1L);

        petService.delete(1L);

        verify(petRepository).deleteById(1L);
    }
}