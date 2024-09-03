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

    // Muestra todas las visitas de un broker
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

    // Guarda un horario disponible para una propiedad, a cargo de un broker
    public boolean addAvailableTime(Long id_broker, Long id_property, String date, String time){
        Optional<Brokers> optionalBroker = brokerRepository.findById(id_broker);
        if(optionalBroker.isPresent()){
            Brokers broker = optionalBroker.get();
            String availableTimes = broker.getAvailableTimes();
            availableTimes += id_property + " - " + date + " - " + time + "; ";
            broker.setAvailableTimes(availableTimes);
            brokerRepository.save(broker);
            return true;
        }
        return false;
    }

    // Retorna las horas disponibles de un broker segun propiedad y dia
    public String showAvailableTime(Long id_broker, Long id_property, String date) {
        Brokers broker = brokerRepository.findById(id_broker).get();
        String availableTimes = broker.getAvailableTimes();
        String time = "";
        if (availableTimes.isEmpty()) {
            return null;
        } else {
            List<String> availableTimesList = new ArrayList<>(Arrays.asList(availableTimes.split(";")));
            for (int i = 0; i < availableTimesList.size(); i++) {
                List<String> availableTime = new ArrayList<>(Arrays.asList(availableTimesList.get(i).split(" - ")));
                if (availableTime.get(0).equals(id_property.toString()) && availableTime.get(1).equals(date)) {
                    time = availableTime.get(2);
                }
            }
        }
        return time;
    }

    public boolean saveBroker(Brokers broker){
        brokerRepository.save(broker);
        return true;
    }
}
