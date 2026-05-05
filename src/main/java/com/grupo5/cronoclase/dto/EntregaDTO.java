package com.grupo5.cronoclase.dto;

import com.grupo5.cronoclase.model.enums.EstadoEntrega;
import java.time.LocalDate;

public record EntregaDTO(
    Long id,
    LocalDate fechaEntregaReal,
    EstadoEntrega estado,
    String archivoUrl,
    String comentario,
    String estudianteNombre,
    String evaluacionTitulo
) {}
