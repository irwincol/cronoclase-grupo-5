package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.EstudianteDTO;
import com.grupo5.cronoclase.dto.EstudianteCreateDTO;
import com.grupo5.cronoclase.service.EstudianteService;

import java.util.List;

@RestController
@RequestMapping("/api/estudiante")
@Tag(name = "Estudiante", description = "Gestión de estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @Operation(summary = "Crear un estudiante")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstudianteDTO crearEstudiante(@RequestBody EstudianteCreateDTO estudiante) {
        return estudianteService.crearEstudiante(estudiante);
    }

    @Operation(summary = "Crear varios estudiantes")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EstudianteDTO> crearVariosEstudiantes(@RequestBody List<EstudianteCreateDTO> listaEstudiantes) {
        return estudianteService.crearVariosEstudiantes(listaEstudiantes);
    }

    @Operation(summary = "Obtener todos los estudiantes")
    @GetMapping
    public List<EstudianteDTO> obtenerEstudiantes() {
        return estudianteService.obtenerEstudiantes();
    }

    @Operation(summary = "Buscar estudiante por documento")
    @GetMapping("/documento/{documentoID}")
    public EstudianteDTO findEstudianteByDocumento(@PathVariable String documentoID) {
        return estudianteService.findEstudianteByDocumento(documentoID);
    }

    @Operation(summary = "Buscar estudiantes por nombre")
    @GetMapping("/nombre/{nombreEstudiante}")
    public List<EstudianteDTO> findEstudianteByNombre(@PathVariable String nombreEstudiante) {
        return estudianteService.findEstudianteByNombre(nombreEstudiante);
    }

    @Operation(summary = "Obtener un estudiante por ID")
    @GetMapping("/{estudianteID}")
    public EstudianteDTO obtenerPorId(@PathVariable Long estudianteID) {
        return estudianteService.obtenerPorId(estudianteID);
    }

    @Operation(summary = "Actualizar un estudiante")
    @PutMapping("/{estudianteID}")
    public EstudianteDTO actualizarEstudiante(@PathVariable Long estudianteID, @RequestBody EstudianteCreateDTO estudiante) {
        return estudianteService.actualizarEstudiante(estudianteID, estudiante);
    }

    @Operation(summary = "Eliminar un estudiante")
    @DeleteMapping("/{estudianteID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEstudiante(@PathVariable Long estudianteID) {
        estudianteService.eliminarEstudiante(estudianteID);
    }
}
