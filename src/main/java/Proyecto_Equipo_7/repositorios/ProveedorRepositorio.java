package Proyecto_Equipo_7.repositorios;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM  Proveedor p WHERE  p.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Proveedor p WHERE p.rubro = :rubro")
    List<Proveedor> buscarPorRubro(@Param("rubro") Rubro rubro);

    @Query("SELECT count(*) FROM Proveedor")
    public Integer cantidadProveedores();

     @Query("SELECT p.calificacion FROM  Proveedor p WHERE  p.id = :id")
    public Integer calificacionPorProveedor(@Param("id") String id);
    
}
