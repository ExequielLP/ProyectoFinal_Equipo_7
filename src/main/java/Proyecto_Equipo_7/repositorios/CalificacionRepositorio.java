package Proyecto_Equipo_7.repositorios;


import Proyecto_Equipo_7.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String>{

     @Query("SELECT avg(calificacion) FROM calificacion")
    public Integer promedioCalificacionesTotales();
    
    
}
