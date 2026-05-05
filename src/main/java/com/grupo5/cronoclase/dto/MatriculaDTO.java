package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.EstadoMatricula;

public record MatriculaDTO(
    Long id,
    EstadoMatricula estadoMatricula,
    String estudianteNombre,
    String grupoNombre
) {}
