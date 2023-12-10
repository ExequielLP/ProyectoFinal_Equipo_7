package Proyecto_Equipo_7.entidades;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    private Calificacion calificacion;
}
