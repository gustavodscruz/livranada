package com.gustavodscruz.livranada.service.contracts;

import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public TokenResponse login(UserLoginRequest userLoginRequest);
}
