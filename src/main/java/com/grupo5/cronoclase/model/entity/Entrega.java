package com.grupo5.cronoclase.model.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.grupo5.cronoclase.model.enums.*;

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
@Table(name = "entregas")

public class Entrega extends BaseEntity {
    @Column(nullable = true)
    private LocalDate fechaEntregaReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    //PONER ESTO EN PENDIENTE POR DEFAULT
    private EstadoEntrega estado;

    @Column(length = 300)
    private String archivoUrl;

    @Column(length = 500)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY para rendimiento
    @JoinColumn(name = "estudiante_id", nullable = false)

    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY para rendimiento
    @JoinColumn(name = "evaluacion_id", nullable = false)

    private Evaluacion evaluacion;

}
