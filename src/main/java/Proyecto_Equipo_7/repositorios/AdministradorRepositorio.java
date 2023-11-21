package Proyecto_Equipo_7.repositorios;

import Proyecto_Equipo_7.entidades.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepositorio extends JpaRepository<Administrador, String> {

}