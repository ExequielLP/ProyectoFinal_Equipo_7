package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;
import Proyecto_Equipo_7.repositorios.UsuarioRepositorio;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;
    @Autowired
    private ProveedorRepositorio proveedorRepositorio; 
    @Autowired
    private UsuarioRepositorio usuarioRepositorio; 
    @Transactional
    public Trabajo darDeAltaTrabajo(Proveedor proveedor, Usuario usuario) {

        Trabajo trabajo = new Trabajo();

        trabajo.setProveedor(proveedor);
        trabajo.setUsuario(usuario);
        
        trabajo = trabajoRepositorio.save(trabajo);

        return trabajo;
    }
    
    @Transactional
    public void crearTrabajo(HttpSession session, String id) {

        if (session != null) {
            Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
            Optional<Usuario> respuesta1 = usuarioRepositorio.findById(session.getId());
            if (respuesta.isPresent()) {
                Proveedor proveedor = respuesta.get();

                if (respuesta1.isPresent()) {
                    Usuario usuario = respuesta1.get();
                    Trabajo trabajo = new Trabajo();
                    trabajo.setProveedor(proveedor);
                    trabajo.setUsuario(usuario);
                    trabajoRepositorio.save(trabajo);
                }
            }
        }
    }

    @Transactional
    public void eliminarTrabajo(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setTerminado(true);
            trabajoRepositorio.save(trabajo);

        }
   }
}
