package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.Jornada;

import java.util.*;

@Service

public class GrupoService {


    @Autowired
    private GrupoRepository grupoRepository;

    public Grupo crearGrupo(Grupo grupo) {
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

   

}
