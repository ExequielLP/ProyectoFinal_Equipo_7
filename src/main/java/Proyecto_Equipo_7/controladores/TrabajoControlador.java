package Proyecto_Equipo_7.controladores;


import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProveedorServicio proveedorServicio;

    @GetMapping("/crearTrabajo")
    public String crearTrabajo() {
        return "contratoTrabajo.html";
    }

    @PostMapping("/registro")
    public String registroTrabajo(@PathVariable String id, HttpSession session, ModelMap modelo) {

        trabajoServicio.crearTrabajo(session, id);
        modelo.put("exito", "Trabajo registrado correctamente!");

        return "inicio.html";

    }

    @GetMapping("/cargarTrabajo/{id}")
    public String cargarTrabajo(@PathVariable String id, ModelMap modelo) {

//aca va la vista para que aparesca el form
        return "contratoTrabajo.html";
    }

    @GetMapping("/persistirTrabajo/{id}")
    public String persistirTrabajo(@PathVariable String id, HttpSession session, ModelMap modelo) {
        try {
//            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
//            usuario.getId();
            System.out.println(id);
            System.out.println("session" + session);
            trabajoServicio.crearTrabajo(session, id);
            modelo.put("exito", "Servicio contratado exitosamente");
            return "redirect:/inicio";
        } catch (Exception e) {
            modelo.put("error", "Error al contratar servicio");
            // aca retorna vista de error o index
            return null;
        }
        //aca va la vista dps de envien datos del form

    }

}
