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
@Table(name= "configuracion" )
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Configuracion {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1, nullable = false)
    private Boolean notificacionesActivadas;

    @Column(length = 11, nullable = false)
    private Integer tiempo_anticipacion_notificacion;
}
