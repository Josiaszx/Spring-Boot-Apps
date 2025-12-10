package com.empresa.biblioteca.controller;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.MemberDTO;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    final private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public MemberDTO save(@RequestBody MemberDTO memberDTO) {
        return memberService.save(memberDTO);
    }

    @GetMapping
    public Page<MemberDTO> findAll(
            @RequestParam(defaultValue = "0") int page, // por defecto la pagina retornada cera la 0
            @RequestParam(defaultValue = "10") int size // la cantidad de elementos retornada por defecto sera 10
    ) {
        // creamos la peticion con en numero de pagina y la cantidad de elementos de cada pagina
        Pageable pageable = PageRequest.of(page, size);
        return memberService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public MemberDTO findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }

    @PutMapping("/{id}")
    public MemberDTO update(@PathVariable Long id, @RequestBody MemberDTO memberDTO) {
        return memberService.update(memberDTO, id);
    }

    @GetMapping("{id}/loans")
    public List<LoanDTO> findAllMemberLoans(@PathVariable Long id) {
        return memberService.findAllMemberLoans(id);
    }
}
