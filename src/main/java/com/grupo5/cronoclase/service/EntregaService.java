package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.TipoEvaluacion;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service

public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    // Servicio para crear una sola entrega
    public Entrega crearEntrega(Entrega entrega) {
        return entregaRepository.save(entrega);
    }

    // Servicio para crear varias entregas de una sola vez
    public List<Entrega> crearVariasEntregas(List<Entrega> listaEntregas) {
        // Usamos el método que ya existe en el repositorio por herencia
        return entregaRepository.saveAll(listaEntregas);
    }

    // servicio para obtener todas las entregas
    public List<Entrega> obtenerEntregas() {
        return entregaRepository.findAll();
    }

    // servicio para obtener entregas por el ID de un estudiante
    public List<Entrega> findEntregaByEstudianteId(Long estudianteId) {
        return entregaRepository.findByEstudianteId(estudianteId);
    }

    // servicio para obtener entregas por el ID de una evaluación
    public List<Entrega> findEntregaByEvaluacionId(Long evaluacionId) {
        return entregaRepository.findByEvaluacionId(evaluacionId);
    }

    // servicio para obtener entregas filtrando por el nombre del estudiante
    public List<Entrega> findEntregaByNombreEstudiante(String nombreEstudiante) {
        // Esto permite que el profesor busque "Perez" y vea todas las entregas de
        // alumnos con ese apellido
        return entregaRepository.findByEstudianteNombreContainingIgnoreCase(nombreEstudiante);
    }

    public Entrega obtenerPorId(Long id) {
        // servicio para obtener una entrega por su ID
        // De esta forma se busca una entrega por su ID y se lanza un mensaje de error
        // en caso de que no se encuentre
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con ID: " + id));
    }

    @Transactional
    public Entrega actualizarEntrega(Long id, Entrega entregaNuevosDatos) {
        // 1. Buscamos la entrega actual (si no existe, lanza error)
        Entrega entregaExistente = obtenerPorId(id);

        // 2. Actualizamos los campos según tu entidad
        entregaExistente.setFechaEntregaReal(entregaNuevosDatos.getFechaEntregaReal());
        entregaExistente.setEstado(entregaNuevosDatos.getEstado());
        entregaExistente.setArchivoUrl(entregaNuevosDatos.getArchivoUrl());
        entregaExistente.setComentario(entregaNuevosDatos.getComentario());

        // 3. Guardamos los cambios
        return entregaRepository.save(entregaExistente);
    }

    @Transactional
    public void eliminarEntrega(Long id) {
        // Verificamos existencia antes de borrar
        obtenerPorId(id);
        entregaRepository.deleteById(id);
    }

    

}
