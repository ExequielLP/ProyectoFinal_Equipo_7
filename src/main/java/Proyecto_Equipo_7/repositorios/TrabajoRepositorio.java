package Proyecto_Equipo_7.repositorios;


import Proyecto_Equipo_7.entidades.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrabajoRepositorio extends JpaRepository<Trabajo, String>{

    @Query("SELECT count(*) FROM Trabajo ")
    public Integer cantidadContratosTotales();
    
}
