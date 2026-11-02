package com.appfutbol.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaApiDto {
    // Esta API guarda la lista de partidos en un bloque llamado "matches", no "response"
    private List<PartidoApiDto> matches;

    public List<PartidoApiDto> getMatches() { return matches; }
    public void setMatches(List<PartidoApiDto> matches) { this.matches = matches; }
}