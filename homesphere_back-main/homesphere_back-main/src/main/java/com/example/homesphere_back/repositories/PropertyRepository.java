package com.example.homesphere_back.repositories;

import com.example.homesphere_back.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    // implementacion findById, busca por id
    Optional<Property> findById(Long id);

    // implementacion obtainAll, listar todos las propiedades
    @Query("SELECT p FROM Property p")
    List<Property> obtainAll();

}