package com.example.homesphere_back.services;

import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.repositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public boolean saveProperty(Property property){
        propertyRepository.save(property);
        return true;
    }

    public Optional<Property> findById(Long id){return propertyRepository.findById(id);}
    public List<Property> obtainAll(){return propertyRepository.obtainAll();}
}
