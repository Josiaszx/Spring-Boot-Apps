package com.api.universidad.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "asignaturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String descripcion;

    private Integer creditosRequeridos;

    @ManyToMany(mappedBy = "prerequisitos")
    private List<Curso> cursos;
}
