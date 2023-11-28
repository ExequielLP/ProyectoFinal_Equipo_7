package Proyecto_Equipo_7.repositorios;


import Proyecto_Equipo_7.entidades.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubroRepositorio extends JpaRepository<Rubro, String>{
    
}