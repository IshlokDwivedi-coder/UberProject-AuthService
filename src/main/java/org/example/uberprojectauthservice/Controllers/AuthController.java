package org.example.uberprojectauthservice.Controllers;

import org.example.uberprojectauthservice.DTO.AuthRequestDto;
import org.example.uberprojectauthservice.DTO.PassengerDto;
import org.example.uberprojectauthservice.DTO.PassengerSignupRequestDto;
import org.example.uberprojectauthservice.Services.AuthService;
import org.example.uberprojectauthservice.Services.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private AuthService authService;


    private final JWTService jwtService;
    private AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response=authService.signupPassenger(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signin(@RequestBody AuthRequestDto authRequestDto){
        System.out.println(" Request Received "+ authRequestDto.getEmail()+" "+authRequestDto.getPassword());
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            Map<String,Object> payload=new HashMap<>();
            payload.put("email",authRequestDto.getEmail());
            String jwtToken= jwtService.createTokens(payload,authentication.getPrincipal().toString());
            return   new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }else{
//            return new ResponseEntity<>("Auth not Successful", HttpStatus.OK);
            throw new UsernameNotFoundException("User not found");
        }
    }
}
