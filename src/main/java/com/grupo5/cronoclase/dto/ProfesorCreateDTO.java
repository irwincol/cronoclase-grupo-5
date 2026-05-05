package com.grupo5.cronoclase.dto;

public record ProfesorCreateDTO(
    String nombre,
    String email,
    String documentoID,
    Boolean activo
) {}
