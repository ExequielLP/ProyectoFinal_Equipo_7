package Proyecto_Equipo_7.entidades;
import Proyecto_Equipo_7.enumeradores.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor extends Usuario{

      

    private Integer honorario;

    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)
    private Servicio servicio;
    
    
    
    

}
