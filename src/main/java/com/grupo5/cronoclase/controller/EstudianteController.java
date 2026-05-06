package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.dtos.EstudianteResponseDTO;
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
@RequestMapping("/api/estudiante")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @PostMapping
    public Estudiante crearEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.crearEstudiante(estudiante);
    }

    @PostMapping("/crearVarios")
    List<Estudiante> crearVariosEstudiantes(@RequestBody List<Estudiante> listaEstudiantes) {
        return estudianteService.crearVariosEstudiantes(listaEstudiantes);

    }

    @GetMapping
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteService.obtenerEstudiantes();
    }

    @GetMapping("/documento/{documentoID}")
    public Estudiante findEstudianteByDocumento(@PathVariable String documentoID) {

        return estudianteService.findEstudianteByDocumento(documentoID);

    }

    @GetMapping("/nombre/{nombreEstudiante}")

    public List<Estudiante> findEstudianteByNombre(@PathVariable String nombreEstudiante) {

        return estudianteService.findEstudianteByNombre(nombreEstudiante);

    }

    @GetMapping("/{estudianteID}")

    public EstudianteResponseDTO obtenerPorId(@PathVariable Long estudianteID) {

        return estudianteService.obtenerPorId(estudianteID);

    }

    // Endpoint para actualizar un estudiante (PUT)
    @PutMapping("/{estudianteID}")
    public Estudiante actualizarEstudiante(@PathVariable Long estudianteID, @RequestBody Estudiante estudiante) {
        return estudianteService.actualizarEstudiante(estudianteID, estudiante);
    }

    // Endpoint para eliminar un estudiante (DELETE)
    @DeleteMapping("/{estudianteID}")
    public String eliminarEstudiante(@PathVariable Long estudianteID) {
        estudianteService.eliminarEstudiante(estudianteID);
        return "Estudiante con ID " + estudianteID + " y sus registros asociados han sido eliminados correctamente.";
    }

}
