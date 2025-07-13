package com.MiTurno.MiTurno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
