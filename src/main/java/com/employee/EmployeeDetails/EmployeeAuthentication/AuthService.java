package com.employee.EmployeeDetails.EmployeeAuthentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AuthService {
    private final AuthRepository repository;

    @Value("${token.expiry.minutes}")
    private long expiryMinutes;

    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public String generateToken() {
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(expiryMinutes);

        AuthEntity authToken = new AuthEntity();
        authToken.setToken(token);
        authToken.setExpiryTime(expiryTime);
        repository.save(authToken);
        return token;
    }

    public boolean validateToken(String token) {
        AuthEntity authToken = repository.findById(token).orElse(null);
        if (authToken == null) {
            return false;
        }
        return authToken.getExpiryTime().isAfter(LocalDateTime.now());
    }
}


