package com.grupo5.cronoclase.controller;


import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/api/matricula")

public class MatriculaController {

    @Autowired

    private MatriculaService matriculaService;

    // 1. Crear una sola matrícula
    @PostMapping
    public Matricula crearMatricula(@RequestBody Matricula matricula) {
        return matriculaService.crearMatricula(matricula);
    }

    // 2. Crear varias matrículas al tiempo (POST masivo)
    @PostMapping("/crearVarios")
    public List<Matricula> crearVarios(@RequestBody List<Matricula> matriculas) {
        return matriculaService.crearVariasMatriculas(matriculas);
    }

    // 3. Consultar todas las matrículas
    @GetMapping
    public List<Matricula> obtenerTodas() {
        return matriculaService.obtenerTodas();
    }


    // 4. Consultar por ID de Matrícula
    @GetMapping("/{id}")
    public Matricula obtenerPorId(@PathVariable Long id) {
        return matriculaService.obtenerPorId(id);
    }

    // 5. Consultar matrículas por un Grupo específico
    @GetMapping("/grupo/{grupoId}")
    public List<Matricula> obtenerPorGrupo(@PathVariable Long grupoId) {
        return matriculaService.obtenerPorGrupo(grupoId);
    }

    // 6. Consultar matrículas por un Estudiante específico
    @GetMapping("/estudiante/{estudianteId}")
    public List<Matricula> obtenerPorEstudiante(@PathVariable Long estudianteId) {
        return matriculaService.obtenerPorEstudiante(estudianteId);
    }


}
