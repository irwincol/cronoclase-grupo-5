package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.DiaSemana;
import com.grupo5.cronoclase.model.enums.Jornada;

public record GrupoDTO(
    Long id,
    String nombre,
    DiaSemana dia,
    Jornada jornada,
    String cursoNombre,
    String profesorNombre
) {}
