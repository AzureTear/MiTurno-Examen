package com.MiTurno.MiTurno.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Institucion;
import com.MiTurno.MiTurno.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long>{

    List<Sucursal> findByInstitucionIdAndConfiguracionNotificacionesActivadas(Integer idInst, Boolean notificacionesActivadas);

    @Query(""" 
        SELECT s, c.notificacionesActivadas, i.nombre
        FROM Sucursal s     
        JOIN s.configuracion c
        JOIN s.institucion i
        """)
    List<Object[]> findSucursalConConfigYInsti();

    @Query("""
        SELECT s, i.nombre
        FROM Sucursal s
        JOIN s.institucion i
        JOIN s.configuracion c
        WHERE c.notificacionesActivadas = true
        """)
    List<Sucursal> findSucursalesNombreConConfiguracion(); 

    @Query("""
        SELECT s
        FROM Sucursal s
        JOIN s.configuracion c
        JOIN s.institucion i
        WHERE i.nombre = :instiNombre AND c.tiempo_anticipacion_notificacion > 1800
        """)
    List<Sucursal> findByInstitucionNombre(@Param("instiNombre") String instiNombre);

    List<Sucursal> findByInstitucion(Institucion institucion);

    Void deleteByInstitucion(Institucion institucion);

}
