package Proyecto_Equipo_7.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Proyecto_Equipo_7.entidades.Administrador;

@Repository
public interface AdministradorRepositorio extends JpaRepository<Administrador, String> {

}