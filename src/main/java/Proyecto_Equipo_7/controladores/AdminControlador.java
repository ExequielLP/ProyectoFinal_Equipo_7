package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;


    @Autowired
    private TrabajoServicio trabajoServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {
        modelo.addAttribute("usuariosPorRol", usuarioServicio.listarUsuariosPorRol());
        modelo.addAttribute("proveedores", proveedorServicio.listaProveedor());
        modelo.addAttribute("trabajos", trabajoServicio.listarTrabajos());
        return "panel.html";
    }

   
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("eliminarProveedor/{id}")
    public String eliminarProveedor(@PathVariable String id, ModelMap modelo) {
        try {
            proveedorServicio.eliminar(id);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/proveedor/listarProveedor";
    }
    
       @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.eliminar(id);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "redirec:/usuario/listarUsuario";
    }
   
}
