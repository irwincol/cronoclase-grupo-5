package com.grupo5.cronoclase.repository;


import com.grupo5.cronoclase.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

     // Para saber quiénes están en un grupo específico
    List<Matricula> findByGrupoId(Long grupoId);
    
    // Para saber en qué está inscrito un estudiante
    List<Matricula> findByEstudianteId(Long estudianteId);



}
