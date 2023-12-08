package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Calificacion;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Trabajo;
import Proyecto_Equipo_7.repositorios.CalificacionRepositorio;
import Proyecto_Equipo_7.repositorios.ProveedorRepositorio;
import Proyecto_Equipo_7.repositorios.TrabajoRepositorio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalificacionServicio {

    @Autowired
    private CalificacionRepositorio calificacionRepositorio;

    @Autowired
    TrabajoRepositorio trabajoRepositorio;
    @Autowired
    ProveedorRepositorio proveedorRepositorio;

    public Double promedioCalificacionesTotales() {

        return calificacionRepositorio.promedioCalificacionesTotales();

    }

    public Calificacion agregarCalificacionAlTrabajo(String id, String comentario, Double calificacionUsuario) {
        Optional<Trabajo> respuesta = trabajoRepositorio.findById(id);
        Trabajo respuesta2 = trabajoRepositorio.findByIdWithProveedores(id);
        System.out.println(respuesta2);

        if (respuesta.isPresent() && respuesta.get().isTerminado()) {
            Trabajo trabajo = respuesta.get();
            Proveedor proveedor = trabajoRepositorio.buscarProveedorPorIdDeTrabajo(id);

            if (proveedor != null) { // Verificar si se encontró el proveedor
                Calificacion calificacion = new Calificacion();
                calificacion.setTrabajo(trabajo);
                calificacion.setProveedor(proveedor);
                calificacion.setComentario(comentario);
                calificacion.setCalificacion(calificacionUsuario);
                calificacionRepositorio.save(calificacion);
                return calificacion; // Retornar la calificación creada
            }
        }
        return null;
    }
}
