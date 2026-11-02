package com.appfutbol.backend.service;

import com.appfutbol.backend.dto.PartidoApiDto;
import com.appfutbol.backend.dto.RespuestaApiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartidoSyncService {

    @Value("${api.footballdata.key}")
    private String apiKey;

    public RespuestaApiDto probarConexionExterna() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", apiKey);
        HttpEntity<String> peticion = new HttpEntity<>(headers);

        // 1. Objeto maestro y lista vacía donde volcaremos todas las ligas
        RespuestaApiDto respuestaGlobal = new RespuestaApiDto();
        List<PartidoApiDto> todosLosPartidos = new ArrayList<>();

        // 2. Códigos oficiales de las competiciones (PD=Santander, PL=Premier, CL=Champions)
        // Puedes añadir o quitar códigos aquí. Si alguno da error 403/404, el sistema lo saltará.
        String[] codigosLigas = {"PD", "PL", "CL", "SA", "BL1"};

        // 3. Bucle para descargar cada liga
        for (String codigo : codigosLigas) {
            try {
                String url = "https://api.football-data.org/v4/competitions/" + codigo + "/matches";

                ResponseEntity<RespuestaApiDto> respuestaExterna = restTemplate.exchange(
                        url, HttpMethod.GET, peticion, RespuestaApiDto.class);

                // Si la respuesta tiene datos, los sumamos a la superlista
                if (respuestaExterna.getBody() != null && respuestaExterna.getBody().getMatches() != null) {
                    todosLosPartidos.addAll(respuestaExterna.getBody().getMatches());
                }
            } catch (Exception e) {
                // Si la API no te deja descargar una liga por tu plan gratuito, avisa en la consola pero no crashea
                System.out.println("Saltando liga por límite de API o código inválido: " + codigo);
            }
        }

        // 4. Empaquetamos todos los partidos combinados y los enviamos al móvil
        respuestaGlobal.setMatches(todosLosPartidos);
        return respuestaGlobal;
    }
}