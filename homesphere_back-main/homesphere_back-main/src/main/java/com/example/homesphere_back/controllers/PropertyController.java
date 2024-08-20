package com.example.homesphere_back.controllers;

import com.example.homesphere_back.models.Properties;
import com.example.homesphere_back.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService properService;

    // Metodo para obtener todas las propiedades
    @GetMapping("/obtainAll")
    public ResponseEntity<?> obtainAll() {
        try {
            List<Properties> propiedades = properService.obtainAll();
            return ResponseEntity.ok().body(propiedades);
        } catch (Exception e) {
            // Registra el error para depuración
            e.printStackTrace();
            // Devuelve un error interno del servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving properties");
        }
    }

    // Metodo para obtener una propiedad por id
    @GetMapping("/obtainProperty/{id}")
    public ResponseEntity<Properties> obtainProperties(@PathVariable Long id) {
        Properties property = properService.findById(id);
        return ResponseEntity.ok().body(property);
    }


}
