package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Imagen;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.enumeradores.Rol;
import Proyecto_Equipo_7.enumeradores.Servicio;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Proveedorservicio implements UserDetailsService {

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Transactional
    public void registrarProveedor(String nombre, String domicilio, String telefono, String email, String password,
            String password2, MultipartFile archivo, Integer honorario, Servicio servicio) throws MiException {

        validar(nombre, domicilio, telefono, email, honorario, servicio, password, password2);

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setDomicilio(domicilio);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setHonorario(honorario);
        proveedor.setServicio(servicio);

        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        
        Imagen imagen = imagenServicio.guardar(archivo);

        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);

    }

    private void validar(String nombre, String domicilio, String telefono, String email, Integer honorario, Servicio servicio, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (domicilio.isEmpty() || domicilio == null) {
            throw new MiException("el domicilio no puede ser nulo o estar vacio");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("el telefono no puede ser nulo o estar vacio");
        }
        if (honorario == null || honorario == 0) {
            throw new MiException("el campo honorario no puede ser nulo o estar vacio");
        }
        if (servicio == null) {
            throw new MiException("el campo servicio no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

       @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);

        if (proveedor != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", proveedor);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
        } else {
            return null;
        }

    }

}
