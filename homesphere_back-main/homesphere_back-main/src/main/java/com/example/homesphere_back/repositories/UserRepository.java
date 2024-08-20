package com.example.homesphere_back.repositories;

import com.example.homesphere_back.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {
    // implementacion findByEmail, encontrar usuario por correo
    Users findByEmail(String email);

    // implementacion findByRut, encontrar usuario por rut
    Users findByRut(String rut);

    // implementacion findByNumber, encontrar usuario por numero de contacto
    Users findByNumber(String number);

    // implementacion findByEmailAndPassword, encontrar usuario por correo y contraseña
    Users findByEmailAndPassword(String email, String password);

    // implementacion obtainAll, listar todos los usuarios
    @Query("SELECT u FROM Users u")
    List<Users> obtainAll();

}

