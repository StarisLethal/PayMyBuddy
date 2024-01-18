package com.dnm.paymybuddy.webapp.controller;


import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;

    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("home");
        return "home";
    }

    @GetMapping("/friends")
    public String listFriends(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        List<Person> friends = personService.getFriendList(email);
        model.addAttribute("friends", friends);
        return "friends";
    }

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        try {
            logger.info("GET request to /persons successful");
            return personService.list();
        } catch (Exception e) {
            logger.error("Error processing GET request to /persons", e);
            return null;
        }
    }

}
