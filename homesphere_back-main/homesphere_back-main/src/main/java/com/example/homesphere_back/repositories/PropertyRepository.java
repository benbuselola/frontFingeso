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

    @Query("SELECT p FROM Property p ORDER BY p.valor ASC")
    List<Property> orderAsc();

    @Query("SELECT p FROM Property p ORDER BY p.valor DESC")
    List<Property> orderDes();

    List<Property> findByComuna(String comuna);
    List<Property> findByBathrooms(String bathrooms);
    List<Property> findByBedrooms(String bedrooms);


    @Query("SELECT p FROM Property p WHERE p.comuna = :comuna AND p.banos = :bathrooms")
    List<Property> findByComunaAndBathrooms(String comuna, String bathrooms);

    @Query("SELECT p FROM Property p WHERE p.comuna = :comuna AND p.dormitorios = :bedrooms")
    List<Property> findByComunaAndBedrooms(String comuna, String bedrooms);

    @Query("SELECT p FROM Property p WHERE p.dormitorios = :bedrooms AND p.banos = :bathrooms")
    List<Property> findByBedroomsAndBathrooms(String bedrooms, String bathrooms);

    @Query("SELECT p FROM Property p WHERE p.comuna = :comuna AND p.dormitorios = :bedrooms AND p.banos = :bathrooms AND p.dormitorios = :bedrooms")
    List<Property> findByComunaAndBathroomsAndBedrooms(String comuna,
                                                       String bathrooms,
                                                       String bedrooms);

    @Query("SELECT p FROM Property p WHERE p.comuna = :comuna AND p.banos = :bathrooms AND p.dormitorios = :bedrooms ORDER BY p.valor ASC")
    List<Property> findByAllFiltersAsc(String comuna,
                                       String bathrooms,
                                       String bedrooms);

    @Query("SELECT p FROM Property p WHERE p.comuna = :comuna AND p.banos = :bathrooms AND p.dormitorios = :bedrooms ORDER BY p.valor DESC")
    List<Property> findByAllFiltersDes(String comuna,
                                       String bathrooms,
                                       String bedrooms);
}