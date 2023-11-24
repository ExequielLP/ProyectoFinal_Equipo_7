package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.enumeradores.Servicio;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.Proveedorservicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
            @RequestParam String telefono, @RequestParam Integer honorario, @RequestParam Servicio servicio, MultipartFile archivo,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            proveedorservicio.registrarProveedor(nombre, domicilio, telefono, email, password, password2,
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

            return "registroProv.html";

        }

    }

}
