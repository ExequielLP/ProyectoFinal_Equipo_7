package Proyecto_Equipo_7.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Proyecto_Equipo_7.entidades.Trabajo;

public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    @Query("SELECT count(*) FROM Trabajo ")
    public Integer cantidadContratosTotales();

    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id")
    public List<Trabajo> buscarTrabajosPorProveedor(@Param("id") String proveedorId);

}
