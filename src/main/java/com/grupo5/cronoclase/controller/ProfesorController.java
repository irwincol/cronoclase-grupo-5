package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/api/profesor")

public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @PostMapping
    public Profesor crearProfesor(@RequestBody Profesor profesor) {
        return profesorService.crearProfesor(profesor);
    }

    @PostMapping("/crearVarios")
    List<Profesor> crearVariosProfesores(@RequestBody List<Profesor>  listaProfesores) {
        return profesorService.crearVariosProfesores(listaProfesores);

    }


    
    @GetMapping
    public List<Profesor>  obtenerProfesores() {
        return profesorService.obtenerProfesores();
    }


    @GetMapping("/buscar/{nombreProfesor}")
    public List<Profesor> findProfesorByNombre(@PathVariable String nombreProfesor) {

        return profesorService.findProfesorByNombre(nombreProfesor);

    }

    @GetMapping("/{profesorID}")
    public Profesor obtenerPorId(@PathVariable Long profesorID) {
        return profesorService.obtenerPorId(profesorID);
    }

    // 5. Actualizar un profesor por ID
    @PutMapping("/{id}")
    public Profesor actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        return profesorService.actualizarProfesor(id, profesor);
    }

    // 6. Eliminar un profesor por ID
    @DeleteMapping("/{id}")
    public void eliminarProfesor(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
    }



}
