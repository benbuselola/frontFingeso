package com.example.homesphere_back.services;

import com.example.homesphere_back.models.Property;
import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    public Users findByEmail(String email){return userRepository.findByEmail(email);}
    public Users findByRut(String rut){return userRepository.findByRut(rut);}
    public Users findByNumber(String number){return userRepository.findByNumber(number);}
    public List<Users> obtainAll() {return userRepository.obtainAll();}

    public Optional<Users> findById(Long id){return userRepository.findById(id);}

    public boolean saveUser(Users user){
        if (((findByEmail(user.getEmail())) == null) &&
                ((findByRut(user.getRut())) == null) &&
                ((findByNumber(user.getNumber())) == null)){

            String userName = user.getFirstName();
            String subject = "Bienvenido/a :)" + userName;
            String body = "Es un gusto tenerte con nosotros, te ayudaremos a completar tu sueño de " +
                    "propiedad propia.";
            mailService.sendEmail(user.getEmail(), subject, body);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean saveProperty(Property prop, Long id){
        Optional<Users> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            Users user = optionalUser.get();

            List<Property> properties = user.getProperties();
            properties.add(prop);

            user.setProperties(properties);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public List<Property> getPropertiesByUser(Long id){
        Optional<Users> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            return user.getProperties();
        }else {
            return null;
        }
    }

    public boolean login(String email, String password){
        return userRepository.findByEmailAndPassword(email, password) != null;
    }

    public boolean validateRut(String rut){
        // largo del rut
        int largo = rut.length();

        // recorre el string del rut buscando un punto, si encuentra uno retorna false
        for (int i = 0; i < largo; i++){
            if (rut.charAt(i) == '.'){
                return false;
            }
        }

        char dv;

        // se obtiene el digito verificador
        dv = rut.charAt(largo - 1);

        // se verifica que el digito verificador sea correcto con el rut respectivamente
        int suma = 0;
        int multiplicador = 2;
        for (int i = largo - 2; i >= 0; i--){

            if(rut.charAt(i) == '-'){

                // salta a la siguiente iteracion
                continue;
            }

            suma += multiplicador * Integer.parseInt(String.valueOf(rut.charAt(i)));
            multiplicador++;
            if (multiplicador == 8){
                multiplicador = 2;
            }
        }
        int resto = suma % 11;
        int resultado = 11 - resto;

        System.out.println(resultado);

        // se verifica que el digito verificador sea correcto
        if (resultado == 10){
            if (dv == 'k'){
                return true;
            }
        } else if (resultado == 11){
            if (dv == '0'){
                return true;
            }
        } else {
            // convierte dv al formato de numero
            int gd = Character.getNumericValue(dv);
            if (gd == resultado){

                return true;
            }
        }
        return false;
    }

    public boolean updateEmail(Long id, String email){
        Optional<Users> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            user.setEmail(email);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public boolean updateNumber(Long id, String number){
        Optional<Users> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            user.setNumber(number);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public boolean updatePassword(Long id, String password){
        Optional<Users> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            Users user = optionalUser.get();
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }
}
