package com.example.homesphere_back.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Properties")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter

public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String comuna;
    private String region;
    private String tipo_publicacion;
    private String tipo_propiedad;
    private int tamano;
    private double valor;
    private int banos;
    private int dormitorios;
    private String descripcion;
    private String telefono;
    private String email;
}
