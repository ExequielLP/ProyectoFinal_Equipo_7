package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
// loco para que esta este man
//    @GetMapping("/registrar")
//    public String registrar() {
//        return "registro.html";
//    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            usuarioServicio.registrarusuario(nombre, domicilio, telefono, email, password, password2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "redirect:/";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "/";
        }

    }


    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id,ModelMap modelo) {
        try {
            usuarioServicio.eliminar(id);
        } catch (MiException ex) {
          modelo.put("error",ex.getMessage() );
        }

       return "redirec:/usuario/listarUsuario";
    }


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");
        modelo.put("usuario", usuario);
        return "usuarioModificar.html";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN ')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String domicilio, @RequestParam String telefono, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            usuarioServicio.actualizar(id, nombre, domicilio, telefono, email, password, password2);

            modelo.put("exito", "Usuario actualizado correctamente!");

            return "panel.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "usuarioModificar.html";
        }

    }
    

 
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/lista_usuarioCompleta")
    public String listarUsuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        modelo.addAttribute("usuarios", usuarios);

        return "usuario_listaCompleta.html";
    }
   
    
  
}
