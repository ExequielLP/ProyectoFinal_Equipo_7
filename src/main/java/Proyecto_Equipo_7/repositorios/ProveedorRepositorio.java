package Proyecto_Equipo_7.repositorios;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.enumeradores.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String> {

    @Query("SELECT p FROM  Proveedor p WHERE  p.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);

    @Query("SELECT p FROM Proveedor p WHERE p.servicio = :servicio")
    public Proveedor buscarPorServicio(@Param("servicio") Servicio servicio);

    @Query("SELECT p FROM Proveedor p INNER JOIN Calificacion c ON p.id = c.proveedor.id WHERE c.calificacion = :calificacion")
    public List<Proveedor> buscarPorCalificacionEspecifica(@Param("calificacion") int calificacion);

    @Query("SELECT p FROM Proveedor p INNER JOIN Calificacion c ON p.id = c.proveedor.id ORDER BY c.calificacion DESC")
    public List<Proveedor> buscarPorCalificacionGeneral();
    
    
//    @Query(" SELECT avg(calificacion) FROM calificacion JOIN proveedor p ON c.proveedor_id =:id")
//    public Double buscarProvedorCalificacionMasAlta(@Param("id")String id);
    
}
