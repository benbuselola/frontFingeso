package com.example.homesphere_back.controllers;

import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.services.BrokerService;
import com.example.homesphere_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brokers")
public class BrokerController {
    @Autowired
    private BrokerService brokerService;

    @PostMapping("/addVisit/{id_user}/{id_property}/{id_broker}/{date}")
    public ResponseEntity<Boolean> addVisit(@PathVariable Long id_user, @PathVariable Long id_property, @PathVariable Long id_broker, @PathVariable String date) {
        return ResponseEntity.ok().body(brokerService.addVisit(id_user, id_property, id_broker, date));
    }

    @GetMapping("/showAllVisits/{id}")
    public ResponseEntity<?> showAllVisits(@PathVariable Long id){
        return ResponseEntity.ok().body(brokerService.showAllVisits(id));
    }

    @PostMapping("/addAvailableTime/{id_broker}/{id_property}/{date}/{time}")
    public ResponseEntity<Boolean> addAvailableTime(@PathVariable Long id_broker, @PathVariable Long id_property, @PathVariable String date, @PathVariable String time) {
        return ResponseEntity.ok().body(brokerService.addAvailableTime(id_broker, id_property, date, time));
    }

    @GetMapping("/showAvailableTime/{id_broker}/{id_property}/{date}")
    public ResponseEntity<?> showAvailableTime(@PathVariable Long id_broker, @PathVariable Long id_property, @PathVariable String date){
        return ResponseEntity.ok().body(brokerService.showAvailableTime(id_broker, id_property, date));
    }
}
