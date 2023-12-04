package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Imagen;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.enumeradores.Rol;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private ImagenServicio imagenServicio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Transactional
    public void registrarProveedor(String nombre, String domicilio, String telefono, String email, String password,
            String password2, MultipartFile archivo, Integer honorario, Rubro rubro) throws MiException {

        validar(nombre, domicilio, telefono, email, honorario, rubro, password, password2);

        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setDomicilio(domicilio);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);
        proveedor.setRol(Rol.PROVEEDOR);
        proveedor.setHonorario(honorario);
        proveedor.setRubro(rubro);
        proveedor.setAlta(true);

        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

        Imagen imagen = imagenServicio.guardar(archivo);

        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);

    }

    private void validar(String nombre, String domicilio, String telefono, String email, Integer honorario, Rubro rubro, String password, String password2) throws MiException {

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
        if (rubro == null) {
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

            session.setAttribute("usuarioSession", proveedor);

            return new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
        } else {
            return null;
        }

    }

    @Transactional
    public void actualizar(String id, String nombre, String domicilio, String telefono, String email, String password,
            String password2, MultipartFile archivo, Integer honorario, Rubro rubro) throws MiException {

        validar(nombre, domicilio, telefono, email, honorario, rubro, password, password2);

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setNombre(nombre);
            proveedor.setEmail(email);
            proveedor.setDomicilio(domicilio);
            proveedor.setTelefono(telefono);
            proveedor.setRubro(rubro);
            
            Imagen imagen = imagenServicio.guardar(archivo);
            proveedor.setImagen(imagen);
            
            proveedor.setPassword(new BCryptPasswordEncoder().encode(password));

            proveedor.setRol(Rol.PROVEEDOR);

            proveedorRepositorio.save(proveedor);

        }

    }

    public Usuario getOne(String id){
        
        
        return proveedorRepositorio.getOne(id);
        
        
    }

    @Transactional(readOnly = true)
    public List<Proveedor> listaProveedor() {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = proveedorRepositorio.findAll();
        return proveedores;
    }

//este metodo en el admin para dar de baja proveedor
    public void eliminar(String id) throws MiException {
        if (id.isEmpty() || id == null) {
            new Exception("El id es null");
        }
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Proveedor proveedor = respuesta.get();
            proveedor.setAlta(false);
            proveedorRepositorio.save(proveedor);
        }

    }

    public Integer cantidadProveedores() {

        return proveedorRepositorio.cantidadProveedores();

    }

    @Transactional(readOnly = true)
    public List<Proveedor> seisMejoresProveedores() {
        Pageable pageable = (Pageable) PageRequest.of(0, 6);
        List<Proveedor> proveedores = proveedorRepositorio.seisMejoresProveedores(pageable);
        return proveedores;
    }

}
