package com.api.universidad.repository;

import com.api.universidad.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByDepartamentoId(Long departamentoId);
    boolean existsByCodigo(String codigo);
    boolean existsByEmail(String email);
}
