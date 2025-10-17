package org.example.uberprojectauthservice.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


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
    public String createTokens(Map<String , Object> payload,String email){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        // generate key from secret
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(getSignInKey())
                .compact();
    }

    public Claims extractAllPayloads(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }
    public  Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method checks if the token expiry was before the timestamp or not
     * @param token JWT Token
     * @return true if token is expired else false
     */
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Key getSignInKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean validateToken(String token,String email){

        final String username = extractEmail(token);
        return (username.equals(email)) && !isTokenExpired(token);
    }

    public Object extractPayload(String token,String payloadKey){
        Claims claims = extractAllPayloads(token);
        return (Object) claims.get(payloadKey);
    }


    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp = new HashMap<>();
        mp.put("email","abc@123.com");
        mp.put("phoneNumber","123456789");
        String res=createTokens(mp,"Ishlok");
        System.out.println("Generated Token is : "+res);
        System.out.println(extractPayload(res,"email").toString());
    }
}

