package com.MiTurno.MiTurno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Modulo;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Long>{

}
