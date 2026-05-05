package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.TipoEvaluacion;
import java.time.LocalDate;

public record EvaluacionCreateDTO(
    String titulo,
    TipoEvaluacion tipo,
    Double porcentaje,
    LocalDate fechaEntrega,
    Long grupoId
) {}
