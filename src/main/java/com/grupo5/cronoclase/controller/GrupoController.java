package com.grupo5.cronoclase.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.grupo5.cronoclase.dto.GrupoDTO;
import com.grupo5.cronoclase.dto.GrupoCreateDTO;
import com.grupo5.cronoclase.model.enums.Jornada;
import com.grupo5.cronoclase.service.GrupoService;

import java.util.List;

@RestController
@RequestMapping("/api/grupo")
@Tag(name = "Grupo", description = "Gestión de grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Operation(summary = "Crear un grupo")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO crearGrupo(@RequestBody GrupoCreateDTO grupo) {
        return grupoService.crearGrupo(grupo);
    }

    @Operation(summary = "Crear varios grupos")
    @PostMapping("/crearVarios")
    @ResponseStatus(HttpStatus.CREATED)
    public List<GrupoDTO> crearVariosGrupos(@RequestBody List<GrupoCreateDTO> grupos) {
        return grupoService.crearVariosGrupos(grupos);
    }

    @Operation(summary = "Obtener todos los grupos")
    @GetMapping
    public List<GrupoDTO> obtenerTodos() {
        return grupoService.obtenerTodos();
    }

    @Operation(summary = "Obtener un grupo por ID")
    @GetMapping("/{id}")
    public GrupoDTO obtenerPorId(@PathVariable Long id) {
        return grupoService.obtenerPorId(id);
    }

    @Operation(summary = "Buscar grupos por nombre")
    @GetMapping("/buscar/{nombre}")
    public List<GrupoDTO> obtenerPorNombre(@PathVariable String nombre) {
        return grupoService.obtenerPorNombre(nombre);
    }

    @Operation(summary = "Buscar grupos por jornada")
    @GetMapping("/jornada/{jornada}")
    public List<GrupoDTO> obtenerPorJornada(@PathVariable Jornada jornada) {
        return grupoService.obtenerPorJornada(jornada);
    }

    @Operation(summary = "Actualizar un grupo")
    @PutMapping("/{id}")
    public GrupoDTO actualizarGrupo(@PathVariable Long id, @RequestBody GrupoCreateDTO grupo) {
        return grupoService.actualizarGrupo(id, grupo);
    }

    @Operation(summary = "Eliminar un grupo")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarGrupo(@PathVariable Long id) {
        grupoService.eliminarGrupo(id);
    }
}
