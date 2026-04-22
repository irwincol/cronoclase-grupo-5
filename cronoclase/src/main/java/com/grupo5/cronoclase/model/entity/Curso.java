package com.grupo5.cronoclase.model.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "cursos")

public class Curso extends BaseEntity {

    @Column(nullable = false, length = 100)
    @JsonManagedReference
    private String nombre;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Grupo> grupos;

}