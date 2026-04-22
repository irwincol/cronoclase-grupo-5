package com.grupo5.cronoclase.repository;

import com.grupo5.cronoclase.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;



@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

    List<Profesor> findByNombre(String NombreProfesor);

   


}
