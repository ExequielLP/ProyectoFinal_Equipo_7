package Proyecto_Equipo_7.servicios;

import Proyecto_Equipo_7.repositorios.CalificacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CalificacionServicio {

    @Autowired
    private CalificacionRepositorio calificacionRepositorio;
    
    public Double promedioCalificacionesTotales(){
        
        
        return calificacionRepositorio.promedioCalificacionesTotales();
        
    }
}
