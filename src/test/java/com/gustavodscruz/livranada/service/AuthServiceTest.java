package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import com.gustavodscruz.livranada.model.security.UserPrincipal;
import com.gustavodscruz.livranada.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
@ActiveProfiles("test")
public class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;



    @Test
    void quandoLoginReceberUmUserRegisterValidoEntaoDeveRetornarToken() {
        var user = User.builder()
                .email("teste@teste.com")
                .password("senha123")
                .username("usuario")
                .build();

        var auth = mock(Authentication.class);

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token123");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(new UserPrincipal(user));

        TokenResponse tokenResponse = authService.login(new UserLoginRequest()
                .email("teste@teste.com")
                .password("senha123")
                .username("usuario")
        );

        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(any(UserDetails.class));

        assertDoesNotThrow(tokenResponse::getToken);
        log.debug(tokenResponse.getToken());
        assertInstanceOf(String.class, tokenResponse.getToken());
    }
}
