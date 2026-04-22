package com.grupo5.cronoclase.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Patrón Builder para crear objetos fácilmente

@Entity
@Table(name = "profesores")

public class Profesor extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String documentoID;

    @Column(nullable = true)
   
    private Boolean activo;

    @OneToMany(mappedBy = "profesor")
    @JsonIgnore //Esto se puso para evitar el retorno infinito en el GET
    private List<Grupo> grupos;

}
