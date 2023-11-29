package Proyecto_Equipo_7.repositorios;

import Proyecto_Equipo_7.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM  Usuario u WHERE  u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    public Usuario findByEmail(@Param("email") String email);

    @Query("SELECT count(*) FROM  Usuario")
    public Integer cantidadTotal();

}
