package com.gustavodscruz.livranada.controller;

import com.gustavodscruz.livranada.api.AuthApi;
import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import com.gustavodscruz.livranada.model.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    @Override
    public ResponseEntity<TokenResponse> loginUser(UserLoginRequest userLoginRequest) {
        return AuthApi.super.loginUser(userLoginRequest);
    }

    @Override
    public ResponseEntity<TokenResponse> registerUser(UserRegisterRequest userRegisterRequest) {
        return AuthApi.super.registerUser(userRegisterRequest);
    }
}
