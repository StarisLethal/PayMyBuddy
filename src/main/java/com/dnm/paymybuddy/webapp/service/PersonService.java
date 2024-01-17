package com.dnm.paymybuddy.webapp.service;


import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.repositories.PersonRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class PersonService {

    private final PersonRepository personRepository;


    public Iterable<Person> list(){
        return personRepository.findAll();
    }

    public Optional<Person> get(String email){
        return personRepository.findById(email);
    }

}
