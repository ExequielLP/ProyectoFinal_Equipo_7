package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.servicios.CalificacionServicio;
import Proyecto_Equipo_7.servicios.Proveedorservicio;
import Proyecto_Equipo_7.servicios.RubroServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private Proveedorservicio proveedorServicio;
    @Autowired
    private RubroServicio rubroServicio;
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private CalificacionServicio calificacionServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        
        modelo.put("listaRubro", rubroServicio.listaRubros());
        modelo.put("cantidadUsuarios",usuarioServicio.cantidadUsuarios());
        modelo.put("cantidadProveedores", proveedorServicio.cantidadProveedores());
        modelo.put("promedioCalificacionesTotales",calificacionServicio.promedioCalificacionesTotales() );
        
        
        return "index.html";

    }

   @GetMapping("/contacto")
    public String contacto(){
    
        return"contacto.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contreaseña invalida intente nuevamente");
        }

        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        modelo.put("listaProveedor", proveedorServicio.listarProveedores());


         modelo.put("listaRubros", rubroServicio.listaRubros());
//         modelo.put("seisMejores", proveedorServicio.seisMejoresProveedores());
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        System.out.println(logueado.toString());
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }
 
   
}
