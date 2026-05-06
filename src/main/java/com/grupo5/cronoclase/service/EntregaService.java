package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.TipoEvaluacion;
import com.grupo5.cronoclase.model.enums.EstadoEntrega;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDate;

@Service

public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    // ─────────────────────────────────────────────────────────────────────────
    // LÓGICA DE NEGOCIO: Determinar el estado de una entrega según fechas
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Calcula y devuelve el EstadoEntrega correcto comparando:
     *   - La fecha límite definida en la Evaluacion (fechaEntrega)
     *   - La fecha real en que el estudiante hizo la entrega (fechaEntregaReal)
     *
     * Reglas:
     *   · Si ya está CALIFICADO → no se cambia el estado.
     *   · Si NO hay fecha real y el plazo AÚN NO venció → PENDIENTE
     *   · Si NO hay fecha real y el plazo YA venció    → TARDE
     *   · Si la fecha real es ≤ fecha límite           → ENTREGADO
     *   · Si la fecha real es >  fecha límite           → TARDE
     */
    public EstadoEntrega calcularEstado(Entrega entrega) {

        // Si ya fue calificado, el estado no debe cambiar automáticamente
        if (entrega.getEstado() == EstadoEntrega.CALIFICADO) {
            return EstadoEntrega.CALIFICADO;
        }

        LocalDate fechaLimite  = entrega.getEvaluacion().getFechaEntrega();
        LocalDate fechaReal    = entrega.getFechaEntregaReal();
        LocalDate hoy          = LocalDate.now();

        // Aún no se ha registrado una entrega real
        if (fechaReal == null) {
            return hoy.isAfter(fechaLimite)
                    ? EstadoEntrega.TARDE      // el plazo ya venció y no entregó
                    : EstadoEntrega.PENDIENTE; // aún está dentro del plazo
        }

        // Ya existe fecha real → comparar con la fecha límite
        return fechaReal.isAfter(fechaLimite)
                ? EstadoEntrega.TARDE    // entregó después del plazo
                : EstadoEntrega.ENTREGADO; // entregó a tiempo
    }

    // ─────────────────────────────────────────────────────────────────────────

    // Servicio para crear una sola entrega
    public Entrega crearEntrega(Entrega entrega) {
        entrega.setId(null); // Ignoramos cualquier id del body para forzar INSERT
        entrega.setEstado(calcularEstado(entrega));
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

        // 2. Actualizamos los campos
        entregaExistente.setFechaEntregaReal(entregaNuevosDatos.getFechaEntregaReal());
        entregaExistente.setArchivoUrl(entregaNuevosDatos.getArchivoUrl());
        entregaExistente.setComentario(entregaNuevosDatos.getComentario());

        // 3. Recalculamos el estado automáticamente según las fechas
        //    (solo cambiamos si el profesor NO lo marcó como CALIFICADO previamente)
        if (entregaExistente.getEstado() != EstadoEntrega.CALIFICADO) {
            entregaExistente.setEstado(calcularEstado(entregaExistente));
        }

        // 4. Guardamos los cambios
        return entregaRepository.save(entregaExistente);
    }

    /**
     * Permite al profesor marcar una entrega como CALIFICADO explícitamente.
     * Una vez calificado, el estado ya no cambia de forma automática.
     */
    @Transactional
    public Entrega calificarEntrega(Long id) {
        Entrega entrega = obtenerPorId(id);
        entrega.setEstado(EstadoEntrega.CALIFICADO);
        return entregaRepository.save(entrega);
    }

    /**
     * Recalcula y actualiza el estado de TODAS las entregas PENDIENTES.
     * Útil para ejecutar como tarea programada (@Scheduled) y detectar
     * automáticamente las entregas que vencieron su fecha límite.
     */
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
        // Verificamos existencia antes de borrar
        obtenerPorId(id);
        entregaRepository.deleteById(id);
    }

    

}
