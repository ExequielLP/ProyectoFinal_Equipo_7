package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

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
            Usuario userUpdated = usuarioServicio.actualizar(id, nombre, domicilio, telefono, email, password,
                    password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            session.setAttribute("usuarioSession", userUpdated);
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

    @PostMapping("/calificar")
    @Transactional
    public String calificar(@RequestParam("idProveedor") String idProveedor,
            @RequestParam("idTrabajo") String idTrabajo,
            @RequestParam("calificacion") Integer calificacion, @RequestParam("comentario") String comentario,
            RedirectAttributes redirectAttributes) {
        Optional<Trabajo> respuesta1 = trabajoRepositorio.findById(idTrabajo);
        if (respuesta1.isPresent()) {
            Trabajo trabajo = respuesta1.get();
            trabajo.setAlta(false);
            trabajo.setComentario(comentario);
            trabajoRepositorio.save(trabajo);
        }
        Optional<Proveedor> proveedorOptional = proveedorRepositorio.findById(idProveedor);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            proveedor.setCalificacion(calificacion);
            proveedorRepositorio.save(proveedor);
        }
        redirectAttributes.addFlashAttribute("exito", "GRACIAS POR CALIFICAR!");
        return "redirect:/inicio";
    }
}