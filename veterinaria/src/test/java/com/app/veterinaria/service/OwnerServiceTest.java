package com.app.veterinaria.service;

import com.app.veterinaria.entity.Owner;
import com.app.veterinaria.repository.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    @DisplayName("Listar dueños")
    void findAll() {
        var owners = getOwners();
        var ascRequest = PageRequest.of(0, 10, Direction.ASC, "name");
        var descRequest = PageRequest.of(0, 10, Direction.DESC, "name");

        when(ownerRepository.findAll(ascRequest))
                .thenReturn(new PageImpl<>(owners, ascRequest, owners.size()));

        when(ownerRepository.findAll(descRequest))
                .thenReturn(new PageImpl<>(owners, descRequest, owners.size()));

        var sort = "ASC";
        var expected = new PageImpl<>(owners);
        var result = ownerService.findAll(0, 10, sort);
        assertEquals(expected.getContent(), result.getContent());

        sort = "DESC";
        expected = new PageImpl<>(owners);
        result = ownerService.findAll(0, 10, sort);
        assertEquals(expected.getContent(), result.getContent());

        sort = "asc";
        expected = new PageImpl<>(owners);
        result = ownerService.findAll(0, 10, sort);
        assertEquals(expected.getContent(), result.getContent());

        sort = "desc";
        expected = new PageImpl<>(owners);
        result = ownerService.findAll(0, 10, sort);
        assertEquals(expected.getContent(), result.getContent());

    }

    @Test
    void update() {
        var owner = getOwners().getFirst();

        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any())).thenReturn(owner);

        var updatedOwner = new Owner();
        updatedOwner.setName("Lucas");

        var result = ownerService.update(1L, updatedOwner);
        owner.setName("Lucas");
        assertEquals(owner, result);
    }

    private List<Owner> getOwners(){
        return List.of(
                Owner.builder().id(1L).user(null).name("Juan").lastName("Caceres").build(),
                Owner.builder().id(2L).user(null).name("Andrea").lastName("Lopez").build(),
                Owner.builder().id(3L).user(null).name("Carlos").lastName("Ramirez").build(),
                Owner.builder().id(4L).user(null).name("Diego").lastName("Alonso").build(),
                Owner.builder().id(5L).user(null).name("Jorge").lastName("Martinez").build()
        );
    }
}