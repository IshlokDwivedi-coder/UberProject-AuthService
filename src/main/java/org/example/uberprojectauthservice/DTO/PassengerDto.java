package org.example.uberprojectauthservice.DTO;

import lombok.*;
import org.example.uberprojectauthservice.Models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String password; //encrypted password

    private String phoneNumber;

    private Date CreatedAt;

    public static PassengerDto from(Passenger p){
        PassengerDto result=PassengerDto.builder()
                .id(p.getId().toString())
                .name(p.getName())
                .CreatedAt(p.getCreatedAt())
                .phoneNumber(p.getPhoneNumber())
                .password(p.getPassword())
                .build();
        return result;
    }
}
