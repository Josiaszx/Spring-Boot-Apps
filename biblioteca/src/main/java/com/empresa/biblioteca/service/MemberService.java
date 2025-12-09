package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.LoanDTO;
import com.empresa.biblioteca.dto.MemberDTO;
import com.empresa.biblioteca.model.Loan;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.repository.LoanRepository;
import com.empresa.biblioteca.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {

    final private MemberRepository memberRepository;
    final private LoanRepository loanRepository;
    final private LoanService loanService;
    public MemberService(
            MemberRepository memberRepository,
            LoanRepository loanRepository,
            LoanService loanService
    ) {
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;
        this.loanService = loanService;
    }

    // agregar nuevo miembro
    public MemberDTO save(MemberDTO memberDTO) {
        Member member = toEntity(memberDTO);
        member = memberRepository.save(member);
        return toDTO(member);
    }

    // mostrar todos los miembros
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();
        return toDTOList(members);
    }

    // mostrar miembro por id
    public MemberDTO findById(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        return toDTO(member);
    }

    // eliminar miembro
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    // modificar miembro
    public MemberDTO update(MemberDTO memberDTO, Long id) {
        Member member = updateMember(memberDTO, id);
        member = memberRepository.save(member);
        return toDTO(member);
    }

    // mostrar prestamos de un miembro
    public List<LoanDTO> findAllMemberLoans(Long idUser) {
        var member = memberRepository.findById(idUser).orElseThrow(IllegalStateException::new);
        var memberLoans = loanRepository.findAllByMemberIs(member);
        return loanService.toDTOList(memberLoans);
    }

    // metodos de mappeo
    // MemberDTO a Member
    public Member toEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setFirstName(memberDTO.getFirstName());
        member.setLastName(memberDTO.getLastName());
        member.setEmail(memberDTO.getEmail());
        member.setPhone(memberDTO.getPhone());
        member.setActive(memberDTO.isActive());
        member.setRegistrationDate(memberDTO.getRegistrationDate());
        member.setMemberShipNumber(memberDTO.getMemberShipNumber());
        return member;
    }

    // Member a MemberDTO
    public MemberDTO toDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setFirstName(member.getFirstName());
        memberDTO.setLastName(member.getLastName());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setPhone(member.getPhone());
        memberDTO.setActive(member.isActive());
        memberDTO.setRegistrationDate(member.getRegistrationDate());
        memberDTO.setMemberShipNumber(member.getMemberShipNumber());
        return memberDTO;
    }

    // List<Member> a List<MemberDTO>
    public List<MemberDTO> toDTOList(List<Member> members) {
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : members) {
            memberDTOList.add(toDTO(member));
        }
        return memberDTOList;
    }

    // actualizar miembro
    public Member updateMember(MemberDTO memberDTO, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        if (memberDTO.getFirstName() != null) member.setFirstName(memberDTO.getFirstName());
        if (memberDTO.getLastName() != null) member.setLastName(memberDTO.getLastName());
        if (memberDTO.getEmail() != null) member.setEmail(memberDTO.getEmail());
        if (memberDTO.getPhone() != null) member.setPhone(memberDTO.getPhone());
        if(memberDTO.isActive() != member.isActive()) member.setActive(memberDTO.isActive());
        if (memberDTO.getRegistrationDate() != null) member.setRegistrationDate(memberDTO.getRegistrationDate());
        if (memberDTO.getMemberShipNumber() != null) member.setMemberShipNumber(memberDTO.getMemberShipNumber());
        return member;
    }

}
