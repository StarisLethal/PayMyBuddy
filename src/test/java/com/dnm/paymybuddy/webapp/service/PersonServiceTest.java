package com.dnm.paymybuddy.webapp.service;


import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testList() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person());
        personList.add(new Person());

        when(personRepository.findAll()).thenReturn(personList);

        Iterable<Person> result = personService.list();

        verify(personRepository).findAll();
        assertEquals(personList, result);
    }

    @Test
    public void testGetPersonByMail() {
        String mail = "test@example.com";
        Person person = new Person();
        person.setEmail(mail);

        when(personRepository.findById(mail)).thenReturn(Optional.of(person));

        Person result = personService.getPersonByMail(mail);

        verify(personRepository).findById(mail);
        assertEquals(mail, result.getEmail());
    }

    @Test
    public void testGet() {
        String email = "test@example.com";
        Person person = new Person();
        person.setEmail(email);

        when(personRepository.findById(email)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.get(email);

        verify(personRepository).findById(email);
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    public void testGetFriendList() {
        String email = "test@example.com";
        Person person = new Person();
        person.setEmail(email);
        List<Person> friendList = new ArrayList<>();
        person.setListOfFriend(friendList);

        when(personRepository.findById(email)).thenReturn(Optional.of(person));

        List<Person> result = personService.getFriendList(email);

        verify(personRepository).findById(email);
        assertEquals(friendList, result);
    }
    @Test
    public void testAddFriend() {
        String personEmail = "person@example.com";
        String friendEmail = "friend@example.com";
        Person person = new Person();
        person.setEmail(personEmail);
        Person friend = new Person();
        friend.setEmail(friendEmail);

        when(personRepository.findById(personEmail)).thenReturn(Optional.of(person));
        when(personRepository.findById(friendEmail)).thenReturn(Optional.of(friend));

        personService.addFriend(personEmail, friendEmail);

        verify(personRepository).save(person);
        assertTrue(person.getListOfFriend().contains(friend));
    }

    @Test
    public void testDeleteFriend() {
        String personEmail = "person@example.com";
        String friendEmail = "friend@example.com";
        Person person = new Person();
        person.setEmail(personEmail);
        Person friend = new Person();
        friend.setEmail(friendEmail);
        person.getListOfFriend().add(friend);

        when(personRepository.findById(personEmail)).thenReturn(Optional.of(person));

        personService.deleteFriend(personEmail, friendEmail);

        verify(personRepository).save(person);
        assertFalse(person.getListOfFriend().contains(friend));
    }
}