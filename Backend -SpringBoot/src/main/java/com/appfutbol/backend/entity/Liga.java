package com.appfutbol.backend.entity;

import jakarta.persistence.*;

// @Entity le dice a Hibernate: "Esta clase representa una tabla de PostgreSQL"
@Entity
// @Table es opcional si la clase se llama igual que la tabla, pero es buena práctica
@Table(name = "liga")
public class Liga {

    // @Id indica que es la Clave Primaria (Primary Key)
    @Id
    // GenerationType.IDENTITY es el equivalente exacto al "SERIAL" de PostgreSQL (autoincremental gestionado por la BD)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_liga;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String pais;

    private Boolean activa = true;

    // TODO: Generar constructores vacíos, Getters y Setters para todos los campos

    public Liga() {
    }

    public Integer getId_liga() {
        return id_liga;
    }

    public void setId_liga(Integer id_liga) {
        this.id_liga = id_liga;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
