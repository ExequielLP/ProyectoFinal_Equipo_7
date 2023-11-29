package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.excepciones.MiException;
import Proyecto_Equipo_7.repositorios.RubroRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RubroServicio {

    @Autowired
    private RubroRepositorio rubroRepositorio;

    @Transactional
    public void registrarRubro(String nombreRubro) throws MiException {
        Rubro rubro = new Rubro();
        rubro.setRubro(nombreRubro);
        rubroRepositorio.save(rubro);
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

    @Transactional(readOnly = true)

    public List<Rubro> listaRubros() {

        List<Rubro> rubros = new ArrayList();

        rubros = rubroRepositorio.findAll();

        return rubros;
    }

    @Transactional
    public void actualizar(String id, String nombreRubro) throws MiException {

        Optional<Rubro> respuesta = rubroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Rubro rubro = new Rubro();
            rubro.setRubro(nombreRubro);
            rubroRepositorio.save(rubro);

        }

    }
    
      public Rubro getOne(String id){
        
        
        return rubroRepositorio.getOne(id);
        
        
    }
      

}
