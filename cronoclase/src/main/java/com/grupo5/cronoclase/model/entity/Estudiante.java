package com.grupo5.cronoclase.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Patrón Builder para crear objetos fácilmente
@Entity
@Table(name = "estudiantes")

public class Estudiante extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Matricula> matriculas;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Entrega> entregas;

}
