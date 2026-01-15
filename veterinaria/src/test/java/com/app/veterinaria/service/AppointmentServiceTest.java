package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewAppointmentRequest;
import com.app.veterinaria.entity.Appointment;
import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.entity.Pet;
import com.app.veterinaria.entity.Veterinarian;
import com.app.veterinaria.exception.ResourceNotFoundException;
import com.app.veterinaria.repository.AppointmentRepository;
import com.app.veterinaria.repository.VeterinarianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.app.veterinaria.entity.enums.AppointmentStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PetService petService;
    @Mock
    private VeterinarianRepository veterinarianRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private AppointmentDto appointmentDto;
    private NewAppointmentRequest request;
    private Pet pet;
    private Veterinarian veterinarian;

    @BeforeEach
    void setUp() {
        pet = Pet.builder()
                .id(1L)
                .owner(new Owner())
                .name("Pancho")
                .species("Ave")
                .breed("Loro")
                .birthDate(LocalDate.of(2022, 10, 10))
                .weight(1.5)
                .gender("macho")
                .build();
        pet.getOwner().setName("Juan");
        pet.getOwner().setLastName("Lopez");

        veterinarian = Veterinarian.builder()
                .id(1L)
                .firstName("Carlos")
                .lastName("Ramos")
                .specialty("Doctor")
                .licenseNumber("L-123")
                .build();

        appointment = Appointment.builder()
                .id(1L)
                .pet(pet).
                veterinarian(veterinarian)
                .date(LocalDate.now())
                .status(SCHEDULED)
                .reason("vacunacion")
                .notes("reposar")
                .build();

        appointmentDto = AppointmentDto.builder()
                .id(1L)
                .petName("Pancho")
                .veterinarianName("Carlos Ramos")
                .date(LocalDate.now())
                .status(SCHEDULED)
                .ownerName("Juan Lopez")
                .reason("vacunacion")
                .notes("reposar")
                .build();

        request = NewAppointmentRequest.builder()
                .petId(1L)
                .veterinarianId(1L)
                .date(LocalDate.now())
                .reason("vacunacion")
                .notes("reposar")
                .build();
    }

    @Test
    @DisplayName("Generar cita correctamente")
    void createAppointment() {
        when(petService.findEntityById(anyLong())).thenReturn(pet);
        when(veterinarianRepository.findById(anyLong())).thenReturn(Optional.of(veterinarian));
        when(appointmentRepository.save(any())).thenReturn(appointment);

        // creacion normal
        var result = appointmentService.save(request);
        assertEquals(appointmentDto, result);

        // verificar error si la fecha es anterior a hoy
        request.setDate(LocalDate.of(2020, 10, 10));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.save(request));
    }

    @Test
    @DisplayName("filtrar citas por mascotas")
    void filterByPets() {

        var pets = getPets();
        var appointments = getAppointments();

        var pet1 = pets.getFirst();
        var pet2 = pets.get(1);
        var pet3 = pets.get(2);

        when(petService.findEntityById(1L)).thenReturn(pet1);
        when(petService.findEntityById(2L)).thenReturn(pet2);
        when(petService.findEntityById(3L)).thenReturn(pet3);

        var pet1Appointments = List.of(appointments.getFirst(), appointments.getLast());
        when(appointmentRepository.findAllByPet(pet1)).thenReturn(pet1Appointments);

        var pet2Appointments = List.of(appointments.get(1), appointments.get(4), appointments.get(5));
        when(appointmentRepository.findAllByPet(pet2)).thenReturn(pet2Appointments);

        var pet3Appointments = List.of(appointments.get(2), appointments.get(3));
        when(appointmentRepository.findAllByPet(pet3)).thenReturn(pet3Appointments);

        var expected = pet1Appointments;
        var result = appointmentService.filterByPet(1L, appointments);
        assertEquals(expected, result);

        expected = pet2Appointments;
        result = appointmentService.filterByPet(2L, appointments);
        assertEquals(expected, result);

        expected = pet3Appointments;
        result = appointmentService.filterByPet(3L, appointments);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("filtrar citas por veterinarios")
    void filterByVeterinarians() {
        var appointments = getAppointments();
        var veterinarians = getVeterinarians();

        var vet1 = veterinarians.getFirst();
        var vet2 = veterinarians.get(1);

        when(veterinarianRepository.findById(1L)).thenReturn(Optional.of(vet1));
        when(veterinarianRepository.findById(2L)).thenReturn(Optional.of(vet2));

        var vet1Appointments = List.of(appointments.getFirst(), appointments.get(4), appointments.get(6));
        when(appointmentRepository.findAllByVeterinarian(vet1)).thenReturn(vet1Appointments);

        var vet2Appointments = List.of(appointments.get(1), appointments.get(2), appointments.get(3), appointments.get(5));
        when(appointmentRepository.findAllByVeterinarian(vet2)).thenReturn(vet2Appointments);

        // probar cuando la lista dada ya contiene elementos
        var expected = vet1Appointments;
        var result = appointmentService.filterByVeterinarian(1L, appointments);
        assertEquals(expected, result);

        expected = vet2Appointments;
        result = appointmentService.filterByVeterinarian(2L, appointments);
        assertEquals(expected, result);

        // probar cuando la lista dada no contiene elementos
        expected = vet1Appointments;
        result = appointmentService.filterByVeterinarian(1L, List.of());
        assertEquals(expected, result);

        expected = vet2Appointments;
        result = appointmentService.filterByVeterinarian(2L, List.of());
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("filtrar citas por status")
    void filterByStatus() {
        var appointments = getAppointments();
        var scheduled = "SCHEDULED";
        var cancelled = "CANCELLED";
        var closed = "CLOSED";

        var scheduledAppointments = appointments.subList(0, 4);
        var cancelledAppointments = List.of(appointments.get(5));
        var closedAppointments = List.of(appointments.get(4), appointments.getLast());

        when(appointmentRepository.findAllByStatus(SCHEDULED)).thenReturn(scheduledAppointments);
        when(appointmentRepository.findAllByStatus(CANCELLED)).thenReturn(cancelledAppointments);
        when(appointmentRepository.findAllByStatus(CLOSED)).thenReturn(closedAppointments);

        // Probar cuando la lista dada ya contiene elementos
        var expected = scheduledAppointments;
        var result = appointmentService.filterByStatus(scheduled, appointments);
        assertEquals(expected, result);

        expected = cancelledAppointments;
        result = appointmentService.filterByStatus(cancelled, appointments);
        assertEquals(expected, result);

        expected = closedAppointments;
        result = appointmentService.filterByStatus(closed, appointments);
        assertEquals(expected, result);

        // probar cuando la lista dada no contiene elementos
        expected = scheduledAppointments;
        result = appointmentService.filterByStatus(scheduled, List.of());
        assertEquals(expected, result);

        expected = cancelledAppointments;
        result = appointmentService.filterByStatus(cancelled, List.of());
        assertEquals(expected, result);

        expected = closedAppointments;
        result = appointmentService.filterByStatus(closed, List.of());
        assertEquals(expected, result);

        // probar error cuando se pasa un status invalido
        assertThrows(IllegalArgumentException.class, () -> appointmentService.filterByStatus("invalid", appointments));

        // probar funcionamiento correcto cuando se pasa el status en minusculas
        scheduled = "scheduled";
        cancelled = "cancelled";
        closed = "closed";

        result = appointmentService.filterByStatus(scheduled, appointments);
        assertEquals(scheduledAppointments, result);

        result = appointmentService.filterByStatus(cancelled, appointments);
        assertEquals(cancelledAppointments, result);

        result = appointmentService.filterByStatus(closed, appointments);
        assertEquals(closedAppointments, result);

        result = appointmentService.filterByStatus(scheduled, List.of());
        assertEquals(scheduledAppointments, result);

        result = appointmentService.filterByStatus(cancelled, List.of());
        assertEquals(cancelledAppointments, result);

        result = appointmentService.filterByStatus(closed, List.of());
        assertEquals(closedAppointments, result);
    }

    @Test
    @DisplayName("filtrar citas por fecha")
    void filterByDate() {
        var appointments = getAppointments();

        var date1 = LocalDate.now().plusDays(1);
        var date2 = LocalDate.now().plusDays(2);
        var date3 = LocalDate.now();

        var date1Appointments = appointments.subList(0, 3);
        var date2Appointments = List.of(appointments.get(3), appointments.get(5));
        var date3Appointments = List.of(appointments.get(4), appointments.getLast());

        when(appointmentRepository.findAllByDate(LocalDate.now().plusDays(1))).thenReturn(date1Appointments);
        when(appointmentRepository.findAllByDate(LocalDate.now().plusDays(2))).thenReturn(date2Appointments);
        when(appointmentRepository.findAllByDate(LocalDate.now())).thenReturn(date3Appointments);

        // probar cuando la lista dada ya contiene elementos
        var result = appointmentService.filterByDate(date1, appointments);
        assertEquals(date1Appointments, result);

        result = appointmentService.filterByDate(date2, appointments);
        assertEquals(date2Appointments, result);

        result = appointmentService.filterByDate(date3, appointments);
        assertEquals(date3Appointments, result);

        // probar cuando la lista dada no contiene elementos
        result = appointmentService.filterByDate(date1, List.of());
        assertEquals(date1Appointments, result);

        result = appointmentService.filterByDate(date2, List.of());
        assertEquals(date2Appointments, result);

        result = appointmentService.filterByDate(date3, List.of());
        assertEquals(date3Appointments, result);

        // probar error cuando se pasa un parametro como null
        assertThrows(IllegalArgumentException.class, () -> appointmentService.filterByDate(null, appointments));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.filterByDate(LocalDate.now(), null));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.filterByDate(null, null));
    }

    @Test
    @DisplayName("Paginacion correcta de citas")
    void paginate() {
        var appointments = getAppointments();
        var appointmentDtos = getAppointmentsDto(appointments);
        Integer page = 0;
        Integer size = 1;

        // probar a paginar solo el primer elemento
        var expected = List.of(appointmentDtos.getFirst());
        var result = appointmentService.paginate(appointments, page, size);
        assertEquals(expected, result);

        // paginar seguna tamaño
        for (int i = 2; i < appointmentDtos.size(); i++) {
            size = i;
            expected = appointmentDtos.subList(0, i);
            result = appointmentService.paginate(appointments, page, size);
            assertEquals(expected, result);
        }

        // paginar seguna pagina
        size = 1;
        for (int i = 0; i < appointmentDtos.size(); i++) {
            page = i;
            expected = List.of(appointmentDtos.get(i));
            result = appointmentService.paginate(appointments, page, size);
            assertEquals(expected, result);
        }

        // verificar que retorne un unico elemento cuando el tamaño es mayor que la cantidad de elementos disponibles
        size = 2;
        page = 3;
        expected = List.of(appointmentDtos.getLast());
        result = appointmentService.paginate(appointments, page, size);
        assertEquals(expected, result);

        // verificar que retorne una lista vacia si no hay suficientes elementos para hacer la paginacion
        size = 3;
        page = 3;
        expected = List.of();
        result = appointmentService.paginate(appointments, page, size);
        assertEquals(expected, result);

        // verificar que se lance error si pasa algun valor negativo
        assertThrows(IllegalArgumentException.class, () -> appointmentService.paginate(appointments, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.paginate(appointments, 1, -1));
        assertThrows(IllegalArgumentException.class, () -> appointmentService.paginate(appointments, -1, -1));

        // veririficar que se devuelvan todos los elementos si se excede el tamaño
        size = 10;
        page = 0;
        expected = appointmentDtos;
        result = appointmentService.paginate(appointments, page, size);
        assertEquals(expected, result);

        // verificar que se devuelva una lista vacia si la pagina es mayor que la cantidad de paginas disponibles
        size = 1;
        page = 10;
        expected = List.of();
        result = appointmentService.paginate(appointments, page, size);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Mostrar citas segun parametros")
    void findAllByParams() {
        var appointments = getAppointments();
        var pets = getPets();
        var veterinarians = getVeterinarians();
        var appointmentsDtos = appointmentDto;

        // probar cuando todos los parámetros son nulos (debe devolver todas las citas paginadas)
        Page<Appointment> page = new PageImpl<>(appointments);
        when(appointmentRepository.findAll(any(Pageable.class))).thenReturn(page);

        var result = appointmentService.findAllByParams(null, null, null, null, 0, 10);
        assertEquals(appointments.size(), result.size());
        assertEquals(new AppointmentDto(appointments.getFirst()), result.getFirst());


        // probar filtrar por petId
        var pet1 = pets.getFirst();
        var pet1Appointments = List.of(appointments.getFirst(), appointments.get(6));
        when(petService.findEntityById(1L)).thenReturn(pet1);
        when(appointmentRepository.findAllByPet(pet1)).thenReturn(pet1Appointments);

        result = appointmentService.findAllByParams(1L, null, null, null, 0, 10);
        assertEquals(2, result.size());
        assertEquals(new AppointmentDto(pet1Appointments.getFirst()), result.getFirst());

        // probar filtrar por petId y status (SCHEDULED)
        // filterByPet ya devolvió pet1Appointments (indices 0 y 6)
        // El indice 0 es SCHEDULED, el 6 es CLOSED. Por lo tanto, el resultado debe ser solo el indice 0.
        result = appointmentService.findAllByParams(1L, null, "SCHEDULED", null, 0, 10);
        assertEquals(1, result.size());
        assertEquals(SCHEDULED, result.getFirst().getStatus());
        assertEquals(1L, result.getFirst().getId());

        // probar filtrar por veterinario y status
        var vet2 = veterinarians.get(1);
        var vet2Appointments = List.of(appointments.get(1), appointments.get(2), appointments.get(3), appointments.get(5));
        when(veterinarianRepository.findById(2L)).thenReturn(Optional.of(vet2));
        when(appointmentRepository.findAllByVeterinarian(vet2)).thenReturn(vet2Appointments);

        // vet2Appointments: 
        // idx 1: SCHEDULED
        // idx 2: SCHEDULED
        // idx 3: SCHEDULED
        // idx 5: CANCELLED
        result = appointmentService.findAllByParams(null, 2L, "CANCELLED", null, 0, 10);
        assertEquals(1, result.size());
        assertEquals(CANCELLED, result.getFirst().getStatus());
        assertEquals(6L, result.getFirst().getId());

        // probar cuando no hay coincidencias
        result = appointmentService.findAllByParams(1L, 2L, null, null, 0, 10);
        assertTrue(result.isEmpty());

        // probar filtrado por fecha
        LocalDate date = LocalDate.now();
        var dateAppointments = List.of(appointments.get(4), appointments.get(6));
        when(appointmentRepository.findAllByDate(date)).thenReturn(dateAppointments);

        result = appointmentService.findAllByParams(null, null, null, date, 0, 10);
        assertEquals(2, result.size());
    }

    List<Pet> getPets() {
        Owner owner = new Owner();
        owner.setName("Juan");
        owner.setLastName("Lopez");

        return List.of(
                Pet.builder()
                        .id(1L)
                        .name("Pancho")
                        .owner(owner)
                        .build(),
                Pet.builder()
                        .id(2L)
                        .name("Michi")
                        .owner(owner)
                        .build(),
                Pet.builder()
                        .id(3L)
                        .name("Doggy")
                        .owner(owner)
                        .build()
        );
    }

    List<Veterinarian> getVeterinarians() {
        return List.of(
                Veterinarian.builder()
                        .id(1L)
                        .firstName("Carlos")
                        .build(),
                Veterinarian.builder()
                        .id(2L)
                        .firstName("Juan")
                        .build()
        );
    }

    List<Appointment> getAppointments() {
        var pets = getPets();
        var veterinarians = getVeterinarians();

        return List.of(
                Appointment.builder()
                        .id(1L)
                        .pet(pets.getFirst())
                        .veterinarian(veterinarians.getFirst())
                        .status(SCHEDULED)
                        .date(LocalDate.now().plusDays(1))
                        .build(),
                Appointment.builder()
                        .id(2L)
                        .pet(pets.get(1))
                        .veterinarian(veterinarians.get(1))
                        .status(SCHEDULED)
                        .date(LocalDate.now().plusDays(1))
                        .build(),
                Appointment.builder()
                        .id(3L)
                        .pet(pets.get(2))
                        .veterinarian(veterinarians.get(1))
                        .date(LocalDate.now().plusDays(1))
                        .status(SCHEDULED)
                        .build(),
                Appointment.builder()
                        .id(4L)
                        .pet(pets.get(2))
                        .veterinarian(veterinarians.get(1))
                        .date(LocalDate.now().plusDays(2))
                        .status(SCHEDULED)
                        .build(),
                Appointment.builder()
                        .id(5L)
                        .pet(pets.get(1))
                        .veterinarian(veterinarians.getFirst())
                        .date(LocalDate.now())
                        .status(CLOSED)
                        .build(),
                Appointment.builder()
                        .id(6L)
                        .pet(pets.get(1))
                        .veterinarian(veterinarians.get(1))
                        .date(LocalDate.now().plusDays(2))
                        .status(CANCELLED)
                        .build(),
                Appointment.builder()
                        .id(7L)
                        .pet(pets.getFirst())
                        .veterinarian(veterinarians.getFirst())
                        .status(CLOSED)
                        .date(LocalDate.now())
                        .build()
        );
    }

    List<AppointmentDto> getAppointmentsDto(List<Appointment> appointments) {
        return appointments.stream().map(AppointmentDto::new).toList();
    }
}