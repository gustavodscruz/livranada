package com.gustavodscruz.livranada.service.plans;

import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.UserRegisterRequest;
import com.gustavodscruz.livranada.model.mappers.UserMapper;
import com.gustavodscruz.livranada.service.AuthService;
import com.gustavodscruz.livranada.service.UserService;
import com.gustavodscruz.livranada.service.contracts.IPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisterPlan implements IPlan<UserRegisterRequest, TokenResponse> {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;

    @Override
    public TokenResponse execute(UserRegisterRequest userRegisterRequest) {
        userService.create(userMapper.toEntity(userRegisterRequest));
        return authService.login(userMapper.toUserLoginRequest(userRegisterRequest));
    }
}
