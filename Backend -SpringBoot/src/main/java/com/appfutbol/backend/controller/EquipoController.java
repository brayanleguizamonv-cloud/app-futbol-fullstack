package com.appfutbol.backend.controller;

import com.appfutbol.backend.entity.Equipo;
import com.appfutbol.backend.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @GetMapping
    public List<Equipo> obtenerEquipos() {
        // Capturamos la lista en una variable para mantener el estándar de un solo retorno por método
        List<Equipo> resultadoEquipos = equipoRepository.findAll();

        return resultadoEquipos;
    }
}