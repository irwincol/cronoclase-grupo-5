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
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoDTO convertirADTO(Curso curso) {
        return new CursoDTO(
            curso.getId(),
            curso.getNombre()
        );
    }

    public Curso convertirAEntidad(CursoCreateDTO dto) {
        Curso curso = new Curso();
        curso.setNombre(dto.nombre());
        return curso;
    }

    @Transactional
    public CursoDTO crearCurso(CursoCreateDTO dto) {
        Curso curso = convertirAEntidad(dto);
        curso.setId(null); 
        Curso guardado = cursoRepository.save(curso);
        return convertirADTO(guardado);
    }

    @Transactional
    public List<CursoDTO> crearVariosCursos(List<CursoCreateDTO> dtos) {
        List<Curso> cursos = dtos.stream()
            .map(this::convertirAEntidad)
            .collect(Collectors.toList());
        List<Curso> guardados = cursoRepository.saveAll(cursos);
        return guardados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public List<CursoDTO> obtenerCursos() {
        return cursoRepository.findAll().stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    public List<CursoDTO> findCursoByNombre(String nombreCurso) {
        return cursoRepository.findByNombreContainingIgnoreCase(nombreCurso).stream()
            .map(this::convertirADTO)
            .collect(Collectors.toList());
    }

    private Curso buscarEntidadPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    public CursoDTO obtenerPorId(Long id) {
        return convertirADTO(buscarEntidadPorId(id));
    }

    @Transactional
    public CursoDTO actualizarCurso(Long id, CursoCreateDTO cursoNuevosDatos) {
        Curso cursoExistente = buscarEntidadPorId(id);

        cursoExistente.setNombre(cursoNuevosDatos.nombre());

        Curso actualizado = cursoRepository.save(cursoExistente);
        return convertirADTO(actualizado);
    }

    @Transactional
    public void eliminarCurso(Long id) {
        buscarEntidadPorId(id);
        cursoRepository.deleteById(id);
    }
}
