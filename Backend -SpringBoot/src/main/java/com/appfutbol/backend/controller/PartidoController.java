package com.appfutbol.backend.controller;

import com.appfutbol.backend.dto.RespuestaApiDto;
import com.appfutbol.backend.entity.Partido;
import com.appfutbol.backend.repository.PartidoRepository;
import com.appfutbol.backend.service.PartidoSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private PartidoSyncService partidoSyncService;

    @GetMapping("/sincronizar")
    public RespuestaApiDto sincronizar() {
        RespuestaApiDto resultado = partidoSyncService.probarConexionExterna();

        return resultado;
    }

    @GetMapping
    public List<Partido> obtenerPartidos() {
        // Interacción directa con el contenedor PostgreSQL para extraer la tabla de partidos
        List<Partido> resultadoPartidos = partidoRepository.findAll();

        return resultadoPartidos;
    }
}