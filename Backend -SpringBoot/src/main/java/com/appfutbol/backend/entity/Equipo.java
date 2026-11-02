package com.appfutbol.backend.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "equipo")
public class Equipo {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id_equipo;

        @Column(nullable = false, length = 100)
        private String nombre;

        @Column(length = 100)
        private String estadio;

        // ARQUITECTURA CLAVE: En Java no guardamos un simple "int id_liga", guardamos el OBJETO completo.
        // @ManyToOne indica que "Muchos equipos pertenecen a Una liga".
        @ManyToOne
        // @JoinColumn indica qué columna de la tabla SQL actúa como Clave Foránea (Foreign Key)
        @JoinColumn(name = "id_liga")
        private Liga liga;
        // TODO: Generar constructores vacíos, Getters y Setters

        public Equipo() {}

        public Integer getId_equipo() {
            return id_equipo;
        }

        public void setId_equipo(Integer id_equipo) {
            this.id_equipo = id_equipo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEstadio() {
            return estadio;
        }

        public void setEstadio(String estadio) {
            this.estadio = estadio;
        }

        public Liga getLiga() {
            return liga;
        }

        public void setLiga(Liga liga) {
            this.liga = liga;
        }
    }

