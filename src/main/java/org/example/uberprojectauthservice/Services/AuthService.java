package org.example.uberprojectauthservice.Services;

import org.example.uberprojectauthservice.DTO.PassengerDto;
import org.example.uberprojectauthservice.DTO.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Models.Passenger;
import org.example.uberprojectauthservice.Repository.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository,BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bcryptPasswordEncoder=bcryptPasswordEncoder;
    }

    public PassengerDto signupPassenger(PassengerSignupRequestDto passengerSignupRequestDto){
        Passenger passenger=new Passenger().builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .password(bcryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();
      Passenger newPassenger= passengerRepository.save(passenger);

      return PassengerDto.from(newPassenger);
    }
}
