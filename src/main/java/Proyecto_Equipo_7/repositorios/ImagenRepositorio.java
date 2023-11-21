package Proyecto_Equipo_7.repositorios;

import Proyecto_Equipo_7.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{

}
