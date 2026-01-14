package com.app.veterinaria.service;

import com.app.veterinaria.dto.NewOwnerRequest;
import com.app.veterinaria.entity.Role;
import com.app.veterinaria.entity.User;
import com.app.veterinaria.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;


    Role roleAdmin = new Role("ADMIN");
    Role roleOwner = new Role("OWNER");
    Role roleVeterinarian = new Role("VETERINARIAN");

    User expectedUser = User.builder()
            .id(null)
            .username("Juanp")
            .password("j123")
            .email("juan@gmail.com")
            .role(roleOwner)
            .active(true)
            .createdAt(LocalDate.now())
            .build();

    NewOwnerRequest request = new NewOwnerRequest();

    @BeforeEach
    void setUp() {
        request.setUsername("Juanp");
        request.setPassword("j123");
        request.setCreatedAt(LocalDate.now());
        request.setAddress("Asuncion");
        request.setPhoneNumber("0923");
        request.setLastName("Perez");
        request.setFirstName("Juan");
        request.setActive(true);
        request.setEmail("juan@gmail.com");
        request.setRole("OWNER");
    }

    @Test
    void createUserFromRequest() {

        when(roleService.findByRoleName("OWNER")).thenReturn(roleOwner);
        when(roleService.findByRoleName("ADMIN")).thenReturn(roleAdmin);
        when(roleService.findByRoleName("VETERINARIAN")).thenReturn(roleVeterinarian);
        when(roleService.findByRoleName("USER")).thenThrow( new RuntimeException());

        // crear nuevo usuario de tipo OWNER
        User resultedUser = userService.createUserFromRequest(request);
        assertEquals(expectedUser, resultedUser);

        // crear nuevo usuario de tipo VETERINARIAN
        request.setRole("VETERINARIAN");
        expectedUser.setRole(roleVeterinarian);
        resultedUser = userService.createUserFromRequest(request);
        assertEquals(expectedUser, resultedUser);

        // crear nuevo usuario de tipo ADMIN
        request.setRole("ADMIN");
        expectedUser.setRole(roleAdmin);
        resultedUser = userService.createUserFromRequest(request);
        assertEquals(expectedUser, resultedUser);

        // crear nuevo usuario de tipo OWNER
        // se espera que al pasar una request con rol null, se asuma que se trata de un usuario de tipo OWNER
        request.setRole(null);
        expectedUser.setRole(roleOwner);
        resultedUser = userService.createUserFromRequest(request);
        assertEquals(expectedUser, resultedUser);

        // esperar error si se intenta crear un usuario con rol diferente a los definidos
        request.setRole("USER");
        assertThrows(RuntimeException.class, () -> userService.createUserFromRequest(request));

    }

    @Test
    void createAndSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        User resultedUser = userService.createAndSaveUser(request);
        expectedUser.setId(resultedUser.getId());
        assertEquals(expectedUser, resultedUser);
    }

}