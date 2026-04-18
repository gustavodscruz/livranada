package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.model.User;
import com.gustavodscruz.livranada.model.security.UserPrincipal;
import com.gustavodscruz.livranada.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void quandoUmEmailValidoEntrarNoloadUserByUsernameEHouverUsuarioEntaoDeveRetornarUserDetails() {
        var email = "email@email.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(User.builder()
                .email(email)
                .username("usuario")
                .passwordHash("dsikandl2j183211ibn4839214n12jkn74826bhjkge")
                .build()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        verify(userRepository).findByEmail(email);
        assertInstanceOf(UserDetails.class, userDetails);
        assertInstanceOf(UserPrincipal.class, userDetails);
    }
}
