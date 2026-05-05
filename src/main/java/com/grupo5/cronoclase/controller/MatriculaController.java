package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.MatriculaDTO;
import com.grupo5.cronoclase.dto.MatriculaCreateDTO;
import com.grupo5.cronoclase.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/matricula")
@Tag(name = "Matrícula", description = "Gestión de matrículas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Operation(summary = "Crear una matrícula")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaDTO crearMatricula(@RequestBody MatriculaCreateDTO matricula) {
        return matriculaService.crearMatricula(matricula);
    }

    @Operation(summary = "Crear varias matrículas")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MatriculaDTO> crearVarios(@RequestBody List<MatriculaCreateDTO> matriculas) {
        return matriculaService.crearVariasMatriculas(matriculas);
    }

    @Operation(summary = "Obtener todas las matrículas")
    @GetMapping
    public List<MatriculaDTO> obtenerTodas() {
        return matriculaService.obtenerTodas();
    }

    @Operation(summary = "Obtener una matrícula por ID")
    @GetMapping("/{id}")
    public MatriculaDTO obtenerPorId(@PathVariable Long id) {
        return matriculaService.obtenerPorId(id);
    }

    @Operation(summary = "Obtener matrículas por grupo")
    @GetMapping("/grupo/{grupoId}")
    public List<MatriculaDTO> obtenerPorGrupo(@PathVariable Long grupoId) {
        return matriculaService.obtenerPorGrupo(grupoId);
    }

    @Operation(summary = "Obtener matrículas por estudiante")
    @GetMapping("/estudiante/{estudianteId}")
    public List<MatriculaDTO> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return matriculaService.obtenerPorEstudiante(estudianteId);
    }

    @Operation(summary = "Actualizar una matrícula")
    @PutMapping("/{id}")
    public MatriculaDTO actualizarMatricula(@PathVariable Long id, @RequestBody MatriculaCreateDTO matricula) {
        return matriculaService.actualizarMatricula(id, matricula);
    }

    @Operation(summary = "Eliminar una matrícula")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarMatricula(@PathVariable Long id) {
        matriculaService.eliminarMatricula(id);
    }
}
