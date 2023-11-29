package Proyecto_Equipo_7.repositorios;

import Proyecto_Equipo_7.entidades.Rubro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RubroRepositorio extends JpaRepository<Rubro, String> {

    @Query("SELECT rubro FROM Rubro")
    public List<Rubro> listaRubros();

}
