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
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public ProfesorDTO convertirADTO(Profesor profesor) {
        return new ProfesorDTO(
            profesor.getId(),
            profesor.getNombre(),
            profesor.getEmail(),
            profesor.getActivo()
        );
    }

    public Profesor convertirAEntidad(ProfesorCreateDTO dto) {
        Profesor profesor = new Profesor();
        profesor.setNombre(dto.nombre());
        profesor.setEmail(dto.email());
        profesor.setDocumentoID(dto.documentoID());
        // El activo se maneja en los métodos de creación o puede venir en el DTO
        profesor.setActivo(dto.activo() != null ? dto.activo() : true);
        return profesor;
    }

    @Transactional
    public ProfesorDTO crearProfesor(ProfesorCreateDTO dto) {
        Profesor profesor = convertirAEntidad(dto);
        profesor.setId(null);
        if (profesor.getActivo() == null) {
            profesor.setActivo(true);
        }
        Profesor guardado = profesorRepository.save(profesor);
        return convertirADTO(guardado);
    }

    @Transactional
    public List<ProfesorDTO> crearVariosProfesores(List<ProfesorCreateDTO> dtos) {
        List<Profesor> profesores = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
            
        profesores.forEach(p -> {
            if (p.getActivo() == null)
                p.setActivo(true);
        });
        List<Profesor> guardados = profesorRepository.saveAll(profesores);
        return guardados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<ProfesorDTO> obtenerProfesores() {
        return profesorRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<ProfesorDTO> findProfesorByNombre(String nombreProfesor) {
        List<Profesor> profesorEncontrado = profesorRepository.findByNombre(nombreProfesor);
        if (profesorEncontrado.isEmpty()) {
            throw new RuntimeException("Profesor no encontrado, verifique el nombre ingresado");
        } else {
            return profesorEncontrado.stream().map(this::convertirADTO).collect(Collectors.toList());
        }
    }

    private Profesor buscarEntidadPorId(Long id) {
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));
    }

    public ProfesorDTO obtenerPorId(Long id) {
        Profesor profesor = buscarEntidadPorId(id);
        return convertirADTO(profesor);
    }

    @Transactional
    public ProfesorDTO actualizarProfesor(Long id, ProfesorCreateDTO datosNuevos) {
        Profesor profesorExistente = buscarEntidadPorId(id);

        profesorExistente.setNombre(datosNuevos.nombre());
        profesorExistente.setEmail(datosNuevos.email());
        profesorExistente.setDocumentoID(datosNuevos.documentoID());
        if (datosNuevos.activo() != null) {
            profesorExistente.setActivo(datosNuevos.activo());
        }

        Profesor actualizado = profesorRepository.save(profesorExistente);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarProfesor(Long id) {
        buscarEntidadPorId(id);
        profesorRepository.deleteById(id);
    }
}
