package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;

import java.util.*;

@Service

public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    // 1. Crear una sola matrícula
    public Matricula crearMatricula(Matricula matricula) {
        matricula.setId(null); // Ignoramos cualquier id del body para forzar INSERT
        return matriculaRepository.save(matricula);
    }

    // 2. Crear varias matrículas al tiempo (POST masivo)
    public List<Matricula> crearVariasMatriculas(List<Matricula> matriculas) {
        return matriculaRepository.saveAll(matriculas);
    }


    // 3. Consultar todas las matrículas
    public List<Matricula> obtenerTodas() {
        return matriculaRepository.findAll();
    }

    // 4. Consultar por ID de Matrícula
    public Matricula obtenerPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula no encontrada con ID: " + id));
    }

    // 5. Consultar todas las matrículas de un Grupo específico
    public List<Matricula> obtenerPorGrupo(Long grupoId) {
        return matriculaRepository.findByGrupoId(grupoId);
    }

    // 6. Consultar todas las matrículas de un Estudiante específico
    public List<Matricula> obtenerPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }


    // --- ACTUALIZAR MATRÍCULA ---
    @Transactional
    public Matricula actualizarMatricula(Long id, Matricula datosNuevos) {
        // 1. Buscamos la matrícula actual (si no existe, lanza el error ya programado)
        Matricula matriculaExistente = obtenerPorId(id);

        // 2. Seteamos los nuevos datos
        matriculaExistente.setEstadoMatricula(datosNuevos.getEstadoMatricula());
        matriculaExistente.setEstudiante(datosNuevos.getEstudiante());
        matriculaExistente.setGrupo(datosNuevos.getGrupo());

        // 3. Guardamos (JPA hace el UPDATE automáticamente)
        return matriculaRepository.save(matriculaExistente);
    }

    // --- ELIMINAR MATRÍCULA ---
    @Transactional
    public void eliminarMatricula(Long id) {
        // Validamos que existe antes de intentar borrar
        obtenerPorId(id);

        matriculaRepository.deleteById(id);
    }

}
