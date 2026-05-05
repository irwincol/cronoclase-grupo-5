package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.EstadoEntrega;
import java.time.LocalDate;

public record EntregaCreateDTO(
    LocalDate fechaEntregaReal,
    EstadoEntrega estado,
    String archivoUrl,
    String comentario,
    Long estudianteId,
    Long evaluacionId
) {}
