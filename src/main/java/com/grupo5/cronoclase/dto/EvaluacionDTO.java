package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.TipoEvaluacion;
import java.time.LocalDate;

public record EvaluacionDTO(
    Long id,
    String titulo,
    TipoEvaluacion tipo,
    Double porcentaje,
    LocalDate fechaEntrega,
    String grupoNombre
) {}
