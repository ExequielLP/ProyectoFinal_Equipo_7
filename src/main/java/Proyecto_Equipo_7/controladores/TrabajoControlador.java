package Proyecto_Equipo_7.controladores;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.RubroServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @Autowired
    private TrabajoServicio trabajoServicio;

    @Autowired
    private RubroServicio rubroServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @GetMapping("/crearTrabajo")
    public String crearTrabajo() {
        return "contratoContrato.html";
    }

    @PostMapping("/registro")
    public String registroTrabajo(@PathVariable String id, HttpSession session, ModelMap modelo) {
        trabajoServicio.crearTrabajo(session, id);
        modelo.put("exito", "Trabajo registrado correctamente!");
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('PROVEEDOR','ADMINISTRADOR')")
    @GetMapping("/finalizar_Trabajo/{id}")
    public String finalizarTrabajo(@PathVariable String id, ModelMap modelo) {
        trabajoServicio.finalizarTrabajo(id);
        // este metodo permite al proveedor dar por terminado un trabajo
        return "list_trabajos.html";
    }

    @GetMapping("/cargarTrabajo")
    public String cargarTrabajo(ModelMap modelo) {
        modelo.addAttribute("listaRubros", rubroServicio.listaRubros());
        modelo.addAttribute("proveedores", proveedorServicio.listarProveedores());
        // aca va la vista para que aparesca el form

        return "contratoTrabajo.html";
    }

    @PostMapping("/eliminar")
    public String eliminarTrabajo(@RequestParam String id, ModelMap modelo) {
        trabajoServicio.eliminarTrabajo(id);
        modelo.put("exito", "Trabajo fue dado de baja!");

        return "index.html";
    }

    @GetMapping("/persistirTrabajo/{id}")
    public String persistirTrabajo(@PathVariable String id, HttpSession session,RedirectAttributes attributes, ModelMap modelo) {
        try {
            // Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            // usuario.getId();
            trabajoServicio.crearTrabajo(session, id);
           attributes.addFlashAttribute("exito", "Servicio contratado exitosamente");
            return "redirect:/inicio";
        } catch (Exception e) {
            modelo.put("error", "Error al contratar servicio");
            // aca retorna vista de error o index
            return null;
        }
        // aca va la vista dps de envien datos del form
    }

    @PreAuthorize("AnyRole('ADMINISTRADOR')")
    @GetMapping("/baja_Trabajo/{id}")
    public String darDeBajaTrabajo(@PathVariable String id, ModelMap modelo) {
        trabajoServicio.eliminarTrabajo(id);
       
        return "listTrabajos.html";
    }

    @PostMapping("/persistirTrabajoContratoTrabajo")
    public String persistirTrabajoContratoTrabajo(@PathVariable String idProveedor,HttpSession session,RedirectAttributes attributes, ModelMap modelo) {
        try {
            
            trabajoServicio.crearTrabajo(session, idProveedor);
            attributes.addFlashAttribute("exito", "Servicio contratado exitosamente");
            return "redirect:/inicio";
        } catch (Exception e) {
            modelo.put("error", "Error al contratar servicio");
            // aca retorna vista de error o index
            return null;
        }

    }
}
