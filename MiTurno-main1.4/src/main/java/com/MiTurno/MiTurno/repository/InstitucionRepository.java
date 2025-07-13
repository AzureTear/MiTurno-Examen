package com.MiTurno.MiTurno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Institucion;

@Repository
public interface InstitucionRepository extends JpaRepository<Institucion, Long> {

}
