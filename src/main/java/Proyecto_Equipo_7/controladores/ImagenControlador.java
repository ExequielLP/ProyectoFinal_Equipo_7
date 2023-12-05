package Proyecto_Equipo_7.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import Proyecto_Equipo_7.entidades.Proveedor;
import Proyecto_Equipo_7.entidades.Rubro;
import Proyecto_Equipo_7.servicios.ProveedorServicio;
import Proyecto_Equipo_7.servicios.RubroServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private RubroServicio rubroServicio;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenProveedor(@PathVariable String id) {
        Proveedor proveedor = (Proveedor) proveedorServicio.getone(id);
        byte[] imagen = proveedor.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenRubro(@PathVariable String id) {
        Rubro rubro = (Rubro) rubroServicio.getOne(id);
        byte[] imagen = rubro.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
}
