package com.example.homesphere_back.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    // Atributo relacionado al identificador de cada
    // usuario.
    private Long id;

    // Atributos relacionados a la información
    // básica de cada usuario.
    private String firstName;
    private String secondName;
    private String lastName;
    private String secondLastName;
    private String rut;

    // Atributos relacionados al contacto de cada
    // usuario.
    private String email;
    private String number;

    // Atributos relacionados al inicio de sesión
    // de cada usuario.
    private String password;

    // Atributo relacionado a las propiedades
    // favoritas del usuario.
    private String listIdPropFavorites;

    // ¿cómo vemos los administradores?
    // falta agenda, notificaciones, reseñas, pagos

    @OneToMany
    @JoinColumn(name = "id_user")
    private List<Property> properties;

    @OneToMany
    @JoinColumn(name = "favorites")
    private List<Property> favoriteProps;
}