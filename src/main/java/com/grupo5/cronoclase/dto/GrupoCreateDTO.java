package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.DiaSemana;
import com.grupo5.cronoclase.model.enums.Jornada;

public record GrupoCreateDTO(
    String nombre,
    DiaSemana dia,
    Jornada jornada,
    Long cursoId,
    Long profesorId
) {}
