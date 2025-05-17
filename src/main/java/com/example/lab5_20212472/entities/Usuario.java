package com.example.lab5_20212472.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario") // Exactamente como en tu BD
    private Integer idUsuario;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Column(name = "dni", nullable = false, unique = true, columnDefinition = "CHAR(8)")
    private String dni;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "pwd", nullable = false, length = 100)
    private String pwd;

    @Column(name = "activo", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer activo = 1;

    @ManyToOne
    @JoinColumn(name = "idrol", nullable = false)
    private Rol rol;
}