package com.gustavodscruz.livranada.controller;

import com.gustavodscruz.livranada.model.TokenResponse;
import com.gustavodscruz.livranada.model.UserRegisterRequest;
import com.gustavodscruz.livranada.security.JwtAuthenticationFilter;
import com.gustavodscruz.livranada.service.plans.UserRegisterPlan;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    @Mock
    UserRegisterPlan userRegisterPlan;

    @Autowired
    MockMvc mockMvc;


    @Test
    void quandoRegisterReceberUmUserValidoEntaoDeveRealizarORegistroEDevoltarTokenOK() throws Exception {
        var userRegister = new UserRegisterRequest().email("email@email.com").username("user").password("senha123");

        when(userRegisterPlan.execute(any(UserRegisterRequest.class))).thenReturn(new TokenResponse().token("token123"));

        var postContent = String.format("""
                {
                    "email": "%s",
                    "username": "%s",
                    "password": "%s"
                }
                """, userRegister.getEmail(), userRegister.getUsername(), userRegister.getPassword());

        mockMvc.perform(post("/auth/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token123"));

        verify(userRegisterPlan).execute(any(UserRegisterRequest.class));

    }
}
