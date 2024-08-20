package com.example.homesphere_back.services;

import com.example.homesphere_back.models.Properties;
import com.example.homesphere_back.repositories.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertiesRepository properRepository;

    //metodo para obtener todas las propiedades solicitadas desde el repositorio
    public List<Properties> obtainAll() {
        return properRepository.findAll();
    }

    //metodo para guardar una propiedad solicitada en el repositorio
    public Properties saveProperty(Properties property){
        return properRepository.save(property);
    }

    // metodo para obtener una propiedad por id solicitada desde el repositorio
    public Properties findById(Long id) {
        return properRepository.findByid(id);
    }
}
