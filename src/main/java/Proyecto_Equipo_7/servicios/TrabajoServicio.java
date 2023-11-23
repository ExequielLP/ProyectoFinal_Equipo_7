package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.entidades.Usuario;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

public class TrabajoServicio {

    @Autowired
    private TrabajoRepositorio trabajoRepositorio;

    public Trabajo darDeAltaTrabajo(Proveedor proveedor, Usuario usuario) {

        Trabajo trabajo = new Trabajo();

        trabajo.setProveedor(proveedor);
        trabajo.setUsuario(usuario);

        trabajo = trabajoRepositorio.save(trabajo);

        return trabajo;
    }


}
