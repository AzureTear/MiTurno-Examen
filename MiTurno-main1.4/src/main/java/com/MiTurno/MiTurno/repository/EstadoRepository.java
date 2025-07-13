package com.MiTurno.MiTurno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Estado;


@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
