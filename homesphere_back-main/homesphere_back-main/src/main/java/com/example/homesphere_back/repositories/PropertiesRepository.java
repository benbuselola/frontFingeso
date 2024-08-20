package com.example.homesphere_back.repositories;

import com.example.homesphere_back.models.Properties;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long> {
    Properties findByid(Long id);
}
