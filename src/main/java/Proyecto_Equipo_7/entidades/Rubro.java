package Proyecto_Equipo_7.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Rubro {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String rubro;

    @OneToOne
    private Imagen imagen;
}
