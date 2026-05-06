package com.grupo5.cronoclase.dtos;

import jakarta.persistence.Column;

public class EstudianteResponseDTO {

     
    private String nombre;

    private String email;

    // 1. Necesitas un constructor vacío (obligatorio para frameworks)
    public EstudianteResponseDTO() {}

    // 2. Necesitas un constructor con parámetros (opcional pero muy útil)
    public EstudianteResponseDTO(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // 3. ¡MUY IMPORTANTE!: Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



}
