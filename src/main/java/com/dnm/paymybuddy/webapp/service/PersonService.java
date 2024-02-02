package com.dnm.paymybuddy.webapp.service;


import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;


    public Iterable<Person> list(){
        return personRepository.findAll();
    }

    public Optional<Person> get(String email){
        return personRepository.findById(email);
    }

    public List<Person> getFriendList(String email){

        Person person = personRepository.findById(email).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return person.getListOfFriend();

    }

    public Person getPersonByMail(String mail){
        return personRepository.findById(mail).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void addFriend(String personEmail, String friendEmail){

        Person person = personRepository.findById(personEmail).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Person friend = personRepository.findById(friendEmail).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        List<Person> listOfFriends = person.getListOfFriend();

        listOfFriends.add(friend);
        personRepository.save(person);

    }

    @Transactional
    public void deleteFriend(String personEmail, String friendEmail){
        Person person = personRepository.findById(personEmail).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        List<Person> listOfFriends = person.getListOfFriend();

        Person friend = person.getListOfFriend().stream()
                .filter(f -> f.getEmail().equals(friendEmail))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not Found"));

        listOfFriends.remove(friend);
        personRepository.save(person);

    }

}
