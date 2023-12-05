package Proyecto_Equipo_7.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import Proyecto_Equipo_7.entidades.Imagen;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.RubroRepositorio;

@Service
public class RubroServicio {

    @Autowired
    private RubroRepositorio rubroRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrarRubro(String nombreRubro, MultipartFile archivo) throws MiException {
        Rubro rubro = new Rubro();
        rubro.setRubro(nombreRubro);
        Imagen imagen = imagenServicio.guardar(archivo);
        rubro.setImagen(imagen);
        rubroRepositorio.save(rubro);
    }

    @Transactional
    public void actualizar(String id, String nombreRubro, MultipartFile archivo) throws MiException {

        Optional<Rubro> respuesta = rubroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Rubro rubro = new Rubro();
            rubro.setRubro(nombreRubro);
            Imagen imagen = imagenServicio.guardar(archivo);
            rubro.setImagen(imagen);
            rubroRepositorio.save(rubro);
        }
    }

    public Rubro getOne(String id) {
        return rubroRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Rubro> listaRubros() {
        List<Rubro> rubros = new ArrayList<>();
        rubros = rubroRepositorio.findAll();
        return rubros;
    }

    public void eliminar(String id) throws MiException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID es nulo o vacío");
        }
        Optional<Rubro> rubroOptional = rubroRepositorio.findById(id);
        if (rubroOptional.isPresent()) {
            Rubro rubro = rubroOptional.get();
            rubroRepositorio.deleteById(id);
        } else {
            throw new MiException("No se encontró ningún Rubro con el ID proporcionado");
        }
    }

}
