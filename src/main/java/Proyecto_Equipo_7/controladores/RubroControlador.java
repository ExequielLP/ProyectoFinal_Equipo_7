package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.RubroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/rubro")
public class RubroControlador {

    @Autowired
    private RubroServicio rubroServicio;
    
      @GetMapping("/registrar")
    public String registrar() {

        return "editorial_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String rubro, ModelMap modelo) {
        if (rubro == null || rubro.trim().isEmpty()) {
            modelo.put("error", "Debe incluir el rubro");
            return "cargar_rubro_form.html";
        }

        try {
            rubroServicio.registrarRubro(rubro);
            modelo.put("exito", "La Editorial fue cargada correctamente!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            return "administrador.html";
        }

        return "panel.html";
    }
    
      @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Rubro> rubros = rubroServicio.listaRubros();
        modelo.addAttribute("rubros", rubros);
        if (modelo.containsAttribute("exito")) {
            String exito = (String) modelo.getAttribute("exito");
            modelo.addAttribute("exito", exito);
        }
        return "rubros_list.html";

    }
    
        @GetMapping("/modificar/{id}")
    public String formularioModificarRubro(@PathVariable String id, ModelMap modelo) {
        Rubro rubro = rubroServicio.getOne(id);
        modelo.put("rubro", rubro);
        modelo.put("id", id);
      
        return "rubro_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo, @RequestParam String nombreRubro){
        try {
            rubroServicio.actualizar(id, nombreRubro);
            modelo.addAttribute("exito", "editorial modificada exitosamente");
         
            return "administrador.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("autor", rubroServicio.getOne(id));
            modelo.put("id", id);
            modelo.put("nombreRubro", nombreRubro);
            return "rubro_modificar.html";
        }
    }
    
    
}
