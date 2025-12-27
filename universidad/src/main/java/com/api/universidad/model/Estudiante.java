package com.api.universidad.model;

import jakarta.persistence.*;
import lombok.*;
import com.api.universidad.dto.PostEstudianteDTO;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "estudiantes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String codigo;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    private String carrera;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "perfil_academico_id", referencedColumnName = "id")
    private PerfilAcademico perfilAcademico;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas;

    public Estudiante(PostEstudianteDTO dto) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.codigo = dto.getCodigo();
        this.email = dto.getEmail();
        this.fechaIngreso = dto.getFechaIngreso() == null ? LocalDate.now() : dto.getFechaIngreso();
        this.carrera = dto.getCarrera();
        this.perfilAcademico = new PerfilAcademico(dto);
    }
}
