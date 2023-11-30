package Proyecto_Equipo_7.entidades;

//import Proyecto_Equipo_7.enumeradores.Rol;
import Proyecto_Equipo_7.enumeradores.Servicio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
//import javax.persistence.PrimaryKeyJoinColumn;
//import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor extends Usuario{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

      

    private Integer honorario;

    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)
    private Servicio servicio;
    
    
    
    

}
