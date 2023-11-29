package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.servicios.Proveedorservicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {

    @Autowired
    private TrabajoServicio trabajoServicio;

    @Autowired
    private Proveedorservicio proveedorservicio;

    @GetMapping("/crear_Trabajo")
    public String crearTrabajo() {
        return "contratoTrabajo.html";
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

    @PreAuthorize("AnyRole('ADMINISTRADOR')")
    @GetMapping("/baja_Trabajo/{id}")
    public String darDeBajaTrabajo(@PathVariable String id, ModelMap modelo) {
        trabajoServicio.darDeBajaTrabajo(id);

        // este metodo permite que solo un admin y nadie mas pueda dar la baja a un trabajo, puede ser al estar terminado o 
        // porque por algun motivo se solicito
        return "list_trabajos.html";
    }
}
