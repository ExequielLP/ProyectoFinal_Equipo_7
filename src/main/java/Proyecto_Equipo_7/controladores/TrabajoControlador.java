package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {
    
    @Autowired
    private TrabajoServicio trabajoServicio;

  
    @GetMapping("/crearTrabajo")
    public String crearTrabajo() {
        return "contratoTrabajo.html";
    }

    @PostMapping("/registro")
    public String registroTrabajo(@RequestParam Proveedor proveedor, @RequestParam Usuario usuario, ModelMap modelo) {

        trabajoServicio.darDeAltaTrabajo(proveedor, usuario);
        modelo.put("exito", "Proveedor registrado correctamente!");

        return "index.html";

    }

}
