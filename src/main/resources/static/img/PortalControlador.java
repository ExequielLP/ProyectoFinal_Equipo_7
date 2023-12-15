package Proyecto_Equipo_7.controladores;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.RubroServicio;
import Proyecto_Equipo_7.servicios.TrabajoServicio;
import Proyecto_Equipo_7.servicios.UsuarioServicio;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private RubroServicio rubroServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private TrabajoServicio trabajoServicio;
    
    @GetMapping("/")
    public String index(ModelMap modelo) throws Exception {
        try {
            modelo.put("listaRubros", rubroServicio.listaRubros());
            modelo.put("cantidadUsuarios", usuarioServicio.cantidadUsuarios());
            modelo.put("cantidadProveedores", proveedorServicio.cantidadProveedores());
            modelo.put("cantidadTrabajosTotales", trabajoServicio.cantidadTrabajosTotales());
            return "index.html";
        } catch (Exception e) {
            return "index.html";
        }

    }

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto.html";

    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros(){
        return "sobreNosotros.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo,RedirectAttributes redirectAttrs) {
            if (error != null) {
                redirectAttrs.addFlashAttribute("error", "intenta nuevamente");
                return "redirect:/logout";
            }
            return "index.html";

    }
    

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        modelo.put("listarTrabajosPorCalificar", usuarioServicio.listarTrabajosPorCalificar());
        modelo.put("listaProveedor", proveedorServicio.listarProveedores());
        modelo.put("listaRubros", rubroServicio.listaRubros());
        // modelo.put("seisMejores", proveedorServicio.seisMejoresProveedores());
        Usuario logueado = (Usuario) session.getAttribute("usuarioSession");
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        // MUESTRA EL INICIO PROVEEDOR
        if (logueado.getRol().toString().equals("PROVEEDOR")) {
            List<Trabajo> listaTrabajosPorProveedor = trabajoServicio.listarTrabajosPorProveedor(session);
            modelo.put("listaTrabajosPorProveedor", listaTrabajosPorProveedor);
        }
        return "inicio.html";
    }

    @PostMapping("/registroUsuario")
    public String registroUsuario(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String domicilio,
            @RequestParam String telefono,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo,
            RedirectAttributes redirectAttrs) {

        try {
            usuarioServicio.registrarusuario(nombre, domicilio, telefono, email, password, password2);

            redirectAttrs.addFlashAttribute("exito", "Usuario registrado correctamente!");

            return "redirect:/";
        } catch (MiException ex) {
            redirectAttrs.addFlashAttribute("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            return "redirect:/";
        }
    }

    @PostMapping("/registroProveedor")
    public String registroProveedor(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam(required = false) Integer honorario,
            @RequestParam Rubro rubro, MultipartFile archivo, @RequestParam String password, String password2,
            ModelMap modelo,
            RedirectAttributes redirectAttrs) {
        try {
            if (honorario == null) {
                // Puedes manejar la situación de honorario nulo aquí
                throw new MiException("El 'honorario' es requerido.");
            }

            proveedorServicio.registrarProveedor(nombre, domicilio, telefono, email, password, password2, archivo,
                    honorario, rubro);
            redirectAttrs.addFlashAttribute("exito", "Proveedor registrado correctamente!");
            return "redirect:/";
        } catch (MiException ex) {
            redirectAttrs.addFlashAttribute("error", ex.getMessage());
            System.out.println(ex.getMessage());
            System.out.println("---------------------ERRRORRRR-------");
            // modelo.put("nombre", nombre);
            // modelo.put("email", email);
            // modelo.put("domicilio", domicilio);
            // modelo.put("telefono", telefono);
            // modelo.put("honorario", honorario);
            // modelo.put("rubro", rubro);
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/filtroProveedores/{id}", method = { RequestMethod.GET, RequestMethod.POST })
    public String devovlerProveedores(@PathVariable String id, ModelMap modelo) {
        List<Proveedor> proFiltradoList = proveedorServicio.listaProveedorPorRubro(id);
        System.out.println("..................................");
        System.out.println(proFiltradoList);
        System.out.println("..................................");
        modelo.put("proFiltrados", proFiltradoList);
        System.out.println("..................................");
        System.out.println(proFiltradoList);
        System.out.println("..................................");
        return "listarProveedoresPorCards.html";
    }
}