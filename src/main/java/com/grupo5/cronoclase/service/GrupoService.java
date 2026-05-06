package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.Jornada;

import java.util.*;

@Service

public class GrupoService {


    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo crearGrupo(Grupo grupo) {
        grupo.setId(null); // Ignoramos cualquier id del body para forzar INSERT
        return grupoRepository.save(grupo);
    }

    public List<Grupo> crearVariosGrupos(List<Grupo> grupos) {
        return grupoRepository.saveAll(grupos);
    }


    public List<Grupo> obtenerTodos() {
        return grupoRepository.findAll();
    }

    public Grupo obtenerPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + id));
    }

    // 5. Buscar por Nombre
    public List<Grupo> obtenerPorNombre(String nombre) {
        return grupoRepository.findByNombre(nombre);
    }

    // 6. Buscar por Jornada
    public List<Grupo> obtenerPorJornada(Jornada jornada) {
        return grupoRepository.findByJornada(jornada);
    }

    // --- ACTUALIZAR GRUPO ---
    @Transactional
    public Grupo actualizarGrupo(Long id, Grupo datosNuevos) {
        // 1. Buscamos el grupo actual (si no existe, lanza el error ya programado)
        Grupo grupoExistente = obtenerPorId(id);

        // 2. Seteamos los nuevos datos
        grupoExistente.setNombre(datosNuevos.getNombre());
        grupoExistente.setDia(datosNuevos.getDia());
        grupoExistente.setJornada(datosNuevos.getJornada());
        grupoExistente.setCurso(datosNuevos.getCurso());
        grupoExistente.setProfesor(datosNuevos.getProfesor());

        // 3. Guardamos (JPA hace el UPDATE automáticamente)
        return grupoRepository.save(grupoExistente);
    }

    // --- ELIMINAR GRUPO ---
    @Transactional
    public void eliminarGrupo(Long id) {
        // Validamos que existe antes de intentar borrar
        obtenerPorId(id);

        // Al ejecutar esto, por el CascadeType.ALL que tienes en la Entidad,
        // se borrarán también sus MATRICULAS y EVALUACIONES.
        grupoRepository.deleteById(id);
    }

}
