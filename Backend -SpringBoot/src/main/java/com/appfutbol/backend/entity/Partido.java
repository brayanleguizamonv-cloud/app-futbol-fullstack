package com.appfutbol.backend.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime; // Importante para la zona horaria

@Entity
@Table(name = "partido")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_partido;

    // Mapeo de la clave foránea del equipo local
    @ManyToOne
    @JoinColumn(name = "id_equipo_local")
    private Equipo equipoLocal;

    // Mapeo de la clave foránea del equipo visitante
    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante")
    private Equipo equipoVisitante;

    // ARQUITECTURA HORARIA: OffsetDateTime es el equivalente en Java al TIMESTAMPTZ de Postgres.
    // Guarda la fecha, la hora y el "+00:00" del UTC.
    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(name = "goles_local")
    private Integer golesLocal = 0;

    @Column(name = "goles_visitante")
    private Integer golesVisitante = 0;

    private String estado = "PROGRAMADO";

    // TODO: Generar constructores vacíos, Getters y Setters

    public Partido() {}

    public Integer getId_partido() {
        return id_partido;
    }

    public void setId_partido(Integer id_partido) {
        this.id_partido = id_partido;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public void setEquipoLocal(Equipo equipoLocal) {
        this.equipoLocal = equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public void setEquipoVisitante(Equipo equipoVisitante) {
        this.equipoVisitante = equipoVisitante;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(OffsetDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}