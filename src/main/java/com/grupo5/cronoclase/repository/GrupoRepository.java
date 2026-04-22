package com.grupo5.cronoclase.repository;


import com.grupo5.cronoclase.model.entity.Estudiante;
import com.grupo5.cronoclase.model.entity.Grupo;
import com.grupo5.cronoclase.model.enums.Jornada;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    List<Grupo> findByJornada(Jornada jornada);
    List<Grupo> findByNombre(String nombre);




}
