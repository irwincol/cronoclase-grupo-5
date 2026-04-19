package com.grupo5.cronoclase.controller;




import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/api/evaluacion")

public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @PostMapping
    public Evaluacion crearEvaluacion(@RequestBody Evaluacion evaluacion) {
        return evaluacionService.crearEvaluacion(evaluacion);
    }

    @PostMapping("/crearVarios")
    public List<Evaluacion> crearVariasEvaluaciones(@RequestBody List<Evaluacion> listaEvaluaciones) {
        return evaluacionService.crearVariasEvaluaciones(listaEvaluaciones);
    }

    @GetMapping
    public List<Evaluacion> obtenerEvaluaciones() {
        return evaluacionService.obtenerEvaluaciones();
    }

    @GetMapping("/grupo/{grupoId}")
    public List<Evaluacion> findEvaluacionByGrupoId(@PathVariable Long grupoId) {
        return evaluacionService.findEvaluacionByGrupoId(grupoId);
    }

    @GetMapping("/grupo/nombre/{nombreGrupo}")
    public List<Evaluacion> findEvaluacionByNombreGrupo(@PathVariable String nombreGrupo) {
        return evaluacionService.findEvaluacionByNombreGrupo(nombreGrupo);
    }

    @GetMapping("/{evaluacionID}")
    public Evaluacion obtenerPorId(@PathVariable Long evaluacionID) {
        return evaluacionService.obtenerPorId(evaluacionID);
    }

    @PutMapping("/{id}")
    public Evaluacion actualizarEvaluacion(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        // Llama al service que setea: titulo, tipo, porcentaje y fechaEntrega
        return evaluacionService.actualizarEvaluacion(id, evaluacion);
    }

    // Endpoint para eliminar una evaluación (DELETE)
    @DeleteMapping("/{id}")
    public String eliminarEvaluacion(@PathVariable Long id) {
        evaluacionService.eliminarEvaluacion(id);
        return "La evaluación con ID " + id + " ha sido eliminada (junto con sus entregas asociadas).";
    }

}
