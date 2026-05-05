package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.EntregaDTO;
import com.grupo5.cronoclase.dto.EntregaCreateDTO;
import com.grupo5.cronoclase.service.EntregaService;

import java.util.List;

@RestController
@RequestMapping("/api/entrega")
@Tag(name = "Entrega", description = "Gestión de entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Operation(summary = "Crear una entrega")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaDTO crearEntrega(@RequestBody EntregaCreateDTO entrega) {
        return entregaService.crearEntrega(entrega);
    }

    @Operation(summary = "Crear varias entregas")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EntregaDTO> crearVariasEntregas(@RequestBody List<EntregaCreateDTO> listaEntregas) {
        return entregaService.crearVariasEntregas(listaEntregas);
    }

    @Operation(summary = "Obtener todas las entregas")
    @GetMapping
    public List<EntregaDTO> obtenerEntregas() {
        return entregaService.obtenerEntregas();
    }

    @Operation(summary = "Obtener entregas por estudiante")
    @GetMapping("/estudianteID/{estudianteID}")
    public List<EntregaDTO> findEntregaByEstudianteId(@PathVariable Long estudianteID) {
        return entregaService.findEntregaByEstudianteId(estudianteID);
    }

    @Operation(summary = "Obtener entregas por evaluación")
    @GetMapping("/evaluacionID/{evaluacionID}")
    public List<EntregaDTO> findEntregaByEvaluacionId(@PathVariable Long evaluacionID) {
        return entregaService.findEntregaByEvaluacionId(evaluacionID);
    }

    @Operation(summary = "Buscar entregas por nombre de estudiante")
    @GetMapping("/nombreEstudiante/{nombreEstudiante}")
    public List<EntregaDTO> findEntregaByNombreEstudiante(@PathVariable String nombreEstudiante) {
        return entregaService.findEntregaByNombreEstudiante(nombreEstudiante);
    }

    @Operation(summary = "Obtener una entrega por ID")
    @GetMapping("/{entregaID}")
    public EntregaDTO obtenerPorId(@PathVariable Long entregaID) {
        return entregaService.obtenerPorId(entregaID);
    }

    @Operation(summary = "Actualizar una entrega")
    @PutMapping("/{id}")
    public EntregaDTO actualizarEntrega(@PathVariable Long id, @RequestBody EntregaCreateDTO entrega) {
        return entregaService.actualizarEntrega(id, entrega);
    }

    @Operation(summary = "Calificar una entrega")
    @PatchMapping("/{id}/calificar")
    public EntregaDTO calificarEntrega(@PathVariable Long id) {
        return entregaService.calificarEntrega(id);
    }

    @Operation(summary = "Actualizar el estado de todas las entregas")
    @PutMapping("/actualizar-estados")
    public ResponseEntity<String> actualizarEstadosTodas() {
        entregaService.actualizarEstadosTodas();
        return ResponseEntity.ok("Estados actualizados correctamente.");
    }

    @Operation(summary = "Eliminar una entrega")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEntrega(@PathVariable Long id) {
        entregaService.eliminarEntrega(id);
    }
}
