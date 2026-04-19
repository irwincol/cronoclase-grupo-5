package com.grupo5.cronoclase.model.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter //ok
@Setter //ok
@NoArgsConstructor //ok
@AllArgsConstructor //ok
@Builder // Patrón Builder para crear objetos fácilmente
@Entity  //ok
@Table(name = "cursos") //ok

//falta el generate value, pero creo que eso es para generar automaticamente el ID

public class Curso extends BaseEntity {

    @Column(nullable = false, length = 100)
    
    private String nombre;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore //Esto se puso para evitar el retorno infinito en el GET
    private List<Grupo> grupos;

}