package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.Jornada;
import com.grupo5.cronoclase.dto.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    public GrupoDTO convertirADTO(Grupo grupo) {
        return new GrupoDTO(
            grupo.getId(),
            grupo.getNombre(),
            grupo.getDia(),
            grupo.getJornada(),
            grupo.getCurso() != null ? grupo.getCurso().getNombre() : null,
            grupo.getProfesor() != null ? grupo.getProfesor().getNombre() : null
        );
    }

    public Grupo convertirAEntidad(GrupoCreateDTO dto) {
        Grupo grupo = new Grupo();
        grupo.setNombre(dto.nombre());
        grupo.setDia(dto.dia());
        grupo.setJornada(dto.jornada());
        
        if (dto.cursoId() != null) {
            Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + dto.cursoId()));
            grupo.setCurso(curso);
        }
        
        if (dto.profesorId() != null) {
            Profesor profesor = profesorRepository.findById(dto.profesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + dto.profesorId()));
            grupo.setProfesor(profesor);
        }
        
        return grupo;
    }

    @Transactional
    public GrupoDTO crearGrupo(GrupoCreateDTO dto) {
        Grupo grupo = convertirAEntidad(dto);
        grupo.setId(null);
        Grupo guardado = grupoRepository.save(grupo);
        return convertirADTO(guardado);
    }

    @Transactional
    public List<GrupoDTO> crearVariosGrupos(List<GrupoCreateDTO> dtos) {
        List<Grupo> grupos = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
        List<Grupo> guardados = grupoRepository.saveAll(grupos);
        return guardados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<GrupoDTO> obtenerTodos() {
        return grupoRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Grupo buscarEntidadPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + id));
    }

    public GrupoDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    public List<GrupoDTO> obtenerPorNombre(String nombre) {
        return grupoRepository.findByNombre(nombre).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<GrupoDTO> obtenerPorJornada(Jornada jornada) {
        return grupoRepository.findByJornada(jornada).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public GrupoDTO actualizarGrupo(Long id, GrupoCreateDTO datosNuevos) {
        Grupo grupoExistente = buscarEntidadPorId(id);

        grupoExistente.setNombre(datosNuevos.nombre());
        grupoExistente.setDia(datosNuevos.dia());
        grupoExistente.setJornada(datosNuevos.jornada());
        
        if (datosNuevos.cursoId() != null) {
            Curso curso = cursoRepository.findById(datosNuevos.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + datosNuevos.cursoId()));
            grupoExistente.setCurso(curso);
        }
        
        if (datosNuevos.profesorId() != null) {
            Profesor profesor = profesorRepository.findById(datosNuevos.profesorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + datosNuevos.profesorId()));
            grupoExistente.setProfesor(profesor);
        }

        Grupo actualizado = grupoRepository.save(grupoExistente);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarGrupo(Long id) {
        buscarEntidadPorId(id);
        grupoRepository.deleteById(id);
    }
}
