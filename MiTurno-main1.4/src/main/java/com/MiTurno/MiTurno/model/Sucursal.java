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

import java.util.Date;


@Entity
@Table(name = "sucursal")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String direccion;

    @Column(length = 20, nullable = false)
    private Integer telefono;    

    @Column(nullable = false)
    private Date horario;

    @Column(length =  4, nullable = false)
    private Integer capacidadMaxima;  
    
    @ManyToOne
    @JoinColumn(name = "configuracion_id" , nullable = false)
    private Configuracion configuracion;

    @ManyToOne
    @JoinColumn(name = "institucion_id" , nullable = false)
    private Institucion institucion;
}
