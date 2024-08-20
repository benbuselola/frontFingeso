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
    public List<Property> orderAsc(){return propertyRepository.orderAsc();}
    public List<Property> orderDes(){return propertyRepository.orderDes();}
    public List<Property> findByComuna(String comuna){return propertyRepository.findByComuna(comuna);}
    public List<Property> findByBathrooms(Integer bathrooms){return propertyRepository.findByBanos(bathrooms);}
    public List<Property> findByBedrooms(Integer bedrooms){return propertyRepository.findByDormitorios(bedrooms);}

    public List<Property> findByComunaAndBathrooms(String comuna, Integer bathrooms){
        return propertyRepository.findByComunaAndBanos(comuna, bathrooms);}

    public List<Property> findByComunaAndBedrooms(String comuna, Integer bedrooms){
        return propertyRepository.findByComunaAndDormitorios(comuna, bedrooms);
    }

    public List<Property> findByHabitacionesAndBanos(String bedrooms, String bathrooms){
        return propertyRepository.findByDormitoriosAndBanos(bedrooms, bathrooms);
    }
    public List<Property> findByComunaAndHabitacionesAndBanos(String comuna,
                                                              Integer bathrooms,
                                                              Integer bedrooms){
        return propertyRepository.findByComunaAndDormitoriosAndBanos(comuna, bathrooms, bedrooms);
    }

    public List<Property> findByAllFiltersAsc(String comuna,
                                              Integer bathrooms,
                                              Integer bedrooms){
        return propertyRepository.findByAllFiltersAsc(comuna, bathrooms, bedrooms);
    }

    public List<Property> findByAllFiltersDes(String comuna,
                                              Integer bathrooms,
                                              Integer bedrooms){
        return propertyRepository.findByAllFiltersDes(comuna, bathrooms, bedrooms);
    }
}
