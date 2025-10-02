package org.example.uberprojectauthservice.Services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;




@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * This method is used to create a brand-new JWT token based on payload
     * @return
     */
    private String createTokens(Map<String , Object> payload,String username){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        // generate key from secret
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp = new HashMap<>();
        mp.put("email","abc@123.com");
        mp.put("phoneNumber","123456789");
        String res=createTokens(mp,"Ishlok");
        System.out.println("Generated Token is : "+res);
    }
}

