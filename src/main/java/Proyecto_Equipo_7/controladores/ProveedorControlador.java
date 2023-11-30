package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Imagen;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.enumeradores.Servicio;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

   
    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio,
            @RequestParam String telefono, @RequestParam Integer honorario, @RequestParam Servicio servicio, MultipartFile archivo,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            proveedorServicio.registrarProveedor(nombre, domicilio, telefono, email, password, password2,
                    archivo, honorario, servicio);

            modelo.put("exito", "Proveedor registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("honorario", honorario);

            return "registro.html";
        }

    }

    @PreAuthorize("hasAnyRole('USER','ADMINISTRADOR')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@RequestParam Integer honorario, @RequestParam Imagen imagen, @RequestParam Servicio servicio, @PathVariable String id, @RequestParam String nombre, @RequestParam String domicilio,@RequestParam String telefono, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            proveedorServicio.actualizar(nombre,domicilio,email,telefono,password,password2,id, honorario, imagen, servicio);

            modelo.put("exito", "Proveedor actualizado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("honorario", honorario);
            modelo.put("domicilio", imagen);
            modelo.put("telefono", servicio);

            return "proveedor_modificar.html";
        }

    }
     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/lista_proveedorCompleta")
    public String listarProfesionales(ModelMap modelo) {

        List<Proveedor> proveedores = proveedorServicio.listarproveedores();

        modelo.addAttribute("proveedores", proveedores);

        return "proveedor_listaCompleta.html";
    }
}
