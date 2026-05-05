package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.ProfesorDTO;
import com.grupo5.cronoclase.dto.ProfesorCreateDTO;
import com.grupo5.cronoclase.service.ProfesorService;

import java.util.List;

@RestController
@RequestMapping("/api/profesor")
@Tag(name = "Profesor", description = "Gestión de profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @Operation(summary = "Crear un profesor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfesorDTO crearProfesor(@RequestBody ProfesorCreateDTO profesor) {
        return profesorService.crearProfesor(profesor);
    }

    @Operation(summary = "Crear varios profesores")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProfesorDTO> crearVariosProfesores(@RequestBody List<ProfesorCreateDTO> listaProfesores) {
        return profesorService.crearVariosProfesores(listaProfesores);
    }
    
    @Operation(summary = "Obtener todos los profesores")
    @GetMapping
    public List<ProfesorDTO> obtenerProfesores() {
        return profesorService.obtenerProfesores();
    }

    @Operation(summary = "Buscar profesores por nombre")
    @GetMapping("/buscar/{nombreProfesor}")
    public List<ProfesorDTO> findProfesorByNombre(@PathVariable String nombreProfesor) {
        return profesorService.findProfesorByNombre(nombreProfesor);
    }

    @Operation(summary = "Obtener un profesor por ID")
    @GetMapping("/{profesorID}")
    public ProfesorDTO obtenerPorId(@PathVariable Long profesorID) {
        return profesorService.obtenerPorId(profesorID);
    }

    @Operation(summary = "Actualizar un profesor")
    @PutMapping("/{id}")
    public ProfesorDTO actualizarProfesor(@PathVariable Long id, @RequestBody ProfesorCreateDTO profesor) {
        return profesorService.actualizarProfesor(id, profesor);
    }

    @Operation(summary = "Eliminar un profesor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProfesor(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
    }
}
