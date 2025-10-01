package org.example.uberprojectauthservice.Models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends BaseModel{

    private String name;

    @Column(nullable = false,unique = true)
    private  String licenseNo;

    private String phoneNumber;

    // 1 : n , Driver : Booking
    @OneToMany(mappedBy = "driver",fetch = FetchType.EAGER)
    private List<Booking> bookings=new ArrayList<>();
}
