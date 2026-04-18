package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.service.contracts.IJwtService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles("test")
public class JwtServiceTest {

    private IJwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void quandoGenerateTokenReceberUmUserDetailsValidoEntaoDeveRetornarUmToken() {
        var userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("teste@teste.com");
        String token = jwtService.generateToken(userDetails);
        log.debug("Token gerado: {}", token);
    }

    @Test
    void quandoExtractUsernameReceberUmTokenValidoEntaoDeveRetornarOUsername() {
        var userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("teste@teste.com");
        var token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        log.debug("Username retornado: {}", username);
    }

    @Test
    void quandoIsTokenValidReceberUmTokenValidoEntaoDeveRetornarTrue() {
        var userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("teste@teste.com");
        var token = jwtService.generateToken(userDetails);
        var isValid = jwtService.isTokenValid(token, userDetails);
        log.debug("Token é válido: {}", isValid);
        assertTrue(isValid);
    }
}
