package com.empresa.biblioteca.repository;

import com.empresa.biblioteca.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
