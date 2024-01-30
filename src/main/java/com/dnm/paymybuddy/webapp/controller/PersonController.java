package com.dnm.paymybuddy.webapp.controller;


import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;

    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/testrequest")
    public ResponseEntity<String> testPostRequest() {
        System.out.println("TERST///////////§§§§§§");
        return ResponseEntity.ok("POST request successful");
    }

    @GetMapping("/contact")
    public String listFriends(Model model, Principal principal) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);
        return "contact";
    }

    @PostMapping("/addFriend")
    public String addFriend (Model model, Principal principal,
                             @RequestParam("personMail") String personMail,
                             @RequestParam("friendMail") String friendMail){

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);

        model.addAttribute("personMail", personMail);
        model.addAttribute("friendMail", friendMail);
        return "addConfirm";
    }

    @PostMapping("/addConfirm")
    public String addConfirm ( Model model, Principal principal,
            @RequestParam("personMail") String personMail,
            @RequestParam("friendMail") String friendMail){

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);
        model.addAttribute("friendMail", friendMail);
        try {
            personService.addFriend(personMail, friendMail);
            return "addconfirmed";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "test";
        }
    }
}
