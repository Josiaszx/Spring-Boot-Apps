package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.MemberDTO;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    Endpoints a implementar
        1 - POST api/members/ ... agregar meimbro
        2 - GET api/members/ ... mostrar los miembros paginados
        3 - GET api/members/id ... mostrar segun id
        4 - DELELTE api/members/id ... eliminar
        5 - PUT api/members/id ... actualizar
        6 - GET api/members/id/loans ... mostrar prestamos de un miembro
*/

@RestController
@RequestMapping("/api/members")
public class MemberController {

    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 1 - POST api/members/ ... agregar meimbro
    @PostMapping
    public MemberDTO save(@Valid @RequestBody MemberDTO memberDTO) {
        return memberService.save(memberDTO);
    }

    // 2 - GET api/members/ ... mostrar los miembros paginados
    @GetMapping
    public Page<MemberDTO> findAll(
            @RequestParam(defaultValue = "0") int page, // por defecto la pagina retornada cera la 0
            @RequestParam(defaultValue = "10") int size // la cantidad de elementos retornada por defecto sera 10
    ) {
        // creamos la peticion con en numero de pagina y la cantidad de elementos de cada pagina
        Pageable pageable = PageRequest.of(page, size);
        return memberService.findAll(pageable);
    }

    // 3 - GET api/members/id ... mostrar segun id
    @GetMapping("/{id}")
    public MemberDTO findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    // 4 - DELELTE api/members/id ... eliminar
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }

    // 5 - PUT api/members/id ... actualizar
    @PutMapping("/{id}")
    public MemberDTO update(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        return memberService.update(memberDTO, id);
    }

    // 6 - GET api/members/id/loans ... mostrar prestamos de un miembro
    @GetMapping("{id}/loans")
    public List<LoanDTO> findAllMemberLoans(@PathVariable Long id) {
        return memberService.findAllMemberLoans(id);
    }
}
