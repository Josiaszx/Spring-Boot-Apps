package com.app.veterinaria.service;

import com.app.veterinaria.dto.AppointmentDto;
import com.app.veterinaria.dto.NewVeterinarianRequest;
import com.app.veterinaria.dto.VeterinarianDto;
import com.app.veterinaria.entity.*;
import com.app.veterinaria.repository.VeterinarianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.app.veterinaria.entity.enums.AppointmentStatus.*;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VeterinarianServiceTest {

    @Mock
    private VeterinarianRepository veterinarianRepository;
    @Mock
    private UserService userService;
    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private VeterinarianService veterinarianService;

    private Veterinarian veterinarian;
    private NewVeterinarianRequest request;
    private Role veterinarianRole;
    private User user;


    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(null)
                .username("JuanL")
                .password("j123")
                .email("juan@gmail.com")
                .role(veterinarianRole)
                .active(true)
                .createdAt(LocalDate.now())
                .build();

        veterinarian = Veterinarian.builder()
                .id(null)
                .user(user)
                .firstName("Juan")
                .lastName("Lopez")
                .specialty("Cirujano")
                .licenseNumber("123")
                .build();

        request = (NewVeterinarianRequest) NewVeterinarianRequest.builder()
                .specialty("Cirujano")
                .licenseNumber("123")
                .username("JuanL")
                .firstName("Juan")
                .lastName("Lopez")
                .password("j123")
                .email("juan@gmail.com")
                .role("VETERINARIAN")
                .active(true)
                .createdAt(LocalDate.now())
                .build();

        veterinarianRole = new Role("VETERINARIAN");
    }

    @Test
    void createVeterinarian() {

        when(userService.createAndSaveUser(request)).thenReturn(user);

        // crear veterinario
        Veterinarian result = veterinarianService.createVeterinarian(request);
        assertEquals(veterinarian, result);

        // asumir rol VETERINARIAN cuando el role en request es null
        request.setRole(null);
        result = veterinarianService.createVeterinarian(request);
        assertEquals(veterinarian, result);

        // esperar error si se intenta crear un veterinario con rol diferente a VETERINARIAN
        request.setRole("ADMIN");
        assertThrows(RuntimeException.class, () -> veterinarianService.createVeterinarian(request));
        request.setRole("OWNER");
        assertThrows(RuntimeException.class, () -> veterinarianService.createVeterinarian(request));
    }

    @Test
    void createAndSaveVeterinarian() {
        when(userService.createAndSaveUser(request)).thenReturn(user);
        when(veterinarianRepository.save(veterinarian)).thenReturn(veterinarian);

        Veterinarian result = veterinarianService.createAndSaveVeterinarian(request);
        assertEquals(veterinarian, result);
    }

    @Test
    void createAndSaveVeterinarianWithResponse() {
        when(veterinarianService.createAndSaveVeterinarian(request)).thenReturn(veterinarian);

        var expectedResponseBody = new HashMap<String, Object>();
        expectedResponseBody.put("message", "Veterinario creado exitosamente");
        expectedResponseBody.put("status", HttpStatus.CREATED.value() + " " + HttpStatus.CREATED.getReasonPhrase());
        expectedResponseBody.put("path", "/api/users/veterinarian");
        expectedResponseBody.put("name", "Juan Lopez");
        expectedResponseBody.put("username", "JuanL");
        expectedResponseBody.put("role", "VETERINARIAN");
        expectedResponseBody.put("email", "juan@gmail.com");
        expectedResponseBody.put("Licsence number", "123");

        var expectedResponse = ResponseEntity.ok(expectedResponseBody);
        var response = veterinarianService.createAndSaveVeterinarianWithResponse(request);
        assertEquals(expectedResponse, response);
    }

    @Test
    void findAll() {
        var veterinariosDto = getVeterinariansDto();
        var veterinarios= getVeterinarians();

        // cuando se consulta por todos los veterinarios
        when(veterinarianRepository.findAll()).thenReturn(veterinarios);

        // caso general para cualquier especialidad no defininda
        when(veterinarianRepository.findAllBySpecialty(anyString())).thenReturn(Collections.emptyList());

        // cuando se consulta por especialidad
        var doctores = veterinarios.subList(0, 2);
        when(veterinarianRepository.findAllBySpecialty("Doctor")).thenReturn(doctores);
        var cirujanos = veterinarios.subList(2, 4);
        when(veterinarianRepository.findAllBySpecialty("Cirujano")).thenReturn(cirujanos);
        var enfermeros = veterinarios.subList(4, 6);
        when(veterinarianRepository.findAllBySpecialty("Enfermero")).thenReturn(enfermeros);


        // esperar que se retornen todos los veterinarios
        var expectedResponse = veterinariosDto;
        var response = veterinarianService.findAll("all");
        assertEquals(expectedResponse, response);

        // esperar que se retornen solo los veterinarios de la especialidad Doctor
        expectedResponse = veterinariosDto.subList(0, 2);
        response = veterinarianService.findAll("Doctor");
        assertEquals(expectedResponse, response);

        // esperar que se retornen solo los veterinarios de la especialidad Cirujano
        expectedResponse = veterinariosDto.subList(2, 4);
        response = veterinarianService.findAll("Cirujano");
        assertEquals(expectedResponse, response);

        // esperar que se retornen solo los veterinarios de la especialidad Enfermero
        expectedResponse = veterinariosDto.subList(4, 6);
        response = veterinarianService.findAll("Enfermero");
        assertEquals(expectedResponse, response);

        // comportamiento esperado cuando se pasa una especialidad invalida
        expectedResponse = Collections.emptyList();
        response = veterinarianService.findAll("invalid");
        assertEquals(expectedResponse, response);
    }

    @Test
    void filterByDate() {
        var appointments = getAppointments();
        var date1Appointments = getAppointments().subList(0, 3);
        var date2Appointments = getAppointments().subList(3, 5);
        var date3Appointments = getAppointments().subList(5, 7);
        var date4Appointments = getAppointments().subList(7, 9);

        // caso general, si no existe dicha fecha, retornar una lista vacia
        var result = veterinarianService.filterByDate(LocalDate.of(1999, 12, 10), appointments);
        assertEquals(List.of(), result);

        // comprobar que se retornan las citas de las fechas indicadas
        var expected = date1Appointments;
        result = veterinarianService.filterByDate(LocalDate.now(), appointments);
        assertEquals(expected, result);

        expected = date2Appointments;
        result = veterinarianService.filterByDate(LocalDate.now().plusDays(1), appointments);
        assertEquals(expected, result);

        expected = date3Appointments;
        result = veterinarianService.filterByDate(LocalDate.now().plusDays(2), appointments);
        assertEquals(expected, result);

        expected = date4Appointments;
        result = veterinarianService.filterByDate(LocalDate.now().plusDays(3), appointments);
        assertEquals(expected, result);
    }

    @Test
    void filterByStatus() {
        var appointments = getAppointments();
        var scheduledAppointments = getAppointments().subList(0, 3);
        var cancelledAppointments = getAppointments().subList(3, 6);
        var closedAppointments = getAppointments().subList(6, 9);

        // esperar funcionamiento correcto cuando se pasa el status en mayusculas
        var result = veterinarianService.filterByStatus("SCHEDULED", appointments);
        assertEquals(scheduledAppointments, result);

        result = veterinarianService.filterByStatus("CANCELLED", appointments);
        assertEquals(cancelledAppointments, result);

        result = veterinarianService.filterByStatus("CLOSED", appointments);
        assertEquals(closedAppointments, result);

        // esperar funcionamineto correcto cuando se pasa el status en minusculas
        result = veterinarianService.filterByStatus("scheduled", appointments);
        assertEquals(scheduledAppointments, result);

        result = veterinarianService.filterByStatus("cancelled", appointments);
        assertEquals(cancelledAppointments, result);

        result = veterinarianService.filterByStatus("closed", appointments);
        assertEquals(closedAppointments, result);

        // esperar error cuando se pasa un status invalido
        assertThrows(IllegalArgumentException.class, () -> veterinarianService.filterByStatus("invalid", appointments));
    }


    @Test
    void findAllAppointmentsByVeterinarian() {

        var appointments = getAppointments();

        var vet1 = getVeterinarians().getFirst();
        var vet2 = getVeterinarians().get(1);
        var vet3 = getVeterinarians().get(2);

        var vet1Appointments = appointments.subList(0, 3);
        var vet2Appointments = appointments.subList(3, 6);
        var vet3Appointments = appointments.subList(6, 9);

        when(veterinarianRepository.findById(1L)).thenReturn(of(vet1));
        when(veterinarianRepository.findById(2L)).thenReturn(of(vet2));
        when(veterinarianRepository.findById(3L)).thenReturn(of(vet3));

        when(appointmentService.findAllByVeterianrian(vet1)).thenReturn(vet1Appointments);
        when(appointmentService.findAllByVeterianrian(vet2)).thenReturn(vet2Appointments);
        when(appointmentService.findAllByVeterianrian(vet3)).thenReturn(vet3Appointments);

        // esperar que se retornen todas los citas del veterinario 1
        var expectedResult = getAppointmentsDto(vet1Appointments);
        var result = veterinarianService.findAllAppointmentsByVeterinarian(1L, null, null);
        assertEquals(expectedResult, result);

        // esperar que se retornen todas los citas del veterinario 2
        expectedResult = getAppointmentsDto(vet2Appointments);
        result = veterinarianService.findAllAppointmentsByVeterinarian(2L, null, null);
        assertEquals(expectedResult, result);

        // esperar que se retornen todas los citas del veterinario 3
        expectedResult = getAppointmentsDto(vet3Appointments);
        result = veterinarianService.findAllAppointmentsByVeterinarian(3L, null, null);
        assertEquals(expectedResult, result);
    }

    // obtener lista de veterinarios en VeterinarianDto para pruebas
    List<VeterinarianDto> getVeterinariansDto() {
        VeterinarianDto veterinario1 = VeterinarianDto.builder()
                .firstName("Carlos")
                .lastName("González")
                .specialty("Doctor")
                .licenseNumber("LIC-1")
                .email("carlos.gonzalez@vetclinic.com")
                .build();

        VeterinarianDto veterinario2 = VeterinarianDto.builder()
                .firstName("Ana")
                .lastName("Martínez")
                .specialty("Doctor")
                .licenseNumber("LIC-1")
                .email("ana.martinez@vetclinic.com")
                .build();

        VeterinarianDto veterinario3 = VeterinarianDto.builder()
                .firstName("Roberto")
                .lastName("López")
                .specialty("Cirujano")
                .licenseNumber("LIC-1")
                .email("roberto.lopez@vetclinic.com")
                .build();

        VeterinarianDto veterinario4 = VeterinarianDto.builder()
                .firstName("María")
                .lastName("Rodríguez")
                .specialty("Cirujano")
                .licenseNumber("LIC-1")
                .email("maria.rodriguez@vetclinic.com")
                .build();

        VeterinarianDto veterinario5 = VeterinarianDto.builder()
                .firstName("Luis")
                .lastName("Fernández")
                .specialty("Enfermero")
                .licenseNumber("LIC-1")
                .email("luis.fernandez@vetclinic.com")
                .build();

        VeterinarianDto veterinario6 = VeterinarianDto.builder()
                .firstName("Sofía")
                .lastName("Pérez")
                .specialty("Enfermero")
                .licenseNumber("LIC-1")
                .email("sofia.perez@vetclinic.com")
                .build();

        return List.of(veterinario1, veterinario2, veterinario3, veterinario4, veterinario5, veterinario6);
    }

    // obtener lista de veterinarios para pruebas
    List<Veterinarian> getVeterinarians() {
        Veterinarian veterinario1 = Veterinarian.builder()
                .firstName("Carlos")
                .lastName("González")
                .specialty("Doctor")
                .licenseNumber("LIC-1")
                .user((new User()))
                .build();
        veterinario1.getUser().setEmail("carlos.gonzalez@vetclinic.com");

        Veterinarian veterinario2 = Veterinarian.builder()
                .firstName("Ana")
                .lastName("Martínez")
                .specialty("Doctor")
                .licenseNumber("LIC-1")
                .user(new User())
                .build();
        veterinario2.getUser().setEmail("ana.martinez@vetclinic.com");

        Veterinarian veterinario3 = Veterinarian.builder()
                .firstName("Roberto")
                .lastName("López")
                .specialty("Cirujano")
                .licenseNumber("LIC-1")
                .user(new User())
                .build();
        veterinario3.getUser().setEmail("roberto.lopez@vetclinic.com");

        Veterinarian veterinario4 = Veterinarian.builder()
                .firstName("María")
                .lastName("Rodríguez")
                .specialty("Cirujano")
                .licenseNumber("LIC-1")
                .user(new User())
                .build();
        veterinario4.getUser().setEmail("maria.rodriguez@vetclinic.com");

        Veterinarian veterinario5 = Veterinarian.builder()
                .firstName("Luis")
                .lastName("Fernández")
                .specialty("Enfermero")
                .licenseNumber("LIC-1")
                .user(new User())
                .build();
        veterinario5.getUser().setEmail("luis.fernandez@vetclinic.com");

        Veterinarian veterinario6 = Veterinarian.builder()
                .firstName("Sofía")
                .lastName("Pérez")
                .specialty("Enfermero")
                .licenseNumber("LIC-1")
                .user(new User())
                .build();
        veterinario6.getUser().setEmail("sofia.perez@vetclinic.com");

        return List.of(veterinario1, veterinario2, veterinario3, veterinario4, veterinario5, veterinario6);
    }
    
    List<Appointment> getAppointments() {
        Veterinarian vet1 = getVeterinarians().getFirst();

        Veterinarian vet2 = getVeterinarians().get(1);

        Veterinarian vet3 = getVeterinarians().get(2);

        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setName("Firulais");
        pet1.setSpecies("Dog");
        pet1.setOwner(new Owner());
        pet1.getOwner().setName("Juan");
        pet1.getOwner().setLastName("Lopez");

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setName("Michi");
        pet2.setSpecies("Cat");
        pet2.setOwner(new Owner());
        pet2.getOwner().setName("Luana");
        pet2.getOwner().setLastName("Lopez");

        return List.of(
                Appointment.builder().id(1L).date(LocalDate.now()).pet(pet1).veterinarian(vet1).status(SCHEDULED).build(),
                Appointment.builder().id(2L).date(LocalDate.now()).pet(pet2).veterinarian(vet1).status(SCHEDULED).build(),
                Appointment.builder().id(3L).date(LocalDate.now()).pet(pet1).veterinarian(vet1).status(SCHEDULED).build(),
                Appointment.builder().id(4L).date(LocalDate.now().plusDays(1)).pet(pet2).veterinarian(vet2).status(CANCELLED).build(),
                Appointment.builder().id(5L).date(LocalDate.now().plusDays(1)).pet(pet1).veterinarian(vet2).status(CANCELLED).build(),
                Appointment.builder().id(6L).date(LocalDate.now().plusDays(2)).pet(pet2).veterinarian(vet2).status(CANCELLED).build(),
                Appointment.builder().id(7L).date(LocalDate.now().plusDays(2)).pet(pet1).veterinarian(vet3).status(CLOSED).build(),
                Appointment.builder().id(8L).date(LocalDate.now().plusDays(3)).pet(pet2).veterinarian(vet3).status(CLOSED).build(),
                Appointment.builder().id(9L).date(LocalDate.now().plusDays(3)).pet(pet1).veterinarian(vet3).status(CLOSED).build()
        );
    }

    List<AppointmentDto> getAppointmentsDto(List<Appointment> appointments) {
        return appointments.stream().map(AppointmentDto::new).toList();
    }
}