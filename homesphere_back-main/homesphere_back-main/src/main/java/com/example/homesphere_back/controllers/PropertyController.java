package com.example.homesphere_back.controllers;

import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody Property property) {
        boolean isSaved = propertyService.savePropertie(property);
        return ResponseEntity.ok(isSaved);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return  ResponseEntity.ok().body(propertyService.findById(id));
    }

    @GetMapping("/obtainAll")
    public ResponseEntity<?> obtainAll(){
        return ResponseEntity.ok().body(propertyService.obtainAll());
    }

    @GetMapping("/findByComuna/{comuna}")
    public ResponseEntity<?> findByComuna(@PathVariable String comuna){
        return ResponseEntity.ok().body(propertyService.findByComuna(comuna));
    }

    @GetMapping("/findByBath/{bathrooms}")
    public ResponseEntity<?> findByBathroom(@PathVariable String bathrooms){
        return ResponseEntity.ok().body(propertyService.findByBathrooms(bathrooms));
    }

    @GetMapping("/findByBed/{bedrooms}")
    public ResponseEntity<?> findByBedrooms(@PathVariable String bedrooms){
        return ResponseEntity.ok().body(propertyService.findByBedrooms(bedrooms));
    }

    @GetMapping("/findByAsc")
    public ResponseEntity<?> orderAsc(){
        return ResponseEntity.ok().body(propertyService.orderAsc());
    }

    @GetMapping("/findByDesc")
    public ResponseEntity<?> orderDes(){
        return ResponseEntity.ok().body(propertyService.orderDes());
    }

    @GetMapping("/findByComunaAndBathrooms/{comuna}/{bathrooms}")
    public ResponseEntity<?> findByComunaAndBathrooms(@PathVariable String comuna,
                                                      @PathVariable String bathrooms){
        return ResponseEntity.ok().body(propertyService.findByComunaAndBathrooms(comuna, bathrooms));
    }

    @GetMapping("/findByComunaAndBedrooms/{comuna}/{bedrooms}")
    public ResponseEntity<?> findByComunaAndBedrooms(@PathVariable String comuna,
                                                     @PathVariable String bedrooms){
        return ResponseEntity.ok().body(propertyService.findByComunaAndBedrooms(comuna, bedrooms));
    }

    @GetMapping("/findByBedroomsAndBathrooms/{bedrooms}/{bathrooms}")
    public ResponseEntity<?> findByBedroomsAndBathrooms(@PathVariable String bedrooms,
                                                        @PathVariable String bathrooms){
        return ResponseEntity.ok().body(propertyService.findByBedroomsAndBathrooms(bedrooms, bathrooms));
    }

    @GetMapping("/findByAll/{comuna}/{bathrooms}/{bedrooms}")
    public ResponseEntity<?> findByComunaAndBathroomsAndBedrooms(@PathVariable String comuna,
                                                                 @PathVariable String bathrooms,
                                                                 @PathVariable String bedrooms){
        return ResponseEntity.ok().body(propertyService.findByComunaAndBathroomsAndBedrooms(comuna,
                bathrooms,
                bedrooms));
    }

    @GetMapping("/findByAllByAsc/{comuna}/{bathrooms}/{bedrooms}")
    public ResponseEntity<?> findByAllFiltersAsc(@PathVariable String comuna,
                                                 @PathVariable String bathrooms,
                                                 @PathVariable String bedrooms){
        return ResponseEntity.ok().body(propertyService.findByAllFiltersAsc(comuna,
                bathrooms,
                bedrooms));
    }

    @GetMapping("/findByAllFiltersDes/{comuna}/{bathrooms}/{bedrooms}")
    public ResponseEntity<?> findByAllFiltersDes(@PathVariable String comuna,
                                                 @PathVariable String bathrooms,
                                                 @PathVariable String bedrooms){
        return ResponseEntity.ok().body(propertyService.findByAllFiltersDes(comuna,
                bathrooms,
                bedrooms));
    }
}