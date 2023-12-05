package Proyecto_Equipo_7.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.enumeradores.Rol;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrarusuario(String nombre, String domicilio, String telefono, String email, String password,
            String password2) throws MiException {

        validar(nombre, domicilio, telefono, email, password, password2);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setDomicilio(domicilio);
        usuario.setTelefono(telefono);
        usuario.setEmail(email);
        usuario.setRol(Rol.USER);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario actualizar(String id, String nombre, String domicilio, String telefono, String email,
            String password,
            String password2) throws MiException {

        validar(nombre, domicilio, telefono, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setDomicilio(domicilio);
            usuario.setTelefono(telefono);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);
            return usuarioRepositorio.save(usuario);
        }
        return null;

    }

    public Usuario getone(String id) {
        return usuarioRepositorio.getOne(id);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = usuarioRepositorio.findAll();
        return usuarios;
    }

    public List<Usuario> listarUsuariosPorRol() {

        List<Usuario> usuariosPorRol = new ArrayList<>();

        usuariosPorRol = usuarioRepositorio.listarPorRol();

        return usuariosPorRol;
    }

    public Integer cantidadUsuarios() {
        return usuarioRepositorio.cantidadUsuariosTotal();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuarioSession", usuario);
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if (usuario.getRol().equals(Rol.USER)) {
                usuario.setRol(Rol.ADMIN);
            } else if (usuario.getRol().equals(Rol.ADMIN)) {
                usuario.setRol(Rol.USER);
            }
        }
    }

    public void eliminar(String id) throws MiException {
        if (id.isEmpty() || id == null) {
            new Exception("El id es null");
        }
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(true);
            usuarioRepositorio.save(usuario);
        }
    }

    private void validar(String nombre, String domicilio, String telefono, String email, String password,
            String password2) throws MiException {

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
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }
}
