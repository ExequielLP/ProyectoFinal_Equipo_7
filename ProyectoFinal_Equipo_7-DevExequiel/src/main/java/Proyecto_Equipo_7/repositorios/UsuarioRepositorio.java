package Proyecto_Equipo_7.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import Proyecto_Equipo_7.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'USER' OR u.rol = 'ADMIN'")
    public List<Usuario> listarPorRol();

    @Query("SELECT u FROM  Usuario u WHERE  u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT count(*) FROM  Usuario u WHERE u.rol = 'USER'")
    public Integer cantidadUsuariosTotal();

}
