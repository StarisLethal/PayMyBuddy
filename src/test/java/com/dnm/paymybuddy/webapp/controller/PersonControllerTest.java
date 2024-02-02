package com.dnm.paymybuddy.webapp.controller;

import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PersonControllerTest {
    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowLoginPage() {
        String result = personController.showLoginPage();
        assertEquals("login", result);
    }

    @Test
    public void testListFriends() {
        Person testPerson = new Person();
        testPerson.setEmail("test@example.com");

        when(principal.getName()).thenReturn("user@example.com");
        when(personService.getPersonByMail("user@example.com")).thenReturn(testPerson);

        String result = personController.listFriends(model, principal);

        verify(model).addAttribute("person", testPerson);

        assertEquals("contact", result);
    }

    @Test
    public void testAddFriend() {
        when(principal.getName()).thenReturn("user@example.com");
        when(personService.getPersonByMail("user@example.com")).thenReturn(new Person());

        String result = personController.addFriend(model, principal, "person@example.com", "friend@example.com");

        verify(model).addAttribute("personMail", "person@example.com");
        verify(model).addAttribute("friendMail", "friend@example.com");

        assertEquals("addConfirm", result);
    }

    @Test
    public void testAddConfirm() {
        when(principal.getName()).thenReturn("user@example.com");
        when(personService.getPersonByMail("user@example.com")).thenReturn(new Person());
        doNothing().when(personService).addFriend("person@example.com", "friend@example.com");

        String result = personController.addConfirm(model, principal, "person@example.com", "friend@example.com");

        verify(model).addAttribute("friendMail", "friend@example.com");

        verify(personService).addFriend("person@example.com", "friend@example.com");

        assertEquals("addconfirmed", result);
    }
}