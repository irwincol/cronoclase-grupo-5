package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.dto.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public MatriculaDTO convertirADTO(Matricula matricula) {
        return new MatriculaDTO(
            matricula.getId(),
            matricula.getEstadoMatricula(),
            matricula.getEstudiante() != null ? matricula.getEstudiante().getNombre() : null,
            matricula.getGrupo() != null ? matricula.getGrupo().getNombre() : null
        );
    }

    public Matricula convertirAEntidad(MatriculaCreateDTO dto) {
        Matricula matricula = new Matricula();
        matricula.setEstadoMatricula(dto.estadoMatricula());
        
        if (dto.estudianteId() != null) {
            Estudiante estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + dto.estudianteId()));
            matricula.setEstudiante(estudiante);
        }
        
        if (dto.grupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + dto.grupoId()));
            matricula.setGrupo(grupo);
        }
        
        return matricula;
    }

    @Transactional
    public MatriculaDTO crearMatricula(MatriculaCreateDTO dto) {
        Matricula matricula = convertirAEntidad(dto);
        matricula.setId(null);
        Matricula guardada = matriculaRepository.save(matricula);
        return convertirADTO(guardada);
    }

    @Transactional
    public List<MatriculaDTO> crearVariasMatriculas(List<MatriculaCreateDTO> dtos) {
        List<Matricula> matriculas = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
        List<Matricula> guardadas = matriculaRepository.saveAll(matriculas);
        return guardadas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<MatriculaDTO> obtenerTodas() {
        return matriculaRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Matricula buscarEntidadPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con ID: " + id));
    }

    public MatriculaDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    public List<MatriculaDTO> obtenerPorGrupo(Long grupoId) {
        return matriculaRepository.findByGrupoId(grupoId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<MatriculaDTO> obtenerPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    @Transactional
    public MatriculaDTO actualizarMatricula(Long id, MatriculaCreateDTO datosNuevos) {
        Matricula matriculaExistente = buscarEntidadPorId(id);

        matriculaExistente.setEstadoMatricula(datosNuevos.estadoMatricula());
        
        if (datosNuevos.estudianteId() != null) {
            Estudiante estudiante = estudianteRepository.findById(datosNuevos.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + datosNuevos.estudianteId()));
            matriculaExistente.setEstudiante(estudiante);
        }
        
        if (datosNuevos.grupoId() != null) {
            Grupo grupo = grupoRepository.findById(datosNuevos.grupoId())
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + datosNuevos.grupoId()));
            matriculaExistente.setGrupo(grupo);
        }

        Matricula actualizada = matriculaRepository.save(matriculaExistente);
        return convertirADTO(actualizada);
    }

    @Transactional
    public void eliminarMatricula(Long id) {
        buscarEntidadPorId(id);
        matriculaRepository.deleteById(id);
    }
}
