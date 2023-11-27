package Proyecto_Equipo_7.entidades;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor extends Usuario {

    private Integer honorario;

    @OneToOne
    private Imagen imagen;

    @OneToOne
    private Rubro rubro;
    
    

}
