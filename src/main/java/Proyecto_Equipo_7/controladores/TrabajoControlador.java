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
import Proyecto_Equipo_7.servicios.TrabajoServicio;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @Autowired
    private TrabajoServicio trabajoServicio;

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
        trabajoServicio.darPorTerminadoUnTrabajo(id);
        // este metodo permite al proveedor dar por terminado un trabajo
        return "list_trabajos.html";
    }

    @GetMapping("/cargarTrabajo/{id}")
    public String cargarTrabajo(@PathVariable String id, ModelMap modelo) {
        // aca va la vista para que aparesca el form
        return "contratoTrabajo.html";
    }

    @GetMapping("/persistirTrabajo/{id}")
    public String persistirTrabajo(@PathVariable String id, HttpSession session, ModelMap modelo) {
        try {
            // Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
            // usuario.getId();
            trabajoServicio.crearTrabajo(session, id);
            modelo.put("exito", "Servicio contratado exitosamente");
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
        trabajoServicio.darDeBajaTrabajo(id);
        // este metodo permite que solo un admin y nadie mas pueda dar la baja a un
        // trabajo, puede ser al estar terminado o
        // porque por algun motivo se solicito
        return "listTrabajos.html";
    }
}
