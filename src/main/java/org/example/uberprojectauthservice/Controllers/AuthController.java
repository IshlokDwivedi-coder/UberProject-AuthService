package org.example.uberprojectauthservice.Controllers;

import org.example.uberprojectauthservice.DTO.PassengerDto;
import org.example.uberprojectauthservice.DTO.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    private AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response=authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/signin/passenger")
    public ResponseEntity<?> signin(){ 
        return new ResponseEntity<>(10, HttpStatus.CREATED);
    }
}
