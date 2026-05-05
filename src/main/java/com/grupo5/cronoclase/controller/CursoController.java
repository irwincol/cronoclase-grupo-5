package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.CursoDTO;
import com.grupo5.cronoclase.dto.CursoCreateDTO;
import com.grupo5.cronoclase.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/curso")
@Tag(name = "Curso", description = "Gestión de cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Crear un curso")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CursoDTO crearCurso(@RequestBody CursoCreateDTO curso) {
        return cursoService.crearCurso(curso);
    }

    @Operation(summary = "Crear varios cursos")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CursoDTO> crearVariosCursos(@RequestBody List<CursoCreateDTO> listaCurso) {
        return cursoService.crearVariosCursos(listaCurso);
    }

    @Operation(summary = "Obtener todos los cursos")
    @GetMapping
    public List<CursoDTO> obtenerCursos() {
        return cursoService.obtenerCursos();
    }

    @Operation(summary = "Buscar cursos por nombre")
    @GetMapping("/buscar/{nombreCurso}")
    public List<CursoDTO> findCursoByNombre(@PathVariable String nombreCurso) {
        return cursoService.findCursoByNombre(nombreCurso);
    }

    @Operation(summary = "Obtener un curso por ID")
    @GetMapping("/{CursoID}")
    public CursoDTO obtenerPorId(@PathVariable Long CursoID) {
        return cursoService.obtenerPorId(CursoID);
    }

    @Operation(summary = "Actualizar un curso")
    @PutMapping("/{id}")
    public CursoDTO actualizar(@PathVariable Long id, @RequestBody CursoCreateDTO curso) {
        return cursoService.actualizarCurso(id, curso);
    }

    @Operation(summary = "Eliminar un curso")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
    }
}
