package Proyecto_Equipo_7.controladores;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import Proyecto_Equipo_7.entidades.Proveedor;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        System.out.println(usuario);
        modelo.put("usuario", usuario);
        return "modificarUsuario.html";
    }

    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String domicilio,
            @RequestParam String telefono, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo, HttpSession session) {

        try {
            Usuario userUpdated= usuarioServicio.actualizar(id, nombre, domicilio, telefono, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            session.setAttribute("usuarioSession", userUpdated );
            return "redirect:/inicio";
        } catch (MiException ex) {
            System.out.println(ex);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            return "modificarUsuario.html";
        }
    }
    
        @GetMapping("/calificacion")
    public String calificacionProveedor( ModelMap modelo, HttpSession session) {
       Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        modelo.put("usuario", usuario);
        
        return "listaCalificar.html";

    }
    
        @PostMapping("/calificacion")
    public String calificar(@PathVariable String id, ModelMap modelo, HttpSession session, Double calificacion) {
        Proveedor proveedor = usuarioServicio.calificarProveedor(id, calificacion);
      //  modelo.put("calificacion", proveedorServicio.calificacionProveedores(id));
        return "redirect:/inicio";
    }
    
}