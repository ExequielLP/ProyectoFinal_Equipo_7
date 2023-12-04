package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private TrabajoServicio trabajoServicio;

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("usuarioSession");
        modelo.put("proveedor", proveedor);

        return "modificarProveedor.html";
    }

    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio,
            @RequestParam String telefono, @RequestParam Integer honorario, @RequestParam Rubro rubro, MultipartFile archivo,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            proveedorServicio.actualizar(id, nombre, domicilio, telefono, email, password, password2, archivo, honorario, rubro);

            modelo.put("exito", "Proveedor actualizado correctamente!");

            return "inicio.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("rubro", rubro);

            return "modificarProveedor.html";
        }

    }

    @GetMapping("/listaTrabajo")
    public String listarTrabajosPorProveedor(@SessionAttribute("proveedor") Proveedor proveedor, ModelMap modelo) {
        String proveedorId = proveedor.getId();
        List<Trabajo> trabajos = trabajoServicio.listarTrabajosPorProveedor(proveedorId);
        modelo.addAttribute("trabajos", trabajos);

        return "listaTrabajoPorProveedor.html";
    }

    /* @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenProveedor (@PathVariable String id){
        Proveedor proveedor = proveedorServicio.getone(id);
        
       byte[] imagen= proveedor.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
    
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }*/
    //metodo para agregar a la lista de trabajo de cada proveedor, al lado de cada trabajo
    @GetMapping("/finalizarTrabajo/{id}")
    public String finalizarTrabajo(@PathVariable String id) {

        trabajoServicio.darTerminadoTrabajo(id);

        return "redirect:/proveedor/";

    }

}
