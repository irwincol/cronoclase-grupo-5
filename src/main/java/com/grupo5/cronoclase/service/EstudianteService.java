package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    public EstudianteDTO convertirADTO(Estudiante estudiante) {
        return new EstudianteDTO(
            estudiante.getId(),
            estudiante.getNombre(),
            estudiante.getEmail()
        );
    }

    public Estudiante convertirAEntidad(EstudianteCreateDTO dto) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(dto.nombre());
        estudiante.setEmail(dto.email());
        estudiante.setDocumentoID(dto.documentoID());
        return estudiante;
    }

    @Transactional
    public EstudianteDTO crearEstudiante(EstudianteCreateDTO dto) {
        Estudiante estudiante = convertirAEntidad(dto);
        estudiante.setId(null);
        Estudiante guardado = estudianteRepository.save(estudiante);
        return convertirADTO(guardado);
    }

    @Transactional
    public List<EstudianteDTO> crearVariosEstudiantes(List<EstudianteCreateDTO> dtos) {
        List<Estudiante> estudiantes = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
        List<Estudiante> guardados = estudianteRepository.saveAll(estudiantes);
        return guardados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<EstudianteDTO> obtenerEstudiantes() {
        return estudianteRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public EstudianteDTO findEstudianteByDocumento(String documentoID) {
        Optional<Estudiante> estudianteEncontrado = estudianteRepository.findByDocumentoID(documentoID);
        if (estudianteEncontrado.isPresent()) {
            return convertirADTO(estudianteEncontrado.get());
        } else {
            throw new RuntimeException("Estudiante no encontrado, verifique ID ingresado");
        }
    }

    public List<EstudianteDTO> findEstudianteByNombre(String nombreEstudiante) {
        return estudianteRepository.findByNombreContainingIgnoreCase(nombreEstudiante).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Estudiante buscarEntidadPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + id));
    }

    public EstudianteDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    @Transactional
    public EstudianteDTO actualizarEstudiante(Long id, EstudianteCreateDTO datosNuevos) {
        Estudiante estudianteExistente = buscarEntidadPorId(id);

        estudianteExistente.setNombre(datosNuevos.nombre());
        estudianteExistente.setEmail(datosNuevos.email());
        estudianteExistente.setDocumentoID(datosNuevos.documentoID());

        Estudiante actualizado = estudianteRepository.save(estudianteExistente);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarEstudiante(Long id) {
        buscarEntidadPorId(id);
        estudianteRepository.deleteById(id);
    }
}
