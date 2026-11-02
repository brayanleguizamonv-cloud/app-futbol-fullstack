package com.appfutbol.backend.controller;

import com.appfutbol.backend.entity.Liga;
import com.appfutbol.backend.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController: Indica a Spring que esta clase recibe peticiones web y devuelve directamente datos en formato JSON.
@RestController
// @RequestMapping: Define la ruta URL base de este controlador.
@RequestMapping("/api/ligas")
public class LigaController {

    // @Autowired: Inyección de dependencias. Spring crea automáticamente el objeto LigaRepository
    // y nos lo presta aquí para que podamos interactuar con la base de datos.
    @Autowired
    private LigaRepository ligaRepository;

    // @GetMapping: Este método se ejecutará cuando alguien haga una petición HTTP GET a "/api/ligas".
    @GetMapping
    public List<Liga> obtenerLigas() {
        // Estructura de un solo return: capturamos los datos en una variable local y la devolvemos.
        List<Liga> resultadoLigas = ligaRepository.findAll();

        return resultadoLigas;
    }
}