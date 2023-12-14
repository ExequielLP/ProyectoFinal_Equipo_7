package Proyecto_Equipo_7.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;

public interface TrabajoRepositorio extends JpaRepository<Trabajo, String> {

    @Query("SELECT count(*) FROM Trabajo ")
    public Integer cantidadContratosTotales();

    // @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id")
    // public List<Trabajo> buscarTrabajosPorProveedor(@Param("id") String proveedorId);

    @Query("SELECT t FROM Trabajo t WHERE t.proveedor.id = :id AND t.terminado = 0 AND t.alta = 1")
    public List<Trabajo> buscarTrabajoPorProveedor(@Param("id") String proveedorId);
    
    
    @Query("SELECT t FROM Trabajo t WHERE t.terminado = 1 AND t.alta = 1")
    public List<Trabajo> listarTrabajosPorCalificar();
    
    

//    @Query("SELECT t.proveedor FROM Trabajo t WHERE t.id = :id")
//    public Proveedor buscarProveedorPorIdDeTrabajo(@Param("id") String id);
//    
//    @Query(nativeQuery = true, value = "SELECT * FROM trabajo t LEFT JOIN proveedor p ON p.id = t.proveedor_id WHERE t.id = :id")
//    public Trabajo findByIdWithProveedores(@Param("id") String id);
}
