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
@Table(name= "modulo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modulo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length= 30, nullable = false)
    private String titulo;

    @Column(length= 20, nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "estadoModulo_id" , nullable = false)
    private EstadoModulo estadoModulo;    
}
