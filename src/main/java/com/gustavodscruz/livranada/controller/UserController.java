package com.gustavodscruz.livranada.controller;

import com.gustavodscruz.livranada.api.UsersApi;
import com.gustavodscruz.livranada.model.ProfileResponse;
import com.gustavodscruz.livranada.model.UserLoginRequest;
import com.gustavodscruz.livranada.model.UserRegisterRequest;
import com.gustavodscruz.livranada.model.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<ProfileResponse> getUserProfile() {
        return UsersApi.super.getUserProfile();
    }


    @Override
    public ResponseEntity<Void> updateUserProfile(UserUpdateRequest userUpdateRequest) {
        return UsersApi.super.updateUserProfile(userUpdateRequest);
    }
}
