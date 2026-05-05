package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.EvaluacionDTO;
import com.grupo5.cronoclase.dto.EvaluacionCreateDTO;
import com.grupo5.cronoclase.service.EvaluacionService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacion")
@Tag(name = "Evaluación", description = "Gestión de evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Operation(summary = "Crear una evaluación")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvaluacionDTO crearEvaluacion(@RequestBody EvaluacionCreateDTO evaluacion) {
        return evaluacionService.crearEvaluacion(evaluacion);
    }

    @Operation(summary = "Crear varias evaluaciones")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EvaluacionDTO> crearVariasEvaluaciones(@RequestBody List<EvaluacionCreateDTO> listaEvaluaciones) {
        return evaluacionService.crearVariasEvaluaciones(listaEvaluaciones);
    }

    @Operation(summary = "Obtener todas las evaluaciones")
    @GetMapping
    public List<EvaluacionDTO> obtenerEvaluaciones() {
        return evaluacionService.obtenerEvaluaciones();
    }

    @Operation(summary = "Obtener evaluaciones por grupo")
    @GetMapping("/grupo/{grupoId}")
    public List<EvaluacionDTO> findEvaluacionByGrupoId(@PathVariable Long grupoId) {
        return evaluacionService.findEvaluacionByGrupoId(grupoId);
    }

    @Operation(summary = "Obtener evaluaciones por nombre de grupo")
    @GetMapping("/grupo/nombre/{nombreGrupo}")
    public List<EvaluacionDTO> findEvaluacionByNombreGrupo(@PathVariable String nombreGrupo) {
        return evaluacionService.findEvaluacionByNombreGrupo(nombreGrupo);
    }

    @Operation(summary = "Obtener una evaluación por ID")
    @GetMapping("/{evaluacionID}")
    public EvaluacionDTO obtenerPorId(@PathVariable Long evaluacionID) {
        return evaluacionService.obtenerPorId(evaluacionID);
    }

    @Operation(summary = "Actualizar una evaluación")
    @PutMapping("/{id}")
    public EvaluacionDTO actualizarEvaluacion(@PathVariable Long id, @RequestBody EvaluacionCreateDTO evaluacion) {
        return evaluacionService.actualizarEvaluacion(id, evaluacion);
    }

    @Operation(summary = "Eliminar una evaluación")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEvaluacion(@PathVariable Long id) {
        evaluacionService.eliminarEvaluacion(id);
    }
}
