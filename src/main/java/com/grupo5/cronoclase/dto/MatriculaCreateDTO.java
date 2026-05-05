package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.EstadoMatricula;

public record MatriculaCreateDTO(
    EstadoMatricula estadoMatricula,
    Long estudianteId,
    Long grupoId
) {}
