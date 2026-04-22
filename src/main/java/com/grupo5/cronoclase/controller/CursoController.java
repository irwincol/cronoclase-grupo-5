package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/api/curso")

public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public Curso crearCrear(@RequestBody Curso curso) {
        return cursoService.crearEstudiante(curso);
    }

    @PostMapping("/crearVarios")
    List<Curso> crearVariosCurso(@RequestBody List<Curso> listaCurso) {
        return cursoService.crearVariosCursos(listaCurso);

    }

    @GetMapping
    public List<Curso> obtenerCurso() {
        return cursoService.obtenerCursos();
    }

    @GetMapping("/buscar/{nombreCurso}")
    public List<Curso> findCursoByNombre(@PathVariable String nombreCurso) {

        return cursoService.findCursoByNombre(nombreCurso);

    }

    @GetMapping("/{CursoID}")
    public Curso obtenerPorId(@PathVariable Long CursoID) {

        return cursoService.obtenerPorId(CursoID);

    }

    // 5. Actualizar (PUT) - EL NUEVO
    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso curso) {
        Curso cursoActualizado = cursoService.actualizarCurso(id, curso);
        return ResponseEntity.ok(cursoActualizado);
    }

    // 6. Eliminar (DELETE) - EL NUEVO
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.ok("Curso con ID " + id + " eliminado con éxito.");
    }

}
