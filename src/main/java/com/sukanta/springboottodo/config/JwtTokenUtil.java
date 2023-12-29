package com.sukanta.springboottodo.config;

import com.sukanta.springboottodo.models.User;
import com.sukanta.springboottodo.repositories.userRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final String SECRET_KEY = "MTkH3MnbXRe5OAd164KR96l9MjObF/Se339B0BKRy9Dy+MGotE+oOwM/YbtFoQzKqJpccn47AX4h7VuTS4kV5Q=="; // Change this with a strong secret key
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days in milliseconds
    @Autowired
    private userRepository userRepository;

    public String generateToken(String email, ObjectId userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder().claim("email", email).claim("userId", String.valueOf(userId)).setSubject(email).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public User getUser(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        String email = (String) claims.get("email");

        return userRepository.findByEmail(email);
    }

    public ObjectId getUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();

        System.out.println(new ObjectId(String.valueOf(claims.get("userId"))) + " <<-- userId");

        return new ObjectId(String.valueOf(claims.get("userId")));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyJWT(String authToken) {
        if (authToken.isEmpty()) {
            return false;
        } else {
            return validateToken(authToken);
        }
    }
}
