package com.MiTurno.MiTurno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Rol;
import com.MiTurno.MiTurno.model.Sucursal;
import com.MiTurno.MiTurno.model.Trabajador;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long>{

    @Query("""
        SELECT t
        FROM Trabajador t
        JOIN t.rol r
        JOIN t.sucursal s
        WHERE s.id = :idS AND r.admin = false
        """)
    List<Trabajador> findBySucursalId(@Param("idS")Integer idS );

    List<Trabajador> findByRolAdminAndSucursalCapacidadMaximaGreaterThan(Boolean admin, Integer capacidadMaxima);

    void deleteBySucursal(Sucursal sucursal);

    List<Sucursal> findBySucursal(Sucursal sucursal);

    List<Trabajador> findByRol(Rol rol);

    Void deleteByRol(Rol rol);
}
