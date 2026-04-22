package com.grupo5.cronoclase.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service

public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;


    public Evaluacion crearEvaluacion(Evaluacion evaluacion) {
        evaluacion.setId(null); // Ignoramos cualquier id del body para forzar INSERT
        return evaluacionRepository.save(evaluacion);
    }

    // Servicio para crear varias evaluaciones de una sola vez
    public List<Evaluacion> crearVariasEvaluaciones(List<Evaluacion> evaluaciones) {
        // Usamos el método que ya existe en el repositorio por herencia
        return evaluacionRepository.saveAll(evaluaciones);
    }

    // servicio para obtener todas las evaluaciones
    public List<Evaluacion> obtenerEvaluaciones() {
        return evaluacionRepository.findAll();
    }

    // servicio para obtener evaluaciones por el ID de un grupo
    public List<Evaluacion> findEvaluacionByGrupoId(Long grupoId) {
        return evaluacionRepository.findByGrupoId(grupoId);
    }


     // servicio para obtener evaluaciones por el nombre de un grupo
    public List<Evaluacion> findEvaluacionByNombreGrupo(String grupoNombre) {
        return evaluacionRepository.findByGrupoNombre(grupoNombre);
    }



    public Evaluacion obtenerPorId(Long id) {
        // servicio para obtener una evaluacion por su ID
        // De esta forma se busca una evaluacion por su ID y se lanza un mensaje de error
        // en caso de que no se encuentre
        return evaluacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluación no encontrada con ID: " + id));
    }

    @Transactional
    public Evaluacion actualizarEvaluacion(Long id, Evaluacion datosNuevos) {
        Evaluacion evaluacionExistente = obtenerPorId(id);

        // Actualizamos los campos de la entidad
        evaluacionExistente.setTitulo(datosNuevos.getTitulo());
        evaluacionExistente.setTipo(datosNuevos.getTipo());
        evaluacionExistente.setPorcentaje(datosNuevos.getPorcentaje());
        evaluacionExistente.setFechaEntrega(datosNuevos.getFechaEntrega());
        // El grupo normalmente no se cambia, pero si lo necesitas, agrégalo aquí

        return evaluacionRepository.save(evaluacionExistente);
    }

    @Transactional
    public void eliminarEvaluacion(Long id) {
        obtenerPorId(id); // Validamos existencia
        evaluacionRepository.deleteById(id);
    }



}
