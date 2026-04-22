package com.grupo5.cronoclase.repository;

import com.grupo5.cronoclase.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository




public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long>{

    List<Evaluacion> findByGrupoId(Long grupoId);
    List<Evaluacion> findByGrupoNombre(String grupoNombre);

}
