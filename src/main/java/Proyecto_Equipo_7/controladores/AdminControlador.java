package Proyecto_Equipo_7.controladores;

import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.RubroServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;

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
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private TrabajoServicio trabajoServicio;

    @Autowired
    private RubroServicio rubroServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuariosPorRol());
        modelo.addAttribute("proveedores", proveedorServicio.listarProveedores());
        modelo.addAttribute("trabajos", trabajoServicio.listarTrabajos());
        modelo.addAttribute("rubros", rubroServicio.listaRubros());
        
        return "panel.html";
    }

    @GetMapping("/registrarRubro")
    public String registrar() {
        return "registroRubro.html";
    }

    @PostMapping("/registroRubro")
    public String registroRubro(@RequestParam String rubro, MultipartFile archivo, ModelMap modelo) {
        if (rubro == null || rubro.trim().isEmpty()) {
            modelo.put("error", "Debe incluir el rubro");
            return "registroRubro.html";
        }
        try {
            rubroServicio.registrarRubro(rubro, archivo);
            modelo.put("exito", "La Editorial fue cargada correctamente!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "panel.html";
        }
        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/lista_usuarioCompleta")
    public String listarUsuarios(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_listaCompleta.html";
    }

    @PostMapping("eliminarProveedor/{id}")
    public String eliminarProveedor(@PathVariable String id, ModelMap modelo) {
        try {
            proveedorServicio.eliminar(id);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/proveedor/listarProveedor";
    }

    @PostMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo) {
        try {
            usuarioServicio.eliminar(id);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/usuario/listarUsuario";
    }

    // metodo para el admin al lado de cada trabajo en la lista para poder
    // eliminarlo
    @PostMapping("/eliminarTrabajo/{id}")
    public String eliminarTrabajo(@PathVariable String id, ModelMap modelo){
        trabajoServicio.darDeBajaTrabajo(id);

        return "redirect:/admin/dashboard";
    }
}
