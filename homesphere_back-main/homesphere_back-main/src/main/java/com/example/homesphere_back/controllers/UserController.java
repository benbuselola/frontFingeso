package com.example.homesphere_back.controllers;

import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.services.PropertyService;
import com.example.homesphere_back.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService properService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody Users user) {
        boolean isSaved = userService.saveUser(user);
        return ResponseEntity.ok(isSaved);
    }

    @GetMapping("/obtainAll")
    public ResponseEntity<?> obtainAll(){
        return ResponseEntity.ok().body(userService.obtainAll());
    }

    @GetMapping("/validateRut/{rut}")
    public ResponseEntity<Boolean> validateRut(@PathVariable String rut){
        return ResponseEntity.ok().body(userService.validateRut(rut));
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<Boolean> login(@PathVariable String email, @PathVariable String password) {
        boolean loginSuccessful = userService.login(email, password);
        return ResponseEntity.ok(loginSuccessful);
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<Users> findByEmail(@PathVariable String email){
        return ResponseEntity.ok().body(userService.findByEmail(email));
    }

    @PostMapping("/saveProperty/{id}")
    public ResponseEntity<Boolean> saveProperty(@RequestBody Property property, @PathVariable Long id) {
        Users user = userService.findById(id);
        properService.saveProperty(property);
        user.getProperties().add(property);
        userService.saveUser(user);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/addToFavorites")
    public ResponseEntity<Boolean> addToFavorites(@RequestBody Property property, @PathVariable Long id){
        boolean isSaved = userService.addToFavorites(property, id);
        return ResponseEntity.ok().body(isSaved);
    }

    @GetMapping("/buscarID/{email}")
    public ResponseEntity<Long> buscarID(@PathVariable String email){
        return ResponseEntity.ok().body(userService.findByEmail(email).getId());
    }

    @GetMapping("/findbyID/{id}")
    public ResponseEntity<Users> findbyID(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping("/updateEmail/{id}/{email}")
    public ResponseEntity<Boolean> updateEmail(@PathVariable Long id, @PathVariable String email){
        return ResponseEntity.ok().body(userService.updateEmail(id, email));
    }

    @PostMapping("/updatePassword/{id}/{password}")
    public ResponseEntity<Boolean> updatePassword(@PathVariable Long id, @PathVariable String password){
        return ResponseEntity.ok().body(userService.updatePassword(id, password));
    }

    @PostMapping("/updateNumber/{id}/{number}")
    public ResponseEntity<Boolean> updateNumber(@PathVariable Long id, @PathVariable String number){
        return ResponseEntity.ok().body(userService.updateNumber(id, number));
    }

    @GetMapping("/getPropertiesbyUser/{id}")
    public ResponseEntity<?> getPropertiesbyUser(@PathVariable Long id){
        Users user = userService.findById(id);
        List<Property> properties = user.getProperties();
        return ResponseEntity.ok().body(properties);
    }
}