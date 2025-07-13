package com.MiTurno.MiTurno.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MiTurno.MiTurno.model.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long>{

    @Query(""" 
        SELECT t.id,t.fecha,t.numero,t.horaCreacion,t.horaAtencion,t.horaCancelacion,t.sucursal.id, t.sucursal.direccion, t.usuario.nombre , t.usuario.apellido FROM Turno t
        """)
    List<Object[]> findTurnoConSucursalYUsuario();

    @Query("""
        SELECT t , s.direccion
        FROM Turno t
        JOIN t.sucursal s
        JOIN t.usuario u
        WHERE u.nombre = :usuarioNombre
        """)
    List<Turno> findByUsuarioNombre(@Param("usuarioNombre") String usuarioNombre);

    @Query("""
        SELECT t 
        FROM Turno t
        JOIN t.estado e
        JOIN t.modulo m
        WHERE e.descripcionEstado = :descripcion AND m.titulo = :titulo
        """)
    List<Turno> findByDescripcionAndEstado(@Param("descripcion") String descripcion,@Param("titulo") String titulo );

    List<Turno> findByUsuarioIdAndModuloEstado(Integer idUsuario, String estado);

    List<Turno> findByFechaBetween(Date horaCreacion, Date horaAtencion);

}
