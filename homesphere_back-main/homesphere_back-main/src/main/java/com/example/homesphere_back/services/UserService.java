package com.example.homesphere_back.services;

import com.example.homesphere_back.models.Properties;
import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users findByEmail(String email){return userRepository.findByEmail(email);}
    public Users findByRut(String rut){return userRepository.findByRut(rut);}
    public Users findByNumber(String number){return userRepository.findByNumber(number);}
    public Users findByEmailAndPassword(String email, String password){return userRepository.findByEmailAndPassword(email, password);}

    public Users findById(Long id){return userRepository.findById(id).get();}
    public boolean saveUser(Users user){
        userRepository.save(user);
        return true;
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



    public boolean login(String email, String password){
        // si encuentra un usuario con esos datos retorna true
        if (userRepository.findByEmailAndPassword(email, password) != null){
            return true;
        }
        return false;
    }

    public Long getIdByEmail(String email){
        Users user = userRepository.findByEmail(email);
        return user.getId();
    }


    public boolean updateEmail(Long id, String email){
        Users user = userRepository.findById(id).get();
        user.setEmail(email);
        userRepository.save(user);
        return true;
    }

    public boolean updateNumber(Long id, String number){
        Users user = userRepository.findById(id).get();
        user.setNumber(number);
        userRepository.save(user);
        return true;
    }

    public boolean updatePassword(Long id, String password){
        Users user = userRepository.findById(id).get();
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    public List<Properties> getPropertiesbyUser(Long id){
        Users user = userRepository.findById(id).get();
        return user.getProperties();
    }
}
