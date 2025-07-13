package com.MiTurno.MiTurno.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity 
@Table(name= "institucion" )
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Institucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(length = 40, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private Integer telefono;
}
