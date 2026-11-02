package com.example.myapplication;
public class Partido {
    private String liga;
    private String jornada; // Añadido
    private String fechaUtc;
    private String local;
    private String visitante;
    private String marcador;
    private String escudoLocal;
    private String escudoVisitante;

    public Partido(String liga, String jornada, String fechaUtc, String local, String visitante, String marcador, String escudoLocal, String escudoVisitante) {
        this.liga = liga;
        this.jornada = jornada;
        this.fechaUtc = fechaUtc;
        this.local = local;
        this.visitante = visitante;
        this.marcador = marcador;
        this.escudoLocal = escudoLocal;
        this.escudoVisitante = escudoVisitante;
    }

    public String getLiga() { return liga; }
    public String getJornada() { return jornada; } // Añadido
    public String getFechaUtc() { return fechaUtc; }
    public String getLocal() { return local; }
    public String getVisitante() { return visitante; }
    public String getMarcador() { return marcador; }
    public String getEscudoLocal() { return escudoLocal; }
    public String getEscudoVisitante() { return escudoVisitante; }
}