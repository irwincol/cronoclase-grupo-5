package com.grupo5.cronoclase.repository;



import com.grupo5.cronoclase.model.entity.*;
import com.grupo5.cronoclase.model.enums.TipoEvaluacion;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    // Buscar entregas de un estudiante específico
    List<Entrega> findByEstudianteId(Long estudianteId);    

    List<Entrega> findByEstudianteNombreContainingIgnoreCase(String estudianteNombre);
    
    // Buscar entregas de una tarea/examen específico para calificar
    List<Entrega> findByEvaluacionId(Long evaluacionId);

    
}
