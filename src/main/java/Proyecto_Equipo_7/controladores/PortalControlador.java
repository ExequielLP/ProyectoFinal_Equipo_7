package Proyecto_Equipo_7.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class PortalControlador {

       @GetMapping("/")
    public String index() {

        return "index.html";

    }
    
      @GetMapping("/login")
    public String login( ModelMap modelo) {
      

        return "login.html";
    }
    
    
    
}
