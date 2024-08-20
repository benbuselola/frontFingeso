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
    public List<Property> findByBathrooms(String bathrooms){return propertyRepository.findByBathrooms(bathrooms);}
    public List<Property> findByBedrooms(String bedrooms){return propertyRepository.findByBedrooms(bedrooms);}

    public List<Property> findByComunaAndBathrooms(String comuna, String bathrooms){
        return propertyRepository.findByComunaAndBathrooms(comuna, bathrooms);}

    public List<Property> findByComunaAndBedrooms(String comuna, String bedrooms){
        return propertyRepository.findByComunaAndBedrooms(comuna, bedrooms);
    }

    public List<Property> findByBedroomsAndBathrooms(String bedrooms, String bathrooms){
        return propertyRepository.findByBedroomsAndBathrooms(bedrooms, bathrooms);
    }
    public List<Property> findByComunaAndBathroomsAndBedrooms(String comuna,
                                                              String bathrooms,
                                                              String bedrooms){
        return propertyRepository.findByComunaAndBathroomsAndBedrooms(comuna, bathrooms, bedrooms);
    }

    public List<Property> findByAllFiltersAsc(String comuna,
                                              String bathrooms,
                                              String bedrooms){
        return propertyRepository.findByAllFiltersAsc(comuna, bathrooms, bedrooms);
    }

    public List<Property> findByAllFiltersDes(String comuna,
                                              String bathrooms,
                                              String bedrooms){
        return propertyRepository.findByAllFiltersDes(comuna, bathrooms, bedrooms);
    }
}
