package com.gustavodscruz.livranada.service;

import com.gustavodscruz.livranada.exception.UserAuthNotRetrivedException;
import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import com.gustavodscruz.livranada.model.security.UserPrincipal;
import com.gustavodscruz.livranada.repository.UserRepository;
import com.gustavodscruz.livranada.service.contracts.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(UserLoginRequest userLoginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequest.getEmail(),
                        userLoginRequest.getPassword()
                )
        );

        UserDetails user = (UserDetails) auth.getPrincipal();

        if (user == null) throw new UserAuthNotRetrivedException();

        return new TokenResponse().token(jwtService.generateToken(user));
    }
}
