package com.api.universidad.model;

import com.api.universidad.dto.ProfesorDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profesor {

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

    private String especialidad;

    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Curso> cursos;

    public Profesor(ProfesorDTO dto, Departamento departamento) {
        this.nombre = dto.getNombre();
        this.apellido = dto.getApellido();
        this.codigo = dto.getCodigo();
        this.email = dto.getEmail();
        this.especialidad = dto.getEspecialidad();
        this.fechaContratacion = dto.getFechaContratacion();
        this.departamento = departamento;
    }
}
