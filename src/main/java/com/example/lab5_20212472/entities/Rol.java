package com.example.lab5_20212472.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrol")
    private Integer idrol;  // lo colocamos como el la bd

    @Column(name = "nombre", nullable = false, unique = true, length = 45)
    private String nombre;
}