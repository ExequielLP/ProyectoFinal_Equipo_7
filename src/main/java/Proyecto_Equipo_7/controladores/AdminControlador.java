package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.servicios.Proveedorservicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
      private UsuarioServicio usuarioServicio;
      
      @Autowired 
      private Proveedorservicio proveedorservicio;

      @Autowired 
      private TrabajoServicio trabajoServicio;
    
   @GetMapping("/dashboard")
   public String panelAdministrativo(ModelMap modelo){
       modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
       modelo.addAttribute("proveedores", proveedorservicio.listarProveedores());
       modelo.addAttribute("trabajos", trabajoServicio.listarTrabajos());
       return "panel.html";
   }
   
   @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioServicio.cambiarRol(id);
        
       return "redirect:/admin/usuarios";
    }
    
    
    
}
