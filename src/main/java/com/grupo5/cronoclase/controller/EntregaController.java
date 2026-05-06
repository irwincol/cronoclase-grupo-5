package com.grupo5.cronoclase.controller;





import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.TipoEvaluacion;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
@RequestMapping("/api/entrega")

public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    public Entrega crearEntrega(@RequestBody Entrega entrega) {
        return entregaService.crearEntrega(entrega);
    }

    @PostMapping("/crearVarios")
    public List<Entrega> crearVariasEntregas(@RequestBody List<Entrega> listaEntregas) {
        return entregaService.crearVariasEntregas(listaEntregas);
    }

    @GetMapping
    public List<Entrega> obtenerEntregas() {
        return entregaService.obtenerEntregas();
    }


    @GetMapping("/estudianteID/{estudianteID}")
    public List<Entrega> findEntregaByEstudianteId(@PathVariable Long estudianteID) {
        return entregaService.findEntregaByEstudianteId(estudianteID);
    }


    @GetMapping("/evaluacionID/{evaluacionID}")
    public List<Entrega> findEntregaByEvaluacionId(@PathVariable Long evaluacionID) {
        return entregaService.findEntregaByEvaluacionId(evaluacionID);
    }


    @GetMapping("/nombreEstudiante/{nombreEstudiante}")
    public List<Entrega> findEntregaByNombreEstudiante(@PathVariable String nombreEstudiante) {
        return entregaService.findEntregaByNombreEstudiante(nombreEstudiante);
    }

   

    @GetMapping("/{entregaID}")
    public Entrega obtenerPorId(@PathVariable Long entregaID) {
        return entregaService.obtenerPorId(entregaID);
    }


    // Endpoint para actualizar una entrega (PUT)
    @PutMapping("/{id}")
    public Entrega actualizarEntrega(@PathVariable Long id, @RequestBody Entrega entrega) {
        return entregaService.actualizarEntrega(id, entrega);
    }

    // Endpoint para marcar una entrega como CALIFICADO (PATCH)
    @PatchMapping("/{id}/calificar")
    public Entrega calificarEntrega(@PathVariable Long id) {
        return entregaService.calificarEntrega(id);
    }

    // Endpoint para recalcular el estado de todas las entregas PENDIENTES (PUT)
    @PutMapping("/actualizar-estados")
    public ResponseEntity<String> actualizarEstadosTodas() {
        entregaService.actualizarEstadosTodas();
        return ResponseEntity.ok("Estados actualizados correctamente.");
    }

    // Endpoint para eliminar una entrega (DELETE)
    @DeleteMapping("/{id}")
    public String eliminarEntrega(@PathVariable Long id) {
        entregaService.eliminarEntrega(id);
        return "Entrega con ID " + id + " eliminada exitosamente.";
    }




}
