package com.grupo5.cronoclase.model.entity;

import com.grupo5.cronoclase.model.enums.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Patrón Builder para crear objetos fácilmente
@Entity
@Table(name = "grupos")

public class Grupo extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana dia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Jornada jornada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonBackReference
    private Curso curso;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Matricula> matriculas;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

}
