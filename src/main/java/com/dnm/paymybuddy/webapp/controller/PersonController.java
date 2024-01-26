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

    @GetMapping("/")
    public String home(Model model, Principal principal){

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);
        model.addAttribute("person", person);
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/test-request")
    public ResponseEntity<String> testPostRequest() {
        return ResponseEntity.ok("POST request successful");
    }

/*    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(final Throwable throwable, final Model model) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }*/

    @GetMapping("/contact")
    public String listFriends(Model model, Principal principal) {

        String userMail = principal.getName();
        Person person = personService.getPersonByMail(userMail);

        model.addAttribute("person", person);
        return "contact";
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

    @PostMapping("/addFriend")
    public String addFriend ( Model model,
            @RequestParam("personMail") String personMail,
            @RequestParam("friendMail") String friendMail){
        try {
            personService.addFriend(personMail, friendMail);
            return "transfer";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "test";
        }
    }
}
