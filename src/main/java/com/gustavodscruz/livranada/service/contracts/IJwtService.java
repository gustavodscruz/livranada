package com.gustavodscruz.livranada.service.contracts;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    public String generateToken(UserDetails user);
    public String extractUsername(String token);
    public boolean isTokenValid(String token, UserDetails user);
}
