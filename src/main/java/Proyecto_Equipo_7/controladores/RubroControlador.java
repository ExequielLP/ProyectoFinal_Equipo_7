package Proyecto_Equipo_7.controladores;


import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.RubroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/rubro")
public class RubroControlador {

    @Autowired
    private RubroServicio rubroServicio;

    
    @GetMapping("/registrar")
    public String registrar() {

        return "registroRubro.html";

    }

    @PostMapping("/registro")
    public String registroRubro(@RequestParam String rubro, MultipartFile archivo, ModelMap modelo) {

        try {
            rubroServicio.registrarRubro(rubro, archivo);
            modelo.put("exito", "El rubro se registro correctamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());

            return "panel.html";
        }

        return "panel.html";
    }

    @GetMapping("/modificar/{id}")
    public String formularioModificarRubro(@PathVariable String id,MultipartFile archivo, ModelMap modelo) {
        Rubro rubro = rubroServicio.getOne(id);
        modelo.put("rubro", rubro);
        modelo.put("id", id);
        modelo.put("archivo", archivo);
        return "rubro_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo,@RequestParam MultipartFile archivo, @RequestParam String nombreRubro) {
        try {
            rubroServicio.actualizar(id, nombreRubro, archivo);
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
