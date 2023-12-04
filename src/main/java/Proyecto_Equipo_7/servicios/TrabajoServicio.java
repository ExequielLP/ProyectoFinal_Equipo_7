package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;
import Proyecto_Equipo_7.repositorios.UsuarioRepositorio;

import java.util.ArrayList;
import java.util.List;
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
    public void crearTrabajo(HttpSession session, String id) {
        System.err.println("CREAR TRABAJO DATOS");
        System.out.println(session.getId());
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        System.out.println("__________________USUARIO________" + usuario);
        if (session != null) {
            Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

            System.out.println("PROVEEDOR_____________________________________" + respuesta);

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
//metodo para que el admin ekimine un trabajo dando de baja
    @Transactional
    public void eliminarTrabajo(String id)throws MiException  {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setAlta(false);
            trabajoRepositorio.save(trabajo);
        }

    }

    public List<Trabajo> listarTrabajos() {
        List<Trabajo> listaTrabajos = new ArrayList<>();
        listaTrabajos = trabajoRepositorio.findAll();

        return listaTrabajos;
    }

    @Transactional
    public void darTerminadoTrabajo(String id) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Trabajo trabajo = respuesta.get();
            trabajo.setTerminado(true);
            trabajoRepositorio.save(trabajo);
        }

    }

    public Integer cantidadTrabajosTotales() {

        return trabajoRepositorio.cantidadContratosTotales();

    }
    
    //metodo en proveedor donde muestra lista de trabajos propios 
    //debe llevar el boton para finalizar trabajo
     public List<Trabajo> listarTrabajosPorProveedor(String id) {
        List<Trabajo> listaTrabajosPorProveedor = new ArrayList<>();
        listaTrabajosPorProveedor = trabajoRepositorio.buscarTrabajosPorProveedor(id);

        return listaTrabajosPorProveedor;
    }
}
