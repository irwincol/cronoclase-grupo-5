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
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public EvaluacionDTO convertirADTO(Evaluacion evaluacion) {
        return new EvaluacionDTO(
            evaluacion.getId(),
            evaluacion.getTitulo(),
            evaluacion.getTipo(),
            evaluacion.getPorcentaje(),
            evaluacion.getFechaEntrega(),
            evaluacion.getGrupo() != null ? evaluacion.getGrupo().getNombre() : null
        );
    }

    public Evaluacion convertirAEntidad(EvaluacionCreateDTO dto) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setTitulo(dto.titulo());
        evaluacion.setTipo(dto.tipo());
        evaluacion.setPorcentaje(dto.porcentaje());
        evaluacion.setFechaEntrega(dto.fechaEntrega());
        
        if (dto.grupoId() != null) {
            Grupo grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + dto.grupoId()));
            evaluacion.setGrupo(grupo);
        }
        
        return evaluacion;
    }

    @Transactional
    public EvaluacionDTO crearEvaluacion(EvaluacionCreateDTO dto) {
        Evaluacion evaluacion = convertirAEntidad(dto);
        evaluacion.setId(null);
        Evaluacion guardada = evaluacionRepository.save(evaluacion);
        return convertirADTO(guardada);
    }

    @Transactional
    public List<EvaluacionDTO> crearVariasEvaluaciones(List<EvaluacionCreateDTO> dtos) {
        List<Evaluacion> evaluaciones = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
        List<Evaluacion> guardadas = evaluacionRepository.saveAll(evaluaciones);
        return guardadas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<EvaluacionDTO> obtenerEvaluaciones() {
        return evaluacionRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<EvaluacionDTO> findEvaluacionByGrupoId(Long grupoId) {
        return evaluacionRepository.findByGrupoId(grupoId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<EvaluacionDTO> findEvaluacionByNombreGrupo(String grupoNombre) {
        return evaluacionRepository.findByGrupoNombre(grupoNombre).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Evaluacion buscarEntidadPorId(Long id) {
        return evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con ID: " + id));
    }

    public EvaluacionDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    @Transactional
    public EvaluacionDTO actualizarEvaluacion(Long id, EvaluacionCreateDTO datosNuevos) {
        Evaluacion evaluacionExistente = buscarEntidadPorId(id);

        evaluacionExistente.setTitulo(datosNuevos.titulo());
        evaluacionExistente.setTipo(datosNuevos.tipo());
        evaluacionExistente.setPorcentaje(datosNuevos.porcentaje());
        evaluacionExistente.setFechaEntrega(datosNuevos.fechaEntrega());
        
        if (datosNuevos.grupoId() != null) {
            Grupo grupo = grupoRepository.findById(datosNuevos.grupoId())
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con ID: " + datosNuevos.grupoId()));
            evaluacionExistente.setGrupo(grupo);
        }

        Evaluacion actualizada = evaluacionRepository.save(evaluacionExistente);
        return convertirADTO(actualizada);
    }

    @Transactional
    public void eliminarEvaluacion(Long id) {
        buscarEntidadPorId(id);
        evaluacionRepository.deleteById(id);
    }
}
