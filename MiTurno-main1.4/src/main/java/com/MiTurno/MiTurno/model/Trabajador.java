package com.MiTurno.MiTurno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trabajador")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabajador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String nombre;

    @Column(length = 30, nullable = true)
    private String segundoNombre;

    @Column(length = 30, nullable = false)
    private String apellido;

    @ManyToOne
    @JoinColumn(name = "sucursal_id" , nullable = false)
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "rol_id" , nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "modulo" , nullable = false)
    private Modulo Modulo;
}
