package Proyecto_Equipo_7.servicios;

import java.util.ArrayList;
import java.util.List;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;
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

    @Transactional
    public void crearTrabajo(HttpSession session, String id) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        if (session != null) {
            Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

            if (respuesta.isPresent()) {
                Proveedor proveedor = respuesta.get();

                Trabajo trabajo = new Trabajo();
                trabajo.setProveedor(proveedor);
                trabajo.setUsuario(usuario);
                trabajo.setTerminado(false);
                trabajo.setAlta(true);
                trabajoRepositorio.save(trabajo);
            }
        }
    }

    public List<Trabajo> listarTrabajos() {
        List<Trabajo> listaTrabajos = new ArrayList<>();
        listaTrabajos = trabajoRepositorio.findAll();


        return listaTrabajos;
    }
    
 
    @Transactional
    public void eliminarTrabajo(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setAlta(false);
            trabajoRepositorio.save(trabajo);

        }
   }

    // metodo en proveedor donde muestra lista de trabajos propios
    // debe llevar el boton para finalizar trabajo
    public List<Trabajo> listarTrabajosPorProveedor(HttpSession session) {
        Proveedor logueadoProveedor = (Proveedor) session.getAttribute("usuarioSession");
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(logueadoProveedor.getId());
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            List<Trabajo> listaTrabajosPorProveedor = new ArrayList<>();
            listaTrabajosPorProveedor = trabajoRepositorio.buscarTrabajoPorProveedor(proveedor.getId());
            return listaTrabajosPorProveedor;
        }
        return null;

    }

    public Integer cantidadTrabajosTotales() {
        return trabajoRepositorio.cantidadContratosTotales();
    }

   @Transactional
    public void finalizarTrabajo(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setTerminado(true);
            trabajoRepositorio.save(trabajo);
        }

    }

    // metodo en proveedor donde muestra lista de trabajos propios
    // debe llevar el boton para finalizar trabajo
    public List<Trabajo> listarTrabajoPorProveedor(HttpSession session) {
        Proveedor logueadoProveedor = (Proveedor) session.getAttribute("usuarioSession");
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(logueadoProveedor.getId());
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            List<Trabajo> listaTrabajoPorProveedor = new ArrayList<>();
            listaTrabajoPorProveedor = trabajoRepositorio.buscarTrabajoPorProveedor(proveedor.getId());
            return listaTrabajoPorProveedor;
        }
        return null;

    }

    @Transactional
    public void eliminarComentario(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setComentario(" ");
            trabajoRepositorio.save(trabajo);
        }

    }

}

