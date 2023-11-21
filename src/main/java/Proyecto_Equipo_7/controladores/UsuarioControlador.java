package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    
     @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

      @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio,@RequestParam String telefono,
             @RequestParam String password,String password2, ModelMap modelo) {
           
        try {
            usuarioServicio.registrarusuario(nombre,domicilio,telefono,email,password,password2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "registro.html";
        }

    }
}
