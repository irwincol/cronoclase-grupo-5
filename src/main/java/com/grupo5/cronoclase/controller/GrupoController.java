package com.grupo5.cronoclase.controller;


import org.springframework.web.bind.annotation.RestController;

import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.Jornada;
import com.grupo5.cronoclase.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
@RequestMapping("/api/grupo")

public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public Grupo crearGrupo(@RequestBody Grupo grupo) {
        return grupoService.crearGrupo(grupo);
    }

    @PostMapping("/crearVarios")
    public List<Grupo> crearVariosGrupos(@RequestBody List<Grupo> grupos) {
        return grupoService.crearVariosGrupos(grupos);
    }


    @GetMapping
    public List<Grupo> obtenerTodos() {
        return grupoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Grupo obtenerPorId(@PathVariable Long id) {
        return grupoService.obtenerPorId(id);
    }


    @GetMapping("/buscar/{nombre}")
    public List<Grupo> obtenerPorNombre(@PathVariable String nombre) {
        return grupoService.obtenerPorNombre(nombre);
    }

    @GetMapping("/jornada/{jornada}")
    public List<Grupo> obtenerPorJornada(@PathVariable Jornada jornada) {
        return grupoService.obtenerPorJornada(jornada);
    }








}
