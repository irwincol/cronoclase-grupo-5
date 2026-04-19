package com.grupo5.cronoclase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.grupo5.cronoclase.repository.*;
import com.grupo5.cronoclase.model.entity.*;

import java.util.*;

@Service

public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    // Servicio para crear un solo profesor

    public Profesor crearProfesor(Profesor profesor) {

        profesor.setActivo(true);
        return profesorRepository.save(profesor);

    }

    // Servicio para crear varios profesores de una sola vez

    public List<Profesor> crearVariosProfesores(List<Profesor> profesores) {

        // se hace eso para poder setear el estado como activo por defecto

        profesores.forEach(p -> {
            if (p.getActivo() == null)
                p.setActivo(true);
        });
        return profesorRepository.saveAll(profesores);

    }

    // servicio para obtener todos los profesores

    public List<Profesor> obtenerProfesores() {
        return profesorRepository.findAll();
    }

    // Servicio para encotrar un profesor por su nombre

    public List<Profesor> findProfesorByNombre(String nombreProfesor) {

        List<Profesor> profesorEncontrado = profesorRepository.findByNombre(nombreProfesor);

        if (profesorEncontrado.isEmpty()) {

            throw new RuntimeException("Porfesor no encontrado, verifique el nombre ingresado");
        }

        else {
            return profesorEncontrado;
        }

    }

    public Profesor obtenerPorId(Long id) {
        // De esta forma se busca un profesor por su ID. y se lanza un mensaje de error en 
        //caso de que no se encuentre
        return profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + id));
    }

}
