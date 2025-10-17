package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.Models.Passenger;
import org.example.uberprojectauthservice.Repository.PassengerRepository;
import org.example.uberprojectauthservice.helpers.AuthPassengerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for  loading the userDetails object for auth
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private  PassengerRepository passengerRepository;

    public UserDetailsServiceImpl() {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger=passengerRepository.findByEmail(email);
        if(passenger.isPresent()){
            return  new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find the passenger using given email");
        }
    }

}
