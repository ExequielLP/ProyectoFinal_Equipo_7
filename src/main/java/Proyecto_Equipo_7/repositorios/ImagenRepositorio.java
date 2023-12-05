package Proyecto_Equipo_7.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Proyecto_Equipo_7.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{

}
