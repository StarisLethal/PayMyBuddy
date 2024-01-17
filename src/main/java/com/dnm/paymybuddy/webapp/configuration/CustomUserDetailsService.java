package com.dnm.paymybuddy.webapp.configuration;

import com.dnm.paymybuddy.webapp.model.Person;
import com.dnm.paymybuddy.webapp.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByEmail(email);
        if (!person.isPresent()) {
            throw new UsernameNotFoundException("L'utilisateur avec l'e-mail " + email + " n'a pas été trouvé");
        }

        return buildUserDetails(person.get());
    }

    private UserDetails buildUserDetails(Person person){

        List<GrantedAuthority> authorities = getGrantedAuthorities(person.getRole());

        return new User(person.getEmail(), person.getPassword(), authorities);

    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
