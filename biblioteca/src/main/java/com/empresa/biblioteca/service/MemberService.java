package com.empresa.biblioteca.service;

import com.empresa.biblioteca.dto.MemberDTO;
import com.empresa.biblioteca.model.Member;
import com.empresa.biblioteca.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MemberService {

    final private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberDTO save(MemberDTO memberDTO) {
        Member member = new Member(memberDTO);
        member = memberRepository.save(member);
        return new MemberDTO(member);
    }

    public Page<MemberDTO> findAll(Pageable pageable) {
        var members = memberRepository.findAll(pageable);
        return members.map(MemberDTO::new);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member Not found"));
    }

    public void delete(Long id) {
        if (memberRepository.findById(id).isEmpty()) throw new NoSuchElementException("Member Not found");
        memberRepository.deleteById(id);
    }

    public MemberDTO update(MemberDTO memberDTO, Long id) {
        if (memberRepository.findById(id).isEmpty()) throw new NoSuchElementException("Member Not found");
        Member member = updateMember(memberDTO, id);
        member = memberRepository.save(member);
        return new MemberDTO(member);
    }

    // metodos de mappeo
    // List<Member> a List<MemberDTO>
    public List<MemberDTO> toDTOList(List<Member> members) {
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : members) {
            var memberDTO = new MemberDTO(member);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    // actualizar miembro
    public Member updateMember(MemberDTO memberDTO, Long memberId) {
        var member = findById(memberId);
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