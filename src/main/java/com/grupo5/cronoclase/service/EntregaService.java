package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.EstadoEntrega;
import com.grupo5.cronoclase.dto.*;

import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    public EstadoEntrega calcularEstado(Entrega entrega) {
        if (entrega.getEstado() == EstadoEntrega.CALIFICADO) {
            return EstadoEntrega.CALIFICADO;
        }

        LocalDate fechaLimite = entrega.getEvaluacion().getFechaEntrega();
        LocalDate fechaReal = entrega.getFechaEntregaReal();
        LocalDate hoy = LocalDate.now();

        if (fechaReal == null) {
            return hoy.isAfter(fechaLimite)
                    ? EstadoEntrega.TARDE
                    : EstadoEntrega.PENDIENTE;
        }

        return fechaReal.isAfter(fechaLimite)
                ? EstadoEntrega.TARDE
                : EstadoEntrega.ENTREGADO;
    }

    public EntregaDTO convertirADTO(Entrega entrega) {
        return new EntregaDTO(
            entrega.getId(),
            entrega.getFechaEntregaReal(),
            entrega.getEstado(),
            entrega.getArchivoUrl(),
            entrega.getComentario(),
            entrega.getEstudiante() != null ? entrega.getEstudiante().getNombre() : null,
            entrega.getEvaluacion() != null ? entrega.getEvaluacion().getTitulo() : null
        );
    }

    public Entrega convertirAEntidad(EntregaCreateDTO dto) {
        Entrega entrega = new Entrega();
        entrega.setFechaEntregaReal(dto.fechaEntregaReal());
        entrega.setEstado(dto.estado());
        entrega.setArchivoUrl(dto.archivoUrl());
        entrega.setComentario(dto.comentario());
        
        if (dto.estudianteId() != null) {
            Estudiante estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + dto.estudianteId()));
            entrega.setEstudiante(estudiante);
        }
        
        if (dto.evaluacionId() != null) {
            Evaluacion evaluacion = evaluacionRepository.findById(dto.evaluacionId())
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con ID: " + dto.evaluacionId()));
            entrega.setEvaluacion(evaluacion);
        }
        
        return entrega;
    }

    @Transactional
    public EntregaDTO crearEntrega(EntregaCreateDTO dto) {
        Entrega entrega = convertirAEntidad(dto);
        entrega.setId(null);
        entrega.setEstado(calcularEstado(entrega));
        Entrega guardada = entregaRepository.save(entrega);
        return convertirADTO(guardada);
    }

    @Transactional
    public List<EntregaDTO> crearVariasEntregas(List<EntregaCreateDTO> dtos) {
        List<Entrega> entregas = dtos.stream()
            .map(dto -> {
                Entrega entrega = convertirAEntidad(dto);
                entrega.setEstado(calcularEstado(entrega));
                return entrega;
            })
            .collect(Collectors.toList());
            
        List<Entrega> guardadas = entregaRepository.saveAll(entregas);
        return guardadas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<EntregaDTO> obtenerEntregas() {
        return entregaRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<EntregaDTO> findEntregaByEstudianteId(Long estudianteId) {
        return entregaRepository.findByEstudianteId(estudianteId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<EntregaDTO> findEntregaByEvaluacionId(Long evaluacionId) {
        return entregaRepository.findByEvaluacionId(evaluacionId).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<EntregaDTO> findEntregaByNombreEstudiante(String nombreEstudiante) {
        return entregaRepository.findByEstudianteNombreContainingIgnoreCase(nombreEstudiante).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Entrega buscarEntidadPorId(Long id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con ID: " + id));
    }

    public EntregaDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    @Transactional
    public EntregaDTO actualizarEntrega(Long id, EntregaCreateDTO dto) {
        Entrega entregaExistente = buscarEntidadPorId(id);

        entregaExistente.setFechaEntregaReal(dto.fechaEntregaReal());
        entregaExistente.setArchivoUrl(dto.archivoUrl());
        entregaExistente.setComentario(dto.comentario());
        
        // No permitimos cambiar la evaluación o estudiante en una actualización normal para mantener integridad, 
        // a menos que sea un requerimiento específico.

        if (entregaExistente.getEstado() != EstadoEntrega.CALIFICADO) {
            entregaExistente.setEstado(calcularEstado(entregaExistente));
        }

        Entrega actualizada = entregaRepository.save(entregaExistente);
        return convertirADTO(actualizada);
    }

    @Transactional
    public EntregaDTO calificarEntrega(Long id) {
        Entrega entrega = buscarEntidadPorId(id);
        entrega.setEstado(EstadoEntrega.CALIFICADO);
        Entrega calificada = entregaRepository.save(entrega);
        return convertirADTO(calificada);
    }

    @Transactional
    public void actualizarEstadosTodas() {
        List<Entrega> todas = entregaRepository.findAll();
        for (Entrega entrega : todas) {
            if (entrega.getEstado() == EstadoEntrega.PENDIENTE) {
                entrega.setEstado(calcularEstado(entrega));
                entregaRepository.save(entrega);
            }
        }
    }

    @Transactional
    public void eliminarEntrega(Long id) {
        buscarEntidadPorId(id);
        entregaRepository.deleteById(id);
    }
}
