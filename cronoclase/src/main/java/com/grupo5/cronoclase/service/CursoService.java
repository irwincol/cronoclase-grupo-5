package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service

public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // Servicio para crear un solo curso

    public Curso crearEstudiante(Curso curso) {
        return cursoRepository.save(curso);

    }

    // Servicio para crear varios cursos de una sola vez

    public List<Curso> crearVariosCursos(List<Curso> cursos) {
        // Usamos el método que ya existe en el repositorio por herencia
        return cursoRepository.saveAll(cursos);
    }

    // servicio para obtener todos los cursos

    public List<Curso> obtenerCursos() {
        return cursoRepository.findAll();
    }

    // servicio para obtener un curso por su nombre
    public List<Curso> findCursoByNombre(String nombreCurso) {

        return cursoRepository.findByNombreContainingIgnoreCase(nombreCurso);

    }

    public Curso obtenerPorId(Long id) {

        // servicio para obtener un curso por su ID
        // De esta forma se busca un profesor por su ID. y se lanza un mensaje de error
        // en
        // caso de que no se encuentre
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
    }

    @Transactional
    public Curso actualizarCurso(Long id, Curso cursoNuevosDatos) {
        // 1. Reutilizamos tu lógica de búsqueda (si no existe, lanza el
        // RuntimeException solo)
        Curso cursoExistente = obtenerPorId(id);

        // 2. Seteamos los cambios (solo el nombre en este caso)
        cursoExistente.setNombre(cursoNuevosDatos.getNombre());

        // 3. Guardamos (JPA detecta el ID y hace el UPDATE)
        return cursoRepository.save(cursoExistente);
    }

    @Transactional
    public void eliminarCurso(Long id) {
        // 1. Usamos tu método para validar que el curso existe.
        // Si no existe, él mismo lanza la RuntimeException y detiene el proceso.
        obtenerPorId(id);

        // 2. Si llegó aquí, es porque el ID es válido. Procedemos a borrar.
        cursoRepository.deleteById(id);
    }

}
