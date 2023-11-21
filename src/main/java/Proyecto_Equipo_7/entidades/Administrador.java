package Proyecto_Equipo_7.entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Administrador extends Usuario {
   
}
