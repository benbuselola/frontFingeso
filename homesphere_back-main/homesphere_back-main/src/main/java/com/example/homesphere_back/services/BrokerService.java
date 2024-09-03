package com.example.homesphere_back.services;

import com.example.homesphere_back.models.Brokers;
import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.repositories.BrokerRepository;
import com.example.homesphere_back.repositories.PropertyRepository;
import com.example.homesphere_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrokerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BrokerRepository brokerRepository;

    // Agrega una cita al atributo Visits, en el siguiente formato "id_user - id_property - date; ..."
    public boolean addVisit(Long id_user, Long id_property, Long id_broker, String date){
        Optional<Brokers> optionalBroker = brokerRepository.findById(id_broker);
        if(optionalBroker.isPresent()){
            Brokers broker = optionalBroker.get();
            String visits = broker.getVisits();
            visits += id_user + " - " + id_property + " - " + date + "; ";
            broker.setVisits(visits);
            brokerRepository.save(broker);
            return true;
        }
        return false;
    }

    public List<Brokers> showAllVisits(Long id){
        Brokers broker = brokerRepository.findById(id).get();
        String visits = broker.getVisits();
        List<Brokers> visitsList = new ArrayList<>();

        if (visits.isEmpty()){
            return null;
        }else{
            List<String> visitsListString = new ArrayList<>(Arrays.asList(visits.split(";")));

            for (int i = 0; i < visitsListString.size(); i++) {
                Brokers visit = brokerRepository.findById(Long.parseLong(visitsListString.get(i))).orElse(null);
                visitsList.add(visit);
            }
        }
        return visitsList;
    }


    /*
    public List<Property> showLikedProperties(Long id){
        Users user = userRepository.findById(id).get();
        String likedProperties = user.getLikedProperties();
        List<Property> likedPropsList = new ArrayList<>();

        if (likedProperties.isEmpty()){
            return null;
        }else{
            List<String> propertiesList = new ArrayList<>(Arrays.asList(likedProperties.split("-")));

            for (int i = 0; i < propertiesList.size(); i++) {
                Property prop = propertyRepository.findById(Long.parseLong(propertiesList.get(i))).orElse(null);
                likedPropsList.add(prop);
            }
        }
        return likedPropsList;
    }
     */



}
