package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.Proveedorservicio;
import javax.servlet.http.HttpSession;
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
    private Proveedorservicio proveedorservicio;

    @GetMapping("/registrar")
    public String registrar() {

        return "registroProv.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio,
            @RequestParam String telefono, @RequestParam Integer honorario, @RequestParam Rubro rubro, MultipartFile archivo,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            proveedorservicio.registrarProveedor(nombre, domicilio, telefono, email, password, password2,
                    archivo, honorario, rubro);

            modelo.put("exito", "Proveedor registrado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("honorario", honorario);
            modelo.put("rubro", rubro);
            
            return "registroProv.html";

        }
    }

    @PostMapping("eliminarProveedor/{id}")
    public String eliminarProveedor(@PathVariable String id, ModelMap modelo){
        try {
            proveedorservicio.eliminar(id);
        } catch (MiException ex){
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/proveedor/listarProveedor";
    }

    @PreAuthorize("hasAnyRole('PROVEEDOR','ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("usuarioSession");
        modelo.put("proveedor", proveedor);

        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('PROVEEDOR','ADMIN')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String email, @RequestParam String domicilio,
            @RequestParam String telefono, @RequestParam Integer honorario, @RequestParam Rubro rubro, MultipartFile archivo,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            proveedorservicio.actualizar(id, nombre, domicilio, telefono, email, password, password2, archivo, honorario, rubro);

            modelo.put("exito", "Proveedor actualizado correctamente!");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("rubro", rubro);

            return "proveedor_modificar.html";
        }

    }

 

}
